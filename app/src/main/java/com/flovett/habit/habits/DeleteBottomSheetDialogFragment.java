package com.flovett.habit.habits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.flovett.habit.R;
import com.flovett.habit.data.entity.Habit;
import com.flovett.habit.databinding.LayoutPromptBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeleteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private Habit habit;
    private HabitsActivity.EventHandler eventHandler;

    public DeleteBottomSheetDialogFragment(Habit habit, HabitsActivity.EventHandler eventHandler) {
        this.habit = habit;
        this.eventHandler = eventHandler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LayoutPromptBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.layout_prompt, container, false);
        binding.setHabit(habit);
        binding.setEventHandler(eventHandler);
        return binding.getRoot();
    }
}
