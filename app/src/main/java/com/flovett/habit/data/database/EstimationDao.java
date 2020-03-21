package com.flovett.habit.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.query.EstimationWithHabit;

import org.joda.time.LocalDate;

import java.util.List;


@Dao
public abstract class EstimationDao {

    @Insert
    public abstract void insert(Estimation estimation);

    @Transaction
    public void update(List<EstimationWithHabit> estimationsWithHabit) {
        for (EstimationWithHabit estimationWithHabit : estimationsWithHabit) {
            update(estimationWithHabit.getEstimation());
        }
    }

    @Update
    public abstract void update(Estimation estimation);

    @Insert
    public abstract void insertList(List<Estimation> estimations);

    @Query("SELECT * FROM estimations")
    public abstract LiveData<List<Estimation>> getAll();

    @Query("SELECT * FROM estimations, habits WHERE estimations.parent_habit_id = habits.habit_id AND date = :date")
    public abstract List<EstimationWithHabit> getEstims(LocalDate date);

    @Query("SELECT * FROM estimations, habits WHERE estimations.parent_habit_id = habits.habit_id AND date = :date")
    public abstract LiveData<List<EstimationWithHabit>> getEstimsLiveData(LocalDate date);

}

