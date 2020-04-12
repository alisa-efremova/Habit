package com.flovett.habit.daily_report;

import androidx.databinding.ObservableField;

import com.flovett.habit.R;
import com.flovett.habit.data.entity.Estimation;
import com.flovett.habit.data.entity.Habit;
import com.flovett.habit.data.query.EstimationWithHabit;

public class EstimationViewModel {
    public static final int MAX_ESTIMATION_VALUE = 5;

    private EstimationWithHabit estimationWithHabit;
    private ObservableField<String> emoji = new ObservableField<>();
    private ObservableField<Integer> textColor = new ObservableField<>();


    public EstimationViewModel(EstimationWithHabit estimationWithHabit) {
        this.estimationWithHabit = estimationWithHabit;
        textColor.set(getTextColorInt(getEstimationValue()));
        emoji.set(getEmoji(getEstimationValue()));
    }

    public int getMaxEstimationValue() {
        return MAX_ESTIMATION_VALUE;
    }

    public Estimation getEstimation() {
        return estimationWithHabit.getEstimation();
    }

    public Habit getHabit() {
        return estimationWithHabit.getHabit();
    }

    public int getEstimationValue() {
        return getEstimation().getValue();
    }

    public void setEstimationValue(int value) {
        getEstimation().setValue(value);
        textColor.set(getTextColorInt(value));
        emoji.set(getEmoji(value));
    }

    public ObservableField<Integer> getTextColor() {
        return textColor;
    }

    public ObservableField<String> getEmoji() {
        return emoji;
    }

    private int getTextColorInt(int estimationValue) {
        return estimationValue == 0
                ? R.color.colorLightDark
                : R.color.colorPrimaryDark;
    }

    private String getEmoji(int estimationValue) {
        switch (estimationValue) {
            case 1:
                return "\uD83D\uDE2B";
            case 2:
                return "\uD83D\uDE41";
            case 3:
                return "\uD83D\uDE42";
            case 4:
                return "\uD83D\uDE01";
            case 5:
                return "\uD83D\uDE1C";
            default:
                return "";
        }
    }
}
