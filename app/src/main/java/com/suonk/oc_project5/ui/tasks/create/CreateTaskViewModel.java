package com.suonk.oc_project5.ui.tasks.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTaskViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final Application context;

    @NonNull
    private final Executor executor;

    @NonNull
    private final SingleLiveEvent<String> toastSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final SingleLiveEvent<Boolean> insertTaskValidLiveEvent = new SingleLiveEvent<>();

    @Inject
    public CreateTaskViewModel(
            @NonNull Application context,
            @NonNull TaskRepository taskRepository,
            @NonNull ProjectRepository projectRepository,
            @NonNull Executor executor
    ) {
        this.context = context;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    public LiveData<List<CreateTaskViewState>> getViewStatesLiveData() {
        return Transformations.map(projectRepository.getAllProjects(), projects -> {
                    List<CreateTaskViewState> createTaskViewStateList = new ArrayList<>();

                    for (Project project : projects) {
                        createTaskViewStateList.add(new CreateTaskViewState(
                                project.getId(), project.getName()
                        ));
                    }

                    return createTaskViewStateList;
                }
        );
    }

    public void insertNewTask(long projectId, @Nullable String taskName) {
        if (taskName == null || taskName.isEmpty()) {
            toastSingleLiveEvent.setValue(context.getString(R.string.create_task_dialog_empty_task_name_error));
            insertTaskValidLiveEvent.setValue(false);
        } else {
            executor.execute(() -> {
                taskRepository.insertTask(new Task(0, projectId, taskName));
            });
            insertTaskValidLiveEvent.setValue(true);
        }
    }

    @NonNull
    public SingleLiveEvent<String> getToastSingleLiveEvent() {
        return toastSingleLiveEvent;
    }

    @NonNull
    public SingleLiveEvent<Boolean> getInsertTaskValidLiveEvent() {
        return insertTaskValidLiveEvent;
    }
}