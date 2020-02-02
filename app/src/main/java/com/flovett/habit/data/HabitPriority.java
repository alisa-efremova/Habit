package com.flovett.habit.data;

import com.flovett.habit.R;


public enum HabitPriority {
    LOW,
    MEDIUM,
    HIGH,
    VITAL;

    public static HabitPriority fromInt(int value) {
        return values()[value];
    }

    public int getIntValue() {
        return ordinal();
    }

    public int getName() {
        switch (this) {
            case LOW:
                return R.string.priority_low;
            case MEDIUM:
                return R.string.priority_medium;
            case HIGH:
                return R.string.priority_high;
            case VITAL:
                return R.string.priority_vital;
        }
        return 0;
    }

    public int getColor() {
        switch (this) {
            case LOW:
                return R.color.colorPriorityLow;
            case MEDIUM:
                return R.color.colorPriorityMedium;
            case HIGH:
                return R.color.colorPriorityHigh;
            case VITAL:
                return R.color.colorPriorityVital;
        }
        return 0;
    }

}
