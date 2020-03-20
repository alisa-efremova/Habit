package com.flovett.habit.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.flovett.habit.R;
import com.flovett.habit.daily_report.DailyReportActivity;
import com.flovett.habit.databinding.ActivityHomeBinding;
import com.flovett.habit.habits.HabitsActivity;

import org.joda.time.LocalDate;


public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        viewModel.getOpenHabitListEvent().observe(this, (Void v) -> {
            Intent intent = new Intent(this, HabitsActivity.class);
            startActivity(intent);
        });

        viewModel.getFillDailyReportEvent().observe(this, (LocalDate date) -> {
            Intent intent = new Intent(this, DailyReportActivity.class);
            intent.putExtra(DailyReportActivity.EXTRA_DATE, viewModel.getDate());
            startActivity(intent);
        });

        bind();


//        Executors.newSingleThreadScheduledExecutor().execute(() -> {
//            App.getInstance().getDb().prepopulate();
//        });
    }

    private void bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }
}
