package com.flovett.habit.edithabit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.flovett.habit.R;
import com.flovett.habit.data.entity.Habit;
import com.flovett.habit.databinding.LayoutHabitBinding;

public class HabitActivity extends AppCompatActivity {

    public static final String EXTRA_HABIT = "EXTRA_HABIT";

    private HabitViewModel model;
    private LayoutHabitBinding binding;
    private Habit habit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        habit = (Habit) getIntent().getSerializableExtra(EXTRA_HABIT);

        model = ViewModelProviders.of(this).get(HabitViewModel.class);
        if (habit != null) {
            model.setHabit(habit);
        }

        bind();

        model.getHabitUpdatedEvent().observe(this, (Void v) -> {
            finish();
        });
    }

    private void bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.layout_habit);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
    }
}
