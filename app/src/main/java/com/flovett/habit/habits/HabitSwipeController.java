package com.flovett.habit.habits;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.flovett.habit.R;
import com.flovett.habit.data.entity.Habit;

public class HabitSwipeController extends ItemTouchHelper.Callback {

    private static final float BUTTON_WIDTH = 170;
    private static final float BUTTON_PADDING = 16;
    private static final float CORNER_RADIUS = 16;
    private static final float BUTTON_AREA_WIDTH = BUTTON_WIDTH * 2 + BUTTON_PADDING * 2;

    private boolean isSwipeBack;
    private boolean areButtonVisible;
    private RecyclerView.ViewHolder currentItemViewHolder;
    private RectF deleteButton;
    private RectF editButton;
    private HabitSwipeController.Actions actions;
    private Context context;

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                         int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags,
                                          int layoutDirection) {
        if (isSwipeBack) {
            isSwipeBack = areButtonVisible;
            return 0;
        }

        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (areButtonVisible) {
                dX = -BUTTON_AREA_WIDTH;
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (!areButtonVisible) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        currentItemViewHolder = viewHolder;
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }

    private void drawButtons(Canvas canvas, RecyclerView.ViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        deleteButton = drawButton(canvas,
                itemView,
                itemView.getRight() - BUTTON_WIDTH,
                itemView.getRight(),
                R.string.button_delete,
                R.color.colorAccent);

        editButton = drawButton(canvas,
                itemView,
                itemView.getRight() - BUTTON_WIDTH * 2 - BUTTON_PADDING,
                itemView.getRight() - BUTTON_WIDTH - BUTTON_PADDING,
                R.string.button_edit,
                R.color.colorOtherButton);
    }

    private RectF drawButton(Canvas canvas, View view, float left, float right, int text, int color) {
        Paint p = new Paint();

        RectF button = new RectF(left, view.getTop(), right, view.getBottom());
        p.setColor(ContextCompat.getColor(context, color));
        canvas.drawRoundRect(button, CORNER_RADIUS, CORNER_RADIUS, p);
        drawText(context.getString(text), canvas, button, p);

        return button;
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 32;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    private void setTouchListener(@NonNull Canvas c,
                                  @NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  float dX,
                                  float dY,
                                  int actionState,
                                  boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((View view, MotionEvent event) -> {
            isSwipeBack = event.getAction() == MotionEvent.ACTION_CANCEL ||
                    event.getAction() == MotionEvent.ACTION_UP;

            if (isSwipeBack) {
                areButtonVisible = (dX < -BUTTON_AREA_WIDTH);

                if (areButtonVisible) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    setItemsClickable(recyclerView, false);
                }
            }

            return false;
        });
    }

    private void setTouchDownListener(@NonNull Canvas c,
                                      @NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      float dX,
                                      float dY,
                                      int actionState,
                                      boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            return false;
        });
    }

    private void setTouchUpListener(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX,
                                    float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                HabitSwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((View view1, MotionEvent event1) -> {
                    return false;
                });
                setItemsClickable(recyclerView, true);
                isSwipeBack = false;

                if (actions != null) {
                    Habit habit = ((HabitsAdapter.HabitViewHolder) viewHolder).getHabit();

                    if (isAreaClicked(deleteButton, event)) {
                        actions.onDelete(habit);
                    } else if (isAreaClicked(editButton, event)) {
                        actions.onEdit(habit);
                    }
                }

                areButtonVisible = false;
                currentItemViewHolder = null;
            }

            return false;
        });
    }

    private boolean isAreaClicked(RectF area, MotionEvent event) {
        return area != null && area.contains(event.getX(), event.getY());
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    public static abstract class Actions {
        public void onDelete(Habit habit) {}
        public void onEdit(Habit habit) {}
    }
}
