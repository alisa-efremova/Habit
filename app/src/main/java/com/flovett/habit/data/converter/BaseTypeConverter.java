package com.flovett.habit.data.converter;


import androidx.room.TypeConverter;

import com.flovett.habit.data.enums.HabitState;
import com.flovett.habit.data.enums.ScheduleType;

import org.joda.time.LocalDate;

import java.util.Date;

public class BaseTypeConverter {

    @TypeConverter
    public static long toTimestamp(LocalDate jodaDate) {
        return jodaDate.toDate().getTime();
    }

    @TypeConverter
    public static LocalDate fromTimestamp(long timestamp) {
        return LocalDate.fromDateFields(new Date(timestamp));
    }

    @TypeConverter
    public static int scheduleTypeToInt(ScheduleType scheduleType) {
        return scheduleType.getIntValue();
    }

    @TypeConverter
    public static ScheduleType scheduleTypeFromInt(int scheduleTypeInt) {
        return ScheduleType.fromInt(scheduleTypeInt);
    }

    @TypeConverter
    public static int habitStateToInt(HabitState habitState) {
        return habitState.getIntValue();
    }

    @TypeConverter
    public static HabitState habitStateFromInt(int habitStateInt) {
        return HabitState.fromInt(habitStateInt);
    }
}
