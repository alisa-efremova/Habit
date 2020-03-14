package com.flovett.habit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.flovett.habit.data.Habit;
import com.flovett.habit.data.database.AppDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class App extends Application {
    private static App INSTANCE;

    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                List<Habit> habits = Arrays.asList(
                                        new Habit("title1", "desc", 1),
                                        new Habit("title2", "desc", 1),
                                        new Habit("title3", "desc", 1)
                                );

                                getInstance().getDb().habitDao().insertList(habits);
                            }
                        });
                    }
                })
                .build();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public AppDatabase getDb() {
        return db;
    }
}
