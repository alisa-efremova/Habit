package com.flovett.habit.edithabit;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flovett.habit.App;
import com.flovett.habit.SingleLiveEvent;
import com.flovett.habit.data.Habit;
import com.flovett.habit.data.database.HabitDao;

import java.util.concurrent.Executors;

public class HabitViewModel extends ViewModel {

    private Habit habit = new Habit("", "", 3);
    private SingleLiveEvent<Void> habitUpdatedEvent = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> titleError = new MutableLiveData<>(Boolean.FALSE);

    public SingleLiveEvent<Void> getHabitUpdatedEvent() {
        return habitUpdatedEvent;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public void onSave() {
        if (TextUtils.isEmpty(habit.getTitle())) {
            titleError.setValue(Boolean.TRUE);
            return;
        }

        HabitDao habitDao = App.getInstance().getDb().habitDao();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            if (habit.getId() == 0) {
                habitDao.insert(habit);
            } else {
                habitDao.update(habit);
            }
        });

        habitUpdatedEvent.call();
    }

    public void onCancel() {
        habitUpdatedEvent.call();
    }

    public LiveData<Boolean> getTitleError() {
        return titleError;
    }
}
