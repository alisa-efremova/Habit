package com.flovett.habit.daily_report;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class DailyReportViewModel extends ViewModel {

    private static LocalDate DEFAULT_DATE = LocalDate.now();
    private static final int LONG_PRESS_DELAY_MS = 800;
    private static final int LONG_PRESS_PERIOD_MS = 100;
    private static final int INCREMENT_DAYS = 1;

    private EstimationDao estimationDao;
    private LocalDate date = DEFAULT_DATE;
    private MutableLiveData<List<EstimationWithHabit>> estimationsLiveData = new MutableLiveData<>();

    private View.OnTouchListener onDayBackButtonTouchListener;
    private View.OnTouchListener onDayForwardButtonTouchListener;
    private Timer longPressTimer;

    private ObservableField<String> formattedDate = new ObservableField<>(DateUtil.formatDate(DEFAULT_DATE));

    @SuppressLint("ClickableViewAccessibility")
    public DailyReportViewModel() {
        estimationDao = App.getInstance().getDb().estimationDao();

        onDayBackButtonTouchListener = (View v, MotionEvent event) -> {
            onDateChangeButtonEvent(event, -INCREMENT_DAYS);
            return false;
        };

        onDayForwardButtonTouchListener = (View v, MotionEvent event) -> {
            onDateChangeButtonEvent(event, INCREMENT_DAYS);
            return false;
        };
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
        changeDateWithIncrement(-INCREMENT_DAYS);
        loadEstimations(date);
    }

    public void onDayForward() {
        saveEstimations();
        changeDateWithIncrement(INCREMENT_DAYS);
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

    public View.OnTouchListener getOnDayBackButtonTouchListener() {
        return onDayBackButtonTouchListener;
    }

    public View.OnTouchListener getOnDayForwardButtonTouchListener() {
        return onDayForwardButtonTouchListener;
    }

    private void changeDateWithIncrement(int increment) {
        date = date.plusDays(increment);
        formattedDate.set(DateUtil.formatDate(date));
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

    private void onDateChangeButtonEvent(MotionEvent event, int increment) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            longPressTimer = new Timer();
            TimerTask task = getLongPressTimerTask(increment);
            longPressTimer.schedule(task, LONG_PRESS_DELAY_MS, LONG_PRESS_PERIOD_MS);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (longPressTimer != null) {
                longPressTimer.cancel();
                loadEstimations(date);
            }
        }
    }

    private TimerTask getLongPressTimerTask(int increment) {
        return new TimerTask() {
            @Override
            public void run() {
                changeDateWithIncrement(increment);
            }
        };
    }
}
