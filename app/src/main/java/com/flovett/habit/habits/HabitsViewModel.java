package com.flovett.habit.habits;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.flovett.habit.App;
import com.flovett.habit.data.Habit;
import com.flovett.habit.data.database.HabitDao;

import java.util.concurrent.Executors;


public class HabitsViewModel extends ViewModel {
    private HabitDao habitDao;
    private ObservableBoolean hasNoHabits = new ObservableBoolean(true);
    private LiveData<PagedList<Habit>> habitsLiveData;


    public HabitsViewModel() {
        habitDao = App.getInstance().getDb().habitDao();
        habitsLiveData = createHabitsPagedListLiveData();
    }

    public LiveData<PagedList<Habit>> getHabitsLiveData() {
        return habitsLiveData;
    }

    public ObservableBoolean hasNoHabits() {
        return hasNoHabits;
    }

    public void deleteHabit(Habit habit) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            habitDao.delete(habit);
        });
    }

    public void deleteAllHabits() {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            habitDao.deleteAll();
        });
    }

    private LiveData<PagedList<Habit>> createHabitsPagedListLiveData() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build();

        return new LivePagedListBuilder<>
                (habitDao.getDataSourceForAll(), config)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Habit>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        hasNoHabits.set(true);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Habit itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        if (hasNoHabits.get()) {
                            hasNoHabits.set(false);
                        }
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Habit itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        if (hasNoHabits.get()) {
                            hasNoHabits.set(false);
                        }

                    }
                })
                .build();
    }

}
