package com.flovett.habit.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.SingleLiveEvent;
import com.flovett.habit.Util.DateUtil;

import org.joda.time.LocalDate;

public class HomeViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();

    private LocalDate date = DEFAULT_DATE;
    private ObservableField<String> formattedDate = new ObservableField<>(formatDate(DEFAULT_DATE));

    private SingleLiveEvent<Void> openHabitListEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<LocalDate> fillDailyReportEvent = new SingleLiveEvent<>();

    public void onOpenHabitList() {
        openHabitListEvent.call();
    }

    public LocalDate getDate() {
        return date;
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

    public void onFillDailyReport() {
        fillDailyReportEvent.setValue(date);
    }

    public SingleLiveEvent<Void> getOpenHabitListEvent() {
        return openHabitListEvent;
    }

    public SingleLiveEvent<LocalDate> getFillDailyReportEvent() {
        return fillDailyReportEvent;
    }

    private String formatDate(LocalDate date) {
        return DateUtil.formatDate(date);
    }
}
