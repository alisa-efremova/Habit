package com.flovett.habit.data;

public enum ScheduleType {
    UNDEFINED(0),
    MORNING(1),
    LUNCH(2),
    EVENING(3);

    private int value;

    ScheduleType(int value) {
        this.value = value;
    }

    public int getIntValue() {
        return value;
    }

    public static ScheduleType fromInt(int value) {
        for (ScheduleType type : ScheduleType.values()) {
            if (type.getIntValue() == value) {
                return type;
            }
        }

        return null;
    }
}
