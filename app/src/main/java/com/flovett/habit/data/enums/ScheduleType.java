package com.flovett.habit.data.enums;

import androidx.annotation.StringRes;

import com.flovett.habit.R;

import java.util.Arrays;
import java.util.List;

public enum ScheduleType {
    DAY(0),
    MORNING(1),
    EVENING(2);

    private int value;

    ScheduleType(int value) {
        this.value = value;
    }

    public int getIntValue() {
        return value;
    }

    public @StringRes int getName() {
        switch (this) {
            case MORNING:
                return R.string.schedule_type_morning;
            case EVENING:
                return R.string.schedule_type_evening;
            default:
                return R.string.schedule_type_day;
        }
    }

    public static ScheduleType fromInt(int value) {
        for (ScheduleType type : ScheduleType.values()) {
            if (type.getIntValue() == value) {
                return type;
            }
        }

        return null;
    }

    public static List<ScheduleType> order() {
        return Arrays.asList(MORNING, DAY, EVENING);
    }
}
