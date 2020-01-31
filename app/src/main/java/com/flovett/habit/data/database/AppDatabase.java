package com.flovett.habit.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.flovett.habit.data.Habit;

@Database(entities = {Habit.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
}
