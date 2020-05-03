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
    public abstract long insert(Estimation estimation);

    @Update
    public abstract void update(Estimation estimation);

    @Insert
    public abstract void insertList(List<Estimation> estimations);

    @Query("SELECT * " +
            "FROM habits " +
            "LEFT JOIN estimations ON habit_id = parent_habit_id AND date = :date " +
            "ORDER BY priority DESC, habit_id DESC")
    public abstract List<EstimationWithHabit> getHabitsWithEstim(LocalDate date);

    @Transaction
    public void update(List<EstimationWithHabit> estimationsWithHabit) {
        for (EstimationWithHabit estimationWithHabit : estimationsWithHabit) {
            update(estimationWithHabit.getEstimation());
        }
    }

    @Transaction
    public List<EstimationWithHabit> loadHabitsWithEstim(LocalDate date) {
        List<EstimationWithHabit> estims = getHabitsWithEstim(date);
        for (EstimationWithHabit estimationWithHabit : estims) {
            if (estimationWithHabit.getEstimation() == null) {
                Estimation estimation = new Estimation(date, 0, estimationWithHabit.getHabit());
                estimationWithHabit.setEstimation(estimation);
                long id = insert(estimation);
                estimation.setEstimationId(id);
            }
        }

        return estims;
    }
}