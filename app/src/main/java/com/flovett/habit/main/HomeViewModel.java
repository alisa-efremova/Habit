package com.flovett.habit.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.SingleLiveEvent;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HomeViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();
    private static DateTimeFormatter weekDayFormatter = DateTimeFormat.forPattern("E");
    private static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("d MMMM YYYY");

    private LocalDate date = DEFAULT_DATE;
    private ObservableField<String> formattedDate = new ObservableField<>(formatDate(DEFAULT_DATE));

    private SingleLiveEvent<Void> openHabitListEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> getOpenHabitListEvent() {
        return openHabitListEvent;
    }

    public void onOpenHabitList() {
        openHabitListEvent.call();
    }

    public ObservableField<String> getFormattedDate() {
        return formattedDate;
    }

    public void onDayBack() {
        date = date.minusDays(1);
        formattedDate.set(formatDate(date));
    }

    public void onDayForward() {
        date = date.plusDays(1);
        formattedDate.set(formatDate(date));
    }

    private String formatDate(LocalDate date) {
        return weekDayFormatter.print(date).toUpperCase() + ", " + dateFormatter.print(date);
    }
}
