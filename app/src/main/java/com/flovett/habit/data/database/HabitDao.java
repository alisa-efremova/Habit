package com.flovett.habit.data.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flovett.habit.data.Habit;

import java.util.List;


@Dao
public interface HabitDao {
    @Insert
    void insert(Habit habit);

    @Insert
    void insertList(List<Habit> habits);

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("DELETE FROM habits")
    void deleteAll();

    @Query("SELECT * FROM habits")
    LiveData<List<Habit>> getAll();

    @Query("SELECT * FROM habits ORDER BY id DESC")
    DataSource.Factory<Integer, Habit> getDataSourceForAll();

    @Query("SELECT * FROM habits WHERE id = :id")
    LiveData<Habit> getById(long id);

    @Query("SELECT * FROM habits WHERE title = :title")
    LiveData<Habit> getByTitle(String title);
}
