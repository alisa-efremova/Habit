package com.flovett.habit.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "habits")
public class Habit {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    private int priority;

    public Habit(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Habit(Habit habit) {
        this(habit.title, habit.description, habit.priority);
        id = habit.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return id == habit.id &&
                priority == habit.priority &&
                title.equals(habit.title) &&
                Objects.equals(description, habit.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, priority);
    }

    @NonNull
    @Override
    public String toString() {
        return id + ":" + title;
    }
}
