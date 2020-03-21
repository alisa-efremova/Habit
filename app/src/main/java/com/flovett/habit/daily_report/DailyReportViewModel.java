package com.flovett.habit.daily_report;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.App;
import com.flovett.habit.Util.DateUtil;
import com.flovett.habit.data.database.EstimationDao;
import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.query.EstimationWithHabit;

import org.joda.time.LocalDate;

import java.util.List;
import java.util.concurrent.Executors;

public class DailyReportViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();

    private EstimationDao estimationDao;
    private LocalDate date = DEFAULT_DATE;
    private MutableLiveData<List<EstimationWithHabit>> estimationsLiveData = new MutableLiveData<>();

    private ObservableField<String> formattedDate = new ObservableField<>(DateUtil.formatDate(DEFAULT_DATE));

    public DailyReportViewModel() {
        estimationDao = App.getInstance().getDb().estimationDao();
    }

    private void loadEstimations(LocalDate date) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            List<EstimationWithHabit> estims = estimationDao.getHabitsWithEstim(date);
            for (EstimationWithHabit estimationWithHabit : estims) {
                if (estimationWithHabit.getEstimation() == null) {
                    Estimation estimation = new Estimation(date, 0, estimationWithHabit.getHabit());
                    estimationWithHabit.setEstimation(estimation);
                    estimationDao.insert(estimation);
                }
            }

            estimationsLiveData.postValue(estims);
        });
    }

    public void setDate(LocalDate date) {
        this.date = date;
        formattedDate.set(DateUtil.formatDate(date));
        loadEstimations(date);
    }

    public LiveData<List<EstimationWithHabit>> getEstimationsLiveData() {
        return estimationsLiveData;
    }

    public void onDayBack() {
        saveEstimations();
        date = date.minusDays(1);
        formattedDate.set(DateUtil.formatDate(date));
        loadEstimations(date);
    }

    public void onDayForward() {
        saveEstimations();
        date = date.plusDays(1);
        formattedDate.set(DateUtil.formatDate(date));
        loadEstimations(date);
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
