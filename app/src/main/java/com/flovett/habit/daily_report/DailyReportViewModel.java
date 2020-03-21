package com.flovett.habit.daily_report;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.App;
import com.flovett.habit.Util.DateUtil;
import com.flovett.habit.data.database.EstimationDao;
import com.flovett.habit.data.database.HabitDao;
import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.entity.Habit;
import com.flovett.habit.data.query.EstimationWithHabit;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DailyReportViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();

    private EstimationDao estimationDao;
    private HabitDao habitDao;
    private LocalDate date = DEFAULT_DATE;
    private MutableLiveData<List<EstimationWithHabit>> estimationsLiveData = new MutableLiveData<>();

    private ObservableField<String> formattedDate = new ObservableField<>(DateUtil.formatDate(DEFAULT_DATE));

    public DailyReportViewModel() {
        estimationDao = App.getInstance().getDb().estimationDao();
        habitDao = App.getInstance().getDb().habitDao();
    }

    private void loadEstimations(LocalDate date) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            List<EstimationWithHabit> estims = estimationDao.getEstims(date);
            if (estims.isEmpty()) {
                List<Habit> habits = habitDao.getAll();
                List<Estimation> newEstimations = new ArrayList<>();
                for (Habit habit : habits) {
                    Estimation estimation = new Estimation(date, 0, habit);
                    newEstimations.add(estimation);
                    estims.add(new EstimationWithHabit(estimation, habit));
                }
                estimationDao.insertList(newEstimations);
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
