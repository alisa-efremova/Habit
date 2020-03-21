package com.flovett.habit;

import android.view.View;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class CustomBindings {
    @BindingAdapter({"app:error"})
    public static void BindError(EditText view, String error) {
        view.setError(error);
        view.requestFocus();
    }

    @BindingAdapter({"app:onTouchListener"})
    public static void BindOnTouchListener(View view, View.OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            view.setOnTouchListener(onTouchListener);
        }
    }
}
