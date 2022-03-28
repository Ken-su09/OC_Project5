package com.suonk.oc_project5.ui.tasks.list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.task.TaskRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TasksViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final MediatorLiveData<List<TasksViewState>> viewStateLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Integer> isListEmptyLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> sortIdLiveData = new MutableLiveData<>(R.id.sort_by_name);

    @Inject
    public TasksViewModel(@NonNull ProjectRepository projectRepository, @NonNull TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        LiveData<List<Project>> projectsLiveData = projectRepository.getAllProjects();
        LiveData<List<Task>> tasksLiveData = taskRepository.getAllTasks();

        viewStateLiveData.addSource(projectsLiveData, projects ->
                combine(projects, tasksLiveData.getValue(), sortIdLiveData.getValue()));

        viewStateLiveData.addSource(tasksLiveData, tasks ->
                combine(projectsLiveData.getValue(), tasks, sortIdLiveData.getValue()));

        viewStateLiveData.addSource(sortIdLiveData, sortId ->
                combine(projectsLiveData.getValue(), tasksLiveData.getValue(), sortId));
    }

    private void combine(@Nullable List<Project> projects, @Nullable List<Task> tasks, @Nullable Integer sortId) {
        if (projects == null || tasks == null) {
            return;
        }

        List<TasksViewState> tasksViewStates = new ArrayList<>();

        for (Task task : tasks) {
            for (Project project : projects) {
                if (project.getId() == task.getProjectId()) {
                    tasksViewStates.add(new TasksViewState(
                            task.getId(),
                            task.getName(),
                            project.getColor())
                    );
                    break;
                }
            }
        }

        if (tasksViewStates.isEmpty()) {
            isListEmptyLiveData.setValue(0);
        } else {
            isListEmptyLiveData.setValue(4);
        }

        tasksViewStates.sort(Comparator.comparing(TasksViewState::getTaskName));

        if (sortId != null) {
            if (sortId == R.id.sort_by_name) {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getTaskName));
            } else if (sortId == R.id.sort_by_date) {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getId).reversed());
            } else {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getColor));
            }
        }

        viewStateLiveData.setValue(tasksViewStates);
    }

    public void setFilterIdLiveData(int sortId) {
        sortIdLiveData.setValue(sortId);
    }

    public LiveData<List<TasksViewState>> getAllTasks() {
        return viewStateLiveData;
    }

    public LiveData<Integer> getIfListIsEmpty() {
        return isListEmptyLiveData;
    }

    public void deleteTask(Long id) {
        executor.submit(() -> {
            taskRepository.deleteTask(id);
        });
    }
}
