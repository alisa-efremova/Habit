package com.flovett.habit.habits;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flovett.habit.R;
import com.flovett.habit.data.Habit;
import com.flovett.habit.data.HabitPriority;
import com.flovett.habit.databinding.ItemHabitBinding;

import java.util.List;

public class HabitsAdapter extends PagedListAdapter<Habit, RecyclerView.ViewHolder> {

    public HabitsAdapter(DiffUtil.ItemCallback<Habit> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHabitBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_habit, parent, false);

        return new HabitViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HabitViewHolder habitViewHolder = (HabitViewHolder) holder;
        habitViewHolder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class HabitViewHolder extends RecyclerView.ViewHolder {

        ItemHabitBinding binding;

        HabitViewHolder(ItemHabitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Habit habit) {
            binding.setBackgroundColor(HabitPriority.fromInt(habit.getPriority()).getColor());
            binding.setHabit(habit);
            binding.executePendingBindings();
        }

        public Habit getHabit() {
            return binding.getHabit();
        }
    }
}
