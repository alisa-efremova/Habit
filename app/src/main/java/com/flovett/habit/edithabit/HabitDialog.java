package com.flovett.habit.edithabit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.flovett.habit.R;
import com.flovett.habit.data.Habit;
import com.flovett.habit.databinding.LayoutDialogEditHabitBinding;

public class HabitDialog extends DialogFragment {

    private HabitViewModel model;
    private Habit habit;

    public HabitDialog() {
    }

    public HabitDialog(Habit habit) {
        this.habit = habit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        model = ViewModelProviders.of(this).get(HabitViewModel.class);
        if (habit != null) {
            model.setHabit(new Habit(habit));
        }

        LayoutDialogEditHabitBinding dialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.layout_dialog_edit_habit,
                null,
                false);

        dialogBinding.setLifecycleOwner(getActivity());
        dialogBinding.setModel(model);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(dialogBinding.getRoot());

        AlertDialog dialog = builder.create();

        model.getHabitUpdatedEvent().observe(getActivity(), (Void v) -> {
            dialog.dismiss();
        });

        return dialog;
    }
}
