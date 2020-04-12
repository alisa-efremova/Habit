package com.flovett.habit.daily_report;

import com.flovett.habit.data.enums.ScheduleType;

class DailyReportHeaderListItem extends DailyReportListItem {

    private ScheduleType scheduleType;

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    public DailyReportHeaderListItem(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }
}
