package com.flovett.habit.daily_report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.flovett.habit.R;
import com.flovett.habit.databinding.ActivityDailyReportBinding;

import org.joda.time.LocalDate;


public class DailyReportActivity extends AppCompatActivity {

    public static String EXTRA_DATE = "EXTRA_DATE";

    private DailyReportViewModel viewModel;
    private ActivityDailyReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.daily_report_title);

        viewModel = ViewModelProviders.of(this).get(DailyReportViewModel.class);
        LocalDate date = (LocalDate) getIntent().getSerializableExtra(EXTRA_DATE);
        viewModel.setDate(date);

        bind();
    }

    private void bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daily_report);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }
}
