package com.suonk.oc_project5.ui.tasks.create;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CreateTaskViewState {

    private final long projectId;

    /**
     * projectName
     */
    @NonNull
    private final String projectName;

    /**
     * Constructor
     */
    public CreateTaskViewState(
            long projectId,
            @NonNull String projectName
    ) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public long getProjectId() {
        return projectId;
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
        return projectId == that.projectId && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName);
    }

    @Override
    public String toString() {
        return projectId + " : " + projectName;
    }
}
