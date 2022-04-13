package com.suonk.oc_project5.model.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Objects;

@Entity(tableName = "task",
        foreignKeys = {@ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "project_id")
})
public class Task {

    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project_id")
    private final long projectId;

    /**
     * The name of the task
     */
    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    /**
     * Instantiates a new Task.
     *
     * @param id                the unique identifier of the task to set
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     */
    public Task(long id, long projectId, @NonNull String name) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the unique identifier of the project associated to the task.
     *
     * @return the unique identifier of the project associated to the task.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && projectId == task.projectId && name.equals(task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                '}';
    }
}
