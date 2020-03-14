package com.flovett.habit.Util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

    public static DateTimeFormatter weekDayFormatter = DateTimeFormat.forPattern("E");
    public static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("d MMMM YYYY");

    public static String formatDate(LocalDate date) {
        return weekDayFormatter.print(date).toUpperCase() + ", " + dateFormatter.print(date);
    }
}
