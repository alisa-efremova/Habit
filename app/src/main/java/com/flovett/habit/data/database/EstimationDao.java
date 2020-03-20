package com.flovett.habit.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.query.EstimationWithHabit;

import org.joda.time.LocalDate;

import java.util.List;


@Dao
public interface EstimationDao {

    @Insert
    void insert(Estimation estimation);

    @Insert
    void insertList(List<Estimation> estimations);

    @Query("SELECT * FROM estimations")
    LiveData<List<Estimation>> getAll();

    @Query("SELECT * FROM estimations, habits where estimations.parent_habit_id = habits.habit_id AND date = :date")
    LiveData<List<EstimationWithHabit>> getEstims(LocalDate date);
}

