package com.flovett.habit.daily_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flovett.habit.R;
import com.flovett.habit.data.query.EstimationWithHabit;
import com.flovett.habit.databinding.ItemDailyReportBinding;

import java.util.List;

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.Holder> {

    private List<EstimationWithHabit> estimationList;

    public DailyReportAdapter(List<EstimationWithHabit> estimationList) {
        this.estimationList = estimationList;
    }

    public void setEstimationList(List<EstimationWithHabit> estimationList) {
        this.estimationList = estimationList;
    }

    @NonNull
    @Override
    public DailyReportAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDailyReportBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_daily_report, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportAdapter.Holder holder, int position) {
        holder.bind(estimationList.get(position));
    }

    @Override
    public int getItemCount() {
        return estimationList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemDailyReportBinding binding;

        Holder(ItemDailyReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EstimationWithHabit estimation) {
            binding.setEstimation(estimation);
            binding.executePendingBindings();
        }
    }
}
