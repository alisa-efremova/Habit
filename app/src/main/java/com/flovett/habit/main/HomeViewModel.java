package com.flovett.habit.main;

import androidx.lifecycle.ViewModel;

import com.flovett.habit.SingleLiveEvent;

public class HomeViewModel extends ViewModel {

    private SingleLiveEvent<Void> openHabitListEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> getOpenHabitListEvent() {
        return openHabitListEvent;
    }

    public void onOpenHabitList() {
        openHabitListEvent.call();
    }
}
