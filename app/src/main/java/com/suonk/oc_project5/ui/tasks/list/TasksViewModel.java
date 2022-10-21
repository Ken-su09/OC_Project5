package com.suonk.oc_project5.ui.tasks.list;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@RequiresApi(api = Build.VERSION_CODES.N)
@HiltViewModel
public class TasksViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final Executor executor;

    private final MediatorLiveData<List<TasksViewState>> viewStateLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Integer> sortIdLiveData = new MutableLiveData<>(R.id.sort_by_date);
    private final MutableLiveData<Integer> isListEmptyLiveData = new MutableLiveData<>(0);

    @Inject
    public TasksViewModel(@NonNull ProjectRepository projectRepository,
                          @NonNull TaskRepository taskRepository,
                          @NonNull Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;

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
        List<TasksViewState> tasksViewStates = new ArrayList<>();

        if (projects == null || tasks == null) {
            viewStateLiveData.setValue(tasksViewStates);
            return;
        }

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

        if (sortId != null) {
            if (sortId == R.id.sort_by_name) {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getTaskName));
            } else if (sortId == R.id.sort_by_date) {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getId).reversed());
            } else if (sortId == R.id.sort_by_project) {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getColor));
            } else {
                tasksViewStates.sort(Comparator.comparing(TasksViewState::getId).reversed());
            }
        } else {
            tasksViewStates.sort(Comparator.comparing(TasksViewState::getId).reversed());
        }

        viewStateLiveData.setValue(tasksViewStates);
    }

    public void setFilterIdLiveData(Integer sortId) {
        sortIdLiveData.setValue(sortId);
    }

    @NonNull
    public LiveData<List<TasksViewState>> getAllTasks() {
        return viewStateLiveData;
    }

    @NonNull
    public LiveData<Integer> getIfListIsEmpty() {
        return isListEmptyLiveData;
    }

    public void deleteTask(Long id) {
        executor.execute(() -> taskRepository.deleteTask(id));
    }
}
