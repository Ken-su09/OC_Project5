package com.suonk.oc_project5.ui.tasks.create;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.utils.TestExecutor;
import com.suonk.oc_project5.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CreateTaskViewModelTest {

    private final Application application = Mockito.mock(Application.class);

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor executor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<Project>> projectsMutableLiveData = new MutableLiveData<>();

    public CreateTaskViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn("create_task_dialog_empty_task_name_error").when(application).getString(R.string.create_task_dialog_empty_task_name_error);
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getAllProjects();

        projectsMutableLiveData.setValue(getDefaultProjects());
        viewModel = new CreateTaskViewModel(application, taskRepository, projectRepository, executor);
    }

    @Test
    public void get_live_data_list_of_projects() {
        // When
        List<CreateTaskViewState> createTaskViewStates = TestUtils.getValueForTesting(viewModel.getViewStatesLiveData());

        assertEquals(3, createTaskViewStates.size());
        assertEquals(getDefaultViewStateProjects(), createTaskViewStates);

        Mockito.verify(projectRepository).getAllProjects();
        Mockito.verifyNoMoreInteractions(projectRepository);
    }

    @Test
    public void when_try_to_insert_task_with_empty_task_name_should_display_dialog_create_task_dialog_empty_task_name_error() {
        // When
        viewModel.insertNewTask(1, "");
        String toastMessage = TestUtils.getValueForTesting(viewModel.getToastSingleLiveEvent());
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // Then
        assertEquals(application.getString(R.string.create_task_dialog_empty_task_name_error), toastMessage);
        assertEquals(false, dismissDialog);

        Mockito.verify(taskRepository, Mockito.never()).insertTask(any());
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void when_try_to_insert_task_with_null_task_name_should_display_dialog_create_task_dialog_empty_task_name_error() {
        // When
        viewModel.insertNewTask(1, null);
        String toastMessage = TestUtils.getValueForTesting(viewModel.getToastSingleLiveEvent());
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // Then
        assertEquals(application.getString(R.string.create_task_dialog_empty_task_name_error), toastMessage);
        assertEquals(false, dismissDialog);

        Mockito.verify(taskRepository, Mockito.never()).insertTask(any());
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void after_successfully_insert_task_should_dismiss_dialog() {
        // When
        viewModel.insertNewTask(1, "test");
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // Then
        assertEquals(true, dismissDialog);

        Mockito.verify(taskRepository).insertTask(new Task(0, 1, "test"));
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();

        projects.add(new Project(1, "Project Tartampion", R.drawable.ic_circle_beige));
        projects.add(new Project(2, "Project Lucidia", R.drawable.ic_circle_green));
        projects.add(new Project(3, "Project Circus", R.drawable.ic_circle_blue));

        return projects;
    }

    @NonNull
    private List<CreateTaskViewState> getDefaultViewStateProjects() {
        List<CreateTaskViewState> projects = new ArrayList<>();

        projects.add(new CreateTaskViewState(1, "Project Tartampion"));
        projects.add(new CreateTaskViewState(2, "Project Lucidia"));
        projects.add(new CreateTaskViewState(3, "Project Circus"));

        return projects;
    }
}