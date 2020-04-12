package com.flovett.habit.daily_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flovett.habit.R;
import com.flovett.habit.data.enums.ScheduleType;
import com.flovett.habit.data.query.EstimationWithHabit;
import com.flovett.habit.databinding.ItemDailyReportEstimBinding;
import com.flovett.habit.databinding.ItemDailyReportHeaderBinding;

import java.util.List;

public class DailyReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DailyReportListItem> items;

    public DailyReportAdapter(List<DailyReportListItem> estimationList) {
        this.items = estimationList;
    }

    public void setEstimationList(List<DailyReportListItem> estimationList) {
        this.items = estimationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == DailyReportListItem.TYPE_HEADER) {
            ItemDailyReportHeaderBinding binding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_daily_report_header, parent, false);
            return new HeaderHolder(binding);
        } else {
            ItemDailyReportEstimBinding binding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_daily_report_estim, parent, false);
            return new EstimHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DailyReportListItem.TYPE_HEADER) {
            DailyReportHeaderListItem item = (DailyReportHeaderListItem) items.get(position);
            ((HeaderHolder) holder).bind(item.getScheduleType());
        } else {
            DailyReportEstimListItem item = (DailyReportEstimListItem) items.get(position);
            ((EstimHolder) holder).bind(item.getEstimationWithHabit());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class EstimHolder extends RecyclerView.ViewHolder {
        ItemDailyReportEstimBinding binding;

        EstimHolder(ItemDailyReportEstimBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EstimationWithHabit estimation) {
            EstimationViewModel viewModel = new EstimationViewModel(estimation);
            binding.setModel(viewModel);
            binding.executePendingBindings();
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        ItemDailyReportHeaderBinding binding;

        HeaderHolder(ItemDailyReportHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ScheduleType scheduleType) {
            binding.setScheduleType(scheduleType);
            binding.executePendingBindings();
        }
    }
}
