package com.suonk.oc_project5.ui.tasks.create;

import static org.junit.Assert.*;

import android.app.Application;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.utils.TestExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.Executor;

public class CreateTaskViewModelTest {

    private Application application = Mockito.mock(Application.class);

    private TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    private Executor executor = new TestExecutor();

    private CreateTaskViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn("create_task_dialog_empty_task_name_error").when(application).getString(R.string.create_task_dialog_empty_task_name_error);

        viewModel = new CreateTaskViewModel(application, taskRepository, projectRepository, executor);
    }

    @Test
    public void nominal_case() {

        // When
        viewModel.insertNewTask(666, "taskName");

        // Then
        Mockito.verify(taskRepository).insertTask(new Task(0, 666, "taskName"));
        Mockito.verifyNoMoreInteractions(taskRepository);
    }
}