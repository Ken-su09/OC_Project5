package com.suonk.oc_project5.ui.tasks.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TasksViewState {

    /**
     * The unique identifier of the task
     */
    private final long id;

    /**
     * TaskName
     */
    @NonNull
    private final String taskName;

    /**
     * color
     */
    private final int color;

    /**
     * Constructor
     */
    public TasksViewState(
            long id,
            @NonNull String taskName,
            int color) {
        this.id = id;
        this.taskName = taskName;
        this.color = color;
    }


    public long getId() {
        return id;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksViewState that = (TasksViewState) o;
        return id == that.id && color == that.color && taskName.equals(that.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, color);
    }

    @Override
    public String toString() {
        return "TasksViewState{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", color=" + color +
                '}';
    }
}