package com.flovett.habit.daily_report;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.Util.DateUtil;

import org.joda.time.LocalDate;

public class DailyReportViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();

    private LocalDate date = DEFAULT_DATE;

    private ObservableField<String> formattedDate = new ObservableField<>(DateUtil.formatDate(DEFAULT_DATE));

    public void setDate(LocalDate date) {
        this.date = date;
        formattedDate.set(DateUtil.formatDate(date));
    }

    public ObservableField<String> getFormattedDate() {
        return formattedDate;
    }
}
