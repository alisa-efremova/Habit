package com.flovett.habit.data.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.flovett.habit.data.converter.BaseTypeConverter;
import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.entity.Habit;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Database(entities = {Habit.class, Estimation.class}, version = 2, exportSchema = false)
@TypeConverters({BaseTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
    public abstract EstimationDao estimationDao();

    public void prepopulate() {
        runInTransaction(() -> {


            List<Habit> habits = Arrays.asList(
                    new Habit("title1", "desc", 1),
                    new Habit("title2", "desc", 1),
                    new Habit("title3", "desc", 1)
            );

            long[] ids = habitDao().insertList(habits);
            if (ids != null) {

                List<Estimation> estimations = new ArrayList<>();
                for (long id : ids) {
                    Estimation estimation = new Estimation(LocalDate.now().plusDays(0), 2, id);
                    estimations.add(estimation);
                }
                estimationDao().insertList(estimations);
            }
        });
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE habits ADD COLUMN schedule_type INTEGER DEFAULT 0");
        }
    };
}
