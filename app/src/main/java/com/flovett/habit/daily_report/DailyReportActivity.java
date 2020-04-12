package com.flovett.habit.daily_report;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flovett.habit.R;
import com.flovett.habit.data.enums.ScheduleType;
import com.flovett.habit.data.query.EstimationWithHabit;
import com.flovett.habit.databinding.ActivityDailyReportBinding;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            adapter.setEstimationList(convertToGroupedList(estimations));
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

    private List<DailyReportListItem> convertToGroupedList(List<EstimationWithHabit> estimations) {
        Map<ScheduleType, List<EstimationWithHabit>> groupingMap = new HashMap<>();

        ArrayList<DailyReportListItem> listItems = new ArrayList<>();
        for (EstimationWithHabit estimation : estimations) {
            List<EstimationWithHabit> estimationsForType = groupingMap.get(estimation.getHabit().getScheduleType());
            if (estimationsForType == null) {
                estimationsForType = new ArrayList<>();
            }
            estimationsForType.add(estimation);
            groupingMap.put(estimation.getHabit().getScheduleType(), estimationsForType);
        }

        for (ScheduleType scheduleType : ScheduleType.order()) {
            if (groupingMap.containsKey(scheduleType)) {
                listItems.add(new DailyReportHeaderListItem(scheduleType));
                for (EstimationWithHabit estimation : groupingMap.get(scheduleType)) {
                    listItems.add(new DailyReportEstimListItem(estimation));
                }
            }
        }

        return listItems;
    }
}
