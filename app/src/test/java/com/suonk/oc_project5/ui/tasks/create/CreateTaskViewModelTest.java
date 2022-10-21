package com.suonk.oc_project5.ui.tasks.create;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class CreateTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public CreateTaskViewModel viewModel;

    //region ============================================= MOCK =============================================

    private final Application application = Mockito.mock(Application.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final Executor executor = Mockito.spy(new TestExecutor());

    //endregion

    //region ======================================== DEFAULT VALUES ========================================

    private static final int PROJECT_ID_1 = 1;
    private static final String PROJECT_NAME_1 = "Project Tartampion";
    private static final int PROJECT_COLOR_1 = R.drawable.ic_circle_beige;

    private static final int PROJECT_ID_2 = 2;
    private static final String PROJECT_NAME_2 = "Project Lucidia";
    private static final int PROJECT_COLOR_2 = R.drawable.ic_circle_green;

    private static final int PROJECT_ID_3 = 3;
    private static final String PROJECT_NAME_3 = "Project Circus";
    private static final int PROJECT_COLOR_3 = R.drawable.ic_circle_blue;

    private static final String TASK_NAME_1 = "TASK_NAME_1";
    private static final String EMPTY_TASK_NAME = "";

    private static final String CREATE_TASK_EMPTY_MESSAGE = "Task name should not be empty";

    //endregion

    private final MutableLiveData<List<Project>> projectsMutableLiveData = new MutableLiveData<>();

    @Before
    public void setup() {
        Mockito.doReturn(CREATE_TASK_EMPTY_MESSAGE).when(application).getString(R.string.create_task_dialog_empty_task_name_error);
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getAllProjects();

        projectsMutableLiveData.setValue(getDefaultProjects());
        viewModel = new CreateTaskViewModel(application, taskRepository, projectRepository, executor);
    }

    @Test
    public void get_live_data_list_of_projects() {
        // WHEN
        List<CreateTaskViewState> createTaskViewStates = TestUtils.getValueForTesting(viewModel.getViewStatesLiveData());

        // THEN
        assertEquals(3, createTaskViewStates.size());
        assertEquals(getDefaultViewStateProjects(), createTaskViewStates);

        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    public void try_to_insert_task_with_empty_task_name_should_display_dialog_create_task_dialog_empty_task_name_error() {
        // WHEN
        viewModel.insertNewTask(PROJECT_ID_1, EMPTY_TASK_NAME);
        String toastMessage = TestUtils.getValueForTesting(viewModel.getToastSingleLiveEvent());
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // THEN
        assertEquals(application.getString(R.string.create_task_dialog_empty_task_name_error), toastMessage);
        assertEquals(false, dismissDialog);

        verify(taskRepository, Mockito.never()).insertTask(any());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void try_to_insert_task_with_null_task_name_should_display_dialog_create_task_dialog_empty_task_name_error() {
        // WHEN
        viewModel.insertNewTask(PROJECT_ID_1, null);
        String toastMessage = TestUtils.getValueForTesting(viewModel.getToastSingleLiveEvent());
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // THEN
        assertEquals(application.getString(R.string.create_task_dialog_empty_task_name_error), toastMessage);
        assertEquals(false, dismissDialog);

        verify(taskRepository, Mockito.never()).insertTask(any());
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void after_successfully_insert_task_should_dismiss_dialog() {
        // When
        viewModel.insertNewTask(PROJECT_ID_1, TASK_NAME_1);
        Boolean dismissDialog = TestUtils.getValueForTesting(viewModel.getInsertTaskValidLiveEvent());

        // Then
        assertEquals(true, dismissDialog);

        verify(taskRepository).insertTask(new Task(0, PROJECT_ID_1, TASK_NAME_1));
        verifyNoMoreInteractions(taskRepository);
    }

    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();

        projects.add(new Project(PROJECT_ID_1, PROJECT_NAME_1, PROJECT_COLOR_1));
        projects.add(new Project(PROJECT_ID_2, PROJECT_NAME_2, PROJECT_COLOR_2));
        projects.add(new Project(PROJECT_ID_3, PROJECT_NAME_3, PROJECT_COLOR_3));

        return projects;
    }

    @NonNull
    private List<CreateTaskViewState> getDefaultViewStateProjects() {
        List<CreateTaskViewState> projects = new ArrayList<>();

        projects.add(new CreateTaskViewState(PROJECT_ID_1, PROJECT_NAME_1));
        projects.add(new CreateTaskViewState(PROJECT_ID_2, PROJECT_NAME_2));
        projects.add(new CreateTaskViewState(PROJECT_ID_3, PROJECT_NAME_3));

        return projects;
    }
}