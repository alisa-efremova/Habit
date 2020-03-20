package com.flovett.habit.data.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flovett.habit.data.entity.Habit;

import java.util.List;


@Dao
public abstract class HabitDao {

    @Insert
    public abstract void insert(Habit habit);

    @Insert
    public abstract long[] insertList(List<Habit> habits);

    @Update
    public abstract void update(Habit habit);

    @Delete
    public abstract void delete(Habit habit);

    @Query("DELETE FROM habits")
    public abstract void deleteAll();

    @Query("SELECT * FROM habits")
    public abstract LiveData<List<Habit>> getAll();

    @Query("SELECT * FROM habits ORDER BY priority DESC, habit_id DESC")
    public abstract DataSource.Factory<Integer, Habit> getDataSourceForAll();

    @Query("SELECT * FROM habits WHERE habit_id = :id")
    public abstract LiveData<Habit> getById(long id);

    @Query("SELECT * FROM habits WHERE title = :title")
    public abstract LiveData<Habit> getByTitle(String title);

}
