package com.flovett.habit.daily_report;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.App;
import com.flovett.habit.Util.DateUtil;
import com.flovett.habit.data.database.EstimationDao;
import com.flovett.habit.data.query.EstimationWithHabit;

import org.joda.time.LocalDate;

import java.util.List;
import java.util.concurrent.Executors;

public class DailyReportViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();

    private EstimationDao estimationDao;
    private LocalDate date = DEFAULT_DATE;
    private LiveData<List<EstimationWithHabit>> estimationsLiveData;

    private ObservableField<String> formattedDate = new ObservableField<>(DateUtil.formatDate(DEFAULT_DATE));

    public DailyReportViewModel() {
        estimationDao = App.getInstance().getDb().estimationDao();
    }

    public void setDate(LocalDate date) {
        this.date = date;
        formattedDate.set(DateUtil.formatDate(date));
        estimationsLiveData = estimationDao.getEstims(date);
    }

    public LiveData<List<EstimationWithHabit>> getEstimationsLiveData() {
        return estimationsLiveData;
    }

    public ObservableField<String> getFormattedDate() {
        return formattedDate;
    }

    public void saveEstimations() {
        List<EstimationWithHabit> estimations = estimationsLiveData.getValue();
        if (estimations != null && !estimations.isEmpty()) {
            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                estimationDao.update(estimations);
            });
        }
    }
}
