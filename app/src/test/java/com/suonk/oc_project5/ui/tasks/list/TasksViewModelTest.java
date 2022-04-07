package com.suonk.oc_project5.ui.tasks.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class TasksViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final Executor executor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<Task>> tasksMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> projectsMutableLiveData = new MutableLiveData<>();

    private TasksViewModel viewModel;

    @Before
    public void setup() {
        Mockito.doReturn(tasksMutableLiveData).when(taskRepository).getAllTasks();
        Mockito.doReturn(projectsMutableLiveData).when(projectRepository).getAllProjects();

        tasksMutableLiveData.setValue(getDefaultTasks());
        projectsMutableLiveData.setValue(getDefaultProjects());

        viewModel = new TasksViewModel(projectRepository, taskRepository, executor);
    }

    @Test
    public void initialCase() {
        // When
        tasksMutableLiveData.setValue(new ArrayList<>());
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(0, listOfTasks.size());

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void nominal_case() {
        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(9, listOfTasks.size());
        assertEquals(getDefaultTasksViewState(), listOfTasks);

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void test_tasks_null_and_projects_not_null() {
        tasksMutableLiveData.setValue(null);

//        liveData.observeForever(t -> {
//        });

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(0, listOfTasks.size());

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void test_projects_null_and_tasks_not_null() {
        projectsMutableLiveData.setValue(null);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(0, listOfTasks.size());

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void test_projects_and_tasks_null() {
        projectsMutableLiveData.setValue(null);
        tasksMutableLiveData.setValue(null);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(0, listOfTasks.size());

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void get_all_tasks_sort_by_name() {
        viewModel.setFilterIdLiveData(R.id.sort_by_name);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(getDefaultTasksViewStateSortByName(), listOfTasks);

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void get_all_tasks_sort_by_date() {
        viewModel.setFilterIdLiveData(R.id.sort_by_date);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(getDefaultTasksViewStateSortByDate(), listOfTasks);

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void get_all_tasks_sort_by_project() {
        viewModel.setFilterIdLiveData(R.id.sort_by_project);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(getDefaultTasksViewStateSortByProject(), listOfTasks);

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void get_all_tasks_sort_by_random_sort_id_should_sort_by_name_by_default() {
        viewModel.setFilterIdLiveData(313546809);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(viewModel.getAllTasks());

        assertEquals(getDefaultTasksViewStateSortByName(), listOfTasks);

        // Then
        verify(taskRepository).getAllTasks();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void if_list_is_empty_should_return_0() {
        // When
        tasksMutableLiveData.setValue(new ArrayList<>());
        TestUtils.getValueForTesting(viewModel.getAllTasks());
        int listEmpty = TestUtils.getValueForTesting(viewModel.getIfListIsEmpty());

        assertEquals(0, listEmpty);
    }

    @Test
    public void if_list_is_not_empty_should_return_4() {
        // When
        TestUtils.getValueForTesting(viewModel.getAllTasks());
        int listEmpty = TestUtils.getValueForTesting(viewModel.getIfListIsEmpty());

        assertEquals(4, listEmpty);
    }

    @Test
    public void delete_task_from_list() {
        // When
        viewModel.deleteTask(1L);

        // Then
        verify(taskRepository).deleteTask(1L);
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
    private List<Task> getDefaultTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task(1, 1, "task1"));
        tasks.add(new Task(2, 2, "task2"));
        tasks.add(new Task(3, 3, "task3"));
        tasks.add(new Task(4, 1, "task4"));
        tasks.add(new Task(5, 2, "task5"));
        tasks.add(new Task(6, 3, "task6"));
        tasks.add(new Task(7, 1, "task7"));
        tasks.add(new Task(8, 2, "task8"));
        tasks.add(new Task(9, 3, "task9"));

        return tasks;
    }

    @NonNull
    private List<TasksViewState> getDefaultTasksViewState() {
        List<TasksViewState> tasksViewState = new ArrayList<>();

        tasksViewState.add(new TasksViewState(1, "task1", R.drawable.ic_circle_beige));
        tasksViewState.add(new TasksViewState(2, "task2", R.drawable.ic_circle_green));
        tasksViewState.add(new TasksViewState(3, "task3", R.drawable.ic_circle_blue));
        tasksViewState.add(new TasksViewState(4, "task4", R.drawable.ic_circle_beige));
        tasksViewState.add(new TasksViewState(5, "task5", R.drawable.ic_circle_green));
        tasksViewState.add(new TasksViewState(6, "task6", R.drawable.ic_circle_blue));
        tasksViewState.add(new TasksViewState(7, "task7", R.drawable.ic_circle_beige));
        tasksViewState.add(new TasksViewState(8, "task8", R.drawable.ic_circle_green));
        tasksViewState.add(new TasksViewState(9, "task9", R.drawable.ic_circle_blue));

        return tasksViewState;
    }

    @NonNull
    private List<TasksViewState> getDefaultTasksViewStateSortByName() {
        List<TasksViewState> tasksViewState = getDefaultTasksViewState();

        tasksViewState.sort(Comparator.comparing(TasksViewState::getTaskName));

        return tasksViewState;
    }

    @NonNull
    private List<TasksViewState> getDefaultTasksViewStateSortByDate() {
        List<TasksViewState> tasksViewState = getDefaultTasksViewState();

        tasksViewState.sort(Comparator.comparing(TasksViewState::getId).reversed());

        return tasksViewState;
    }

    @NonNull
    private List<TasksViewState> getDefaultTasksViewStateSortByProject() {
        List<TasksViewState> tasksViewState = getDefaultTasksViewState();

        tasksViewState.sort(Comparator.comparing(TasksViewState::getColor));

        return tasksViewState;
    }
}