package com.flovett.habit.daily_report;

import com.flovett.habit.data.query.EstimationWithHabit;

class DailyReportEstimListItem extends DailyReportListItem {

    private EstimationWithHabit estimationWithHabit;

    @Override
    public int getType() {
        return TYPE_ESTIM;
    }

    public DailyReportEstimListItem(EstimationWithHabit estimationWithHabit) {
        this.estimationWithHabit = estimationWithHabit;
    }

    public EstimationWithHabit getEstimationWithHabit() {
        return estimationWithHabit;
    }
}
