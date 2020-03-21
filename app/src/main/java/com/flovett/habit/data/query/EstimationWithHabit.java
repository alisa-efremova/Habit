package com.flovett.habit.data.query;

import androidx.room.Embedded;

import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.entity.Habit;

public class EstimationWithHabit {
    public static final int MAX_ESTIMATION_VALUE = 5;

    @Embedded
    private Estimation estimation;

    @Embedded
    private Habit habit;

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

    public int getMaxEstimationValue() {
        return MAX_ESTIMATION_VALUE;
    }

}
