package com.flovett.habit;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class CustomBindings {
    @BindingAdapter({"app:error"})
    public static void BindError(EditText view, String error) {
        view.setError(error);
        view.requestFocus();
    }
}
