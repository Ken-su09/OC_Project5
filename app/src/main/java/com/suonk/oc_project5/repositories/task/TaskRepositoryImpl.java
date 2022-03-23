package com.suonk.oc_project5.repositories.task;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.suonk.oc_project5.model.dao.TaskDao;
import com.suonk.oc_project5.model.data.Task;

import java.util.List;

import javax.inject.Inject;

public class TaskRepositoryImpl implements TaskRepository {

    @NonNull
    private final TaskDao dao;

    @Inject
    public TaskRepositoryImpl(@NonNull TaskDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {
        return dao.getAllTasks();
    }

    @Override
    public LiveData<Task> getTaskById(Long id) {
        return dao.getTaskById(id);
    }

    @Override
    public void insertTask(@NonNull Task task) {
        dao.insertTask(task);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        dao.updateTask(task);
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        dao.deleteTask(task);
    }
}
