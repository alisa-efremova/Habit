package com.flovett.habit.edithabit;

import android.app.Application;
import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.flovett.habit.App;
import com.flovett.habit.R;
import com.flovett.habit.SingleLiveEvent;
import com.flovett.habit.data.enums.HabitPriority;
import com.flovett.habit.data.enums.ScheduleType;
import com.flovett.habit.data.database.HabitDao;
import com.flovett.habit.data.entity.Habit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class HabitViewModel extends AndroidViewModel {

    private static final int DEFAULT_PRIORITY = HabitPriority.MEDIUM.getIntValue();
    private static final int MAX_PRIORITY = HabitPriority.values().length - 1;

    private Habit habit = new Habit("", "", DEFAULT_PRIORITY);

    private boolean isNewHabit = true;
    private ObservableField<String> priorityTitle = new ObservableField<>();
    private ObservableBoolean titleError = new ObservableBoolean(false);

    private SingleLiveEvent<Void> habitUpdatedEvent = new SingleLiveEvent<>();

    private HashMap<ScheduleType, Integer> scheduleTypeMap = new HashMap<ScheduleType, Integer>() {{
        put(ScheduleType.DAY, R.id.radioScheduleTypeDay);
        put(ScheduleType.MORNING, R.id.radioScheduleTypeMorning);
        put(ScheduleType.EVENING, R.id.radioScheduleTypeEvening);
    }} ;

    public HabitViewModel(Application app) {
        super(app);
    }

    public SingleLiveEvent<Void> getHabitUpdatedEvent() {
        return habitUpdatedEvent;
    }

    public void setHabit(Habit habit) {
        this.habit = new Habit(habit);
        isNewHabit = false;
    }

    public void setTitle(String title) {
        habit.setTitle(title);
    }

    public void setDescription(String description) {
        habit.setDescription(description);
    }

    public void setPriority(int priority) {
        habit.setPriority(priority);
        updatePriorityTitle(priority);
    }

    public ObservableField<String> getPriorityTitle() {
        return priorityTitle;
    }

    public int getMaxPriority() {
        return MAX_PRIORITY;
    }

    public boolean isNewHabit() {
        return isNewHabit;
    }

    public String getTitle() {
        return habit.getTitle();
    }

    public String getDescription() {
        return habit.getDescription();
    }

    public int getPriority() {
        return habit.getPriority();
    }

    public ObservableBoolean getTitleError() {
        return titleError;
    }

    public int getScheduleTypeButtonId() {
        return scheduleTypeMap.get(habit.getScheduleType());
    }

    public void setScheduleTypeButtonId(int radioButtonId) {
        ScheduleType type = ScheduleType.DAY;
        for (Map.Entry<ScheduleType, Integer> entry : scheduleTypeMap.entrySet()) {
            if (entry.getValue() == radioButtonId) {
                type = entry.getKey();
            }
        }
        habit.setScheduleType(type);
    }

    public void onSave() {
        if (TextUtils.isEmpty(habit.getTitle())) {
            titleError.set(true);
            return;
        }

        HabitDao habitDao = App.getInstance().getDb().habitDao();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            if (habit.getHabitId() == 0) {
                habitDao.insert(habit);
            } else {
                habitDao.update(habit);
            }
        });

        habitUpdatedEvent.call();
    }

    private void updatePriorityTitle(int priority) {
        String title = getPriorityTitle(priority);
        priorityTitle.set(title);
    }

    private String getPriorityTitle(int priority) {
        return getApplication().getString(HabitPriority.fromInt(priority).getName());
    }
}
