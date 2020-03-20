package com.flovett.habit.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.flovett.habit.Util.DateUtil;

import org.joda.time.LocalDate;


@Entity(tableName = "estimations",
        foreignKeys = @ForeignKey(entity = Habit.class,
                parentColumns = "habit_id",
                childColumns = "parent_habit_id",
                onDelete = ForeignKey.CASCADE)
)
public class Estimation {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "estimation_id")
    private long estimationId;

    private LocalDate date;

    private int value;

    @ColumnInfo(name = "parent_habit_id")
    private long habitId;

    public Estimation(LocalDate date, int value, long habitId) {
        this.date = date;
        this.value = value;
        this.habitId = habitId;
    }

    public Estimation(LocalDate date, int value, Habit habit) {
        this(date, value, habit.getHabitId());
    }

    public long getEstimationId() {
        return estimationId;
    }

    public void setEstimationId(long estimationId) {
        this.estimationId = estimationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public String getFormattedDate() {
        return DateUtil.formatDate(date);
    }
}
