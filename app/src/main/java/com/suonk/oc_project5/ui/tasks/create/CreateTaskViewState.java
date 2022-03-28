package com.suonk.oc_project5.ui.tasks.create;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CreateTaskViewState {

    /**
     * TaskName
     */
    @NonNull
    private final String taskName;

    /**
     * projectName
     */
    @NonNull
    private final String projectName;

    /**
     * Constructor
     */
    public CreateTaskViewState(
            @NonNull String taskName,
            @NonNull String projectName) {
        this.taskName = taskName;
        this.projectName = projectName;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTaskViewState that = (CreateTaskViewState) o;
        return taskName.equals(that.taskName) && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, projectName);
    }

    @Override
    public String toString() {
        return "CreateTaskViewState{" +
                "taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
