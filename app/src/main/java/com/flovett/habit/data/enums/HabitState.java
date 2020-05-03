package com.flovett.habit.data.enums;

public enum HabitState {
    ACTIVE(0),
    ARCHIVED(1),
    DELETED(2);

    HabitState(int value) {
        this.value = value;
    }

    private int value;

    public int getIntValue() {
        return value;
    }

    public static HabitState fromInt(int value) {
        for (HabitState type : HabitState.values()) {
            if (type.getIntValue() == value) {
                return type;
            }
        }

        return null;
    }
}
