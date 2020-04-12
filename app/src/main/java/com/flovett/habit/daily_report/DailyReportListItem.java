package com.flovett.habit.daily_report;

abstract class DailyReportListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ESTIM = 1;

    abstract public int getType();
}
