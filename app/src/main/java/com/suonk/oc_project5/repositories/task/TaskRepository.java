package com.suonk.oc_project5.repositories.task;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.suonk.oc_project5.model.data.Task;

import java.util.List;

public interface TaskRepository {

    LiveData<List<Task>> getAllTasks();

    LiveData<Task> getTaskById(Long id);

    void insertTask(@NonNull Task task);

    void updateTask(@NonNull Task task);

    void deleteTask(long taskId);
}
