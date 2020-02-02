package com.flovett.habit.habits;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flovett.habit.R;
import com.flovett.habit.data.Habit;
import com.flovett.habit.databinding.ActivityHabitsBinding;
import com.flovett.habit.edithabit.HabitDialog;


public class HabitsActivity extends AppCompatActivity {
    HabitsAdapter habitsAdapter;
    ActivityHabitsBinding binding;
    HabitsViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.activity_title_habits);

        model = ViewModelProviders.of(this).get(HabitsViewModel.class);
        habitsAdapter = createHabitsAdapter();

        model.getHabitsLiveData().observe(this,
                (PagedList<Habit> habits) -> habitsAdapter.submitList(habits)
        );

        bind();
    }

    private void bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_habits);
        binding.setLifecycleOwner(this);
        binding.setHandler(new EventHandlers());
        binding.setModel(model);

        binding.recyclerView.setAdapter(habitsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bindSwipeController();
    }

    private void bindSwipeController() {
        HabitSwipeController swipeController = new HabitSwipeController();
        swipeController.setContext(this);
        swipeController.setActions(new HabitSwipeController.Actions() {
            @Override
            public void onDelete(Habit habit) {
                model.deleteHabit(habit);
            }

            @Override
            public void onEdit(Habit habit) {
                new HabitDialog(habit).show(getSupportFragmentManager(), null);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private HabitsAdapter createHabitsAdapter() {
        DiffUtil.ItemCallback<Habit> diffUtilCallback = new DiffUtil.ItemCallback<Habit>() {
            @Override
            public boolean areItemsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
                return oldItem.equals(newItem);
            }
        };

        HabitsAdapter adapter = new HabitsAdapter(diffUtilCallback);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                binding.recyclerView.scrollToPosition(positionStart);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);

                binding.recyclerView.scrollToPosition(toPosition);
            }
        });
        return adapter;
    }

    public class EventHandlers {
        public void onAddNewHabit() {
            new HabitDialog().show(getSupportFragmentManager(), null);
        }

        public void onDeleteAll() {
            model.deleteAllHabits();
        }
    }
}
