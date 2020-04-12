package com.flovett.habit.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.flovett.habit.data.enums.ScheduleType;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "habits")
public class Habit implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id")
    private long habitId;

    private String title;

    private String description;

    private int priority;

    @ColumnInfo(name = "schedule_type")
    private ScheduleType scheduleType = ScheduleType.DAY;

    public Habit(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Habit(Habit habit) {
        this(habit.title, habit.description, habit.priority);
        habitId = habit.habitId;
        scheduleType = habit.scheduleType;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public long getHabitId() {
        return habitId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return habitId == habit.habitId &&
                priority == habit.priority &&
                title.equals(habit.title) &&
                Objects.equals(description, habit.description) &&
                Objects.equals(scheduleType, habit.scheduleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitId, title, description, priority, scheduleType);
    }

    @NonNull
    @Override
    public String toString() {
        return habitId + ":" + title;
    }
}
