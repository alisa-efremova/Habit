package com.flovett.habit.data.query;

import androidx.room.Embedded;

import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.entity.Habit;

public class EstimationWithHabit {
    @Embedded Estimation estimation;

    @Embedded
    private Habit habit;

//    @Relation(parentColumn = "habit_id", entityColumn = "habit_id")
//    private List<Estimation> estimations;

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public Estimation getEstimation() {
        return estimation;
    }

    public void setEstimation(Estimation estimation) {
        this.estimation = estimation;
    }

    //    public List<Estimation> getEstimations() {
//        return estimations;
//    }
//
//    public void setEstimations(List<Estimation> estimations) {
//        this.estimations = estimations;
//    }
}
