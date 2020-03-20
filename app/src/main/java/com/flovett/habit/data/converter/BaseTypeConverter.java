package com.flovett.habit.data.converter;


import androidx.room.TypeConverter;

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
}
