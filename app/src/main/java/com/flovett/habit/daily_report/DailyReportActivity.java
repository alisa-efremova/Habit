package com.flovett.habit.daily_report;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flovett.habit.R;
import com.flovett.habit.data.query.EstimationWithHabit;
import com.flovett.habit.databinding.ActivityDailyReportBinding;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class DailyReportActivity extends AppCompatActivity {

    public static String EXTRA_DATE = "EXTRA_DATE";

    private DailyReportViewModel viewModel;
    private ActivityDailyReportBinding binding;
    private DailyReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.daily_report_title);

        viewModel = ViewModelProviders.of(this).get(DailyReportViewModel.class);
        LocalDate date = (LocalDate) getIntent().getSerializableExtra(EXTRA_DATE);
        viewModel.setDate(date);
        adapter = new DailyReportAdapter(new ArrayList<>());

        bind();

        viewModel.getEstimationsLiveData().observe(this, (List<EstimationWithHabit> estimations) -> {
            adapter.setEstimationList(estimations);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.saveEstimations();
    }

    private void bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daily_report);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        binding.estimRecyclerView.setAdapter(adapter);
        binding.estimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
