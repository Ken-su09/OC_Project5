package com.suonk.oc_project5.ui.tasks.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.task.TaskRepository;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTaskViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Inject
    public CreateTaskViewModel(@NonNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void insertNewTask(CreateTaskViewState createTaskViewState) {
        long projectId;

        switch (createTaskViewState.getProjectName()) {
            case "Project Tartampion":
                projectId = 1L;
                break;
            case "Project Lucidia":
                projectId = 2L;
                break;
            case "Project Circus":
                projectId = 3L;
                break;
            default:
                projectId = 1L;
                break;
        }

        executor.submit(() -> {
            taskRepository.insertTask(new Task(0, projectId, createTaskViewState.getTaskName()));
        });
    }
}
