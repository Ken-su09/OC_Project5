package com.suonk.oc_project5.ui.tasks.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
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

    private TasksViewModel tasksViewModel;

    //region ============================================= MOCK =============================================

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final Executor executor = new TestExecutor();

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

    private static final int TASK_ID_1 = 1;
    private static final int TASK_ID_2 = 2;
    private static final int TASK_ID_3 = 3;
    private static final int TASK_ID_4 = 4;
    private static final int TASK_ID_5 = 5;
    private static final int TASK_ID_6 = 6;
    private static final int TASK_ID_7 = 7;
    private static final int TASK_ID_8 = 8;
    private static final int TASK_ID_9 = 9;

    private static final String TASK_NAME_1 = "TASK_NAME_1";
    private static final String TASK_NAME_2 = "TASK_NAME_2";
    private static final String TASK_NAME_3 = "TASK_NAME_3";
    private static final String TASK_NAME_4 = "TASK_NAME_4";
    private static final String TASK_NAME_5 = "TASK_NAME_5";
    private static final String TASK_NAME_6 = "TASK_NAME_6";
    private static final String TASK_NAME_7 = "TASK_NAME_7";
    private static final String TASK_NAME_8 = "TASK_NAME_8";
    private static final String TASK_NAME_9 = "TASK_NAME_9";

    //endregion

    private final MutableLiveData<List<Task>> tasksMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> projectsMutableLiveData = new MutableLiveData<>();

    @Before
    public void setup() {
        doReturn(tasksMutableLiveData).when(taskRepository).getAllTasks();
        doReturn(projectsMutableLiveData).when(projectRepository).getAllProjects();

        tasksMutableLiveData.setValue(getDefaultTasks());
        projectsMutableLiveData.setValue(getDefaultProjects());

        tasksViewModel = new TasksViewModel(projectRepository, taskRepository, executor);
    }

    //region ======================================== GET ALL TASKS =========================================

    @Test
    public void get_all_tasks_with_empty_tasks() {
        // GIVEN
        tasksMutableLiveData.setValue(new ArrayList<>());

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertEquals(0, listOfTasks.size());
        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks() {
        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertEquals(9, listOfTasks.size());
        assertEquals(getDefaultTasksViewStateSortByDate(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_if_tasks_list_is_null() {
        // GIVEN
        tasksMutableLiveData.setValue(null);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // Then
        assertEquals(0, listOfTasks.size());

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_if_projects_list_is_null() {
        // GIVEN
        projectsMutableLiveData.setValue(null);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // Then
        assertEquals(0, listOfTasks.size());

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_if_projects_and_tasks_lists_are_null() {
        // GIVEN
        tasksMutableLiveData.setValue(null);
        projectsMutableLiveData.setValue(null);

        // When
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // Then
        assertEquals(0, listOfTasks.size());

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    //endregion

    //region ============================================= SORT =============================================

    @Test
    public void get_all_tasks_sort_by_name() {
        // GIVEN
        tasksViewModel.setFilterIdLiveData(R.id.sort_by_name);

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertEquals(getDefaultTasksViewStateSortByName(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_sort_by_date() {
        // GIVEN
        tasksViewModel.setFilterIdLiveData(R.id.sort_by_date);

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertEquals(getDefaultTasksViewStateSortByDate(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_sort_by_project() {
        // GIVEN
        tasksViewModel.setFilterIdLiveData(R.id.sort_by_project);

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertNotNull(listOfTasks);
        assertEquals(getDefaultTasksViewStateSortByProject(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_with_sort_null() {
        // GIVEN
        tasksViewModel.setFilterIdLiveData(null);

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertNotNull(listOfTasks);
        assertEquals(getDefaultTasksViewStateSortByDate(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void get_all_tasks_with_sort_random_value() {
        // GIVEN
        tasksViewModel.setFilterIdLiveData(313546809);

        // WHEN
        List<TasksViewState> listOfTasks = TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // THEN
        assertNotNull(listOfTasks);
        assertEquals(getDefaultTasksViewStateSortByDate(), listOfTasks);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    //endregion

    @Test
    public void if_list_is_empty_should_return_0() {
        // GIVEN
        tasksMutableLiveData.setValue(new ArrayList<>());

        // WHEN
        TestUtils.getValueForTesting(tasksViewModel.getAllTasks());
        int listEmpty = TestUtils.getValueForTesting(tasksViewModel.getIfListIsEmpty());

        // THEN
        assertEquals(0, listEmpty);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void if_list_is_not_empty_should_return_4() {
        // GIVEN
        TestUtils.getValueForTesting(tasksViewModel.getAllTasks());

        // WHEN
        int listEmpty = TestUtils.getValueForTesting(tasksViewModel.getIfListIsEmpty());

        // THEN
        assertEquals(4, listEmpty);

        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    @Test
    public void delete_task_from_list() {
        // WHEN
        tasksViewModel.deleteTask(1L);

        // THEN
        verify(taskRepository).deleteTask(1L);
        verify(taskRepository).getAllTasks();
        verify(projectRepository).getAllProjects();
        verifyNoMoreInteractions(taskRepository, projectRepository);
    }

    //region ======================================== DEFAULT LISTS =========================================

    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();

        projects.add(new Project(PROJECT_ID_1, PROJECT_NAME_1, PROJECT_COLOR_1));
        projects.add(new Project(PROJECT_ID_2, PROJECT_NAME_2, PROJECT_COLOR_2));
        projects.add(new Project(PROJECT_ID_3, PROJECT_NAME_3, PROJECT_COLOR_3));

        return projects;
    }

    @NonNull
    private List<Task> getDefaultTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task(TASK_ID_1, PROJECT_ID_1, TASK_NAME_1));
        tasks.add(new Task(TASK_ID_2, PROJECT_ID_2, TASK_NAME_2));
        tasks.add(new Task(TASK_ID_3, PROJECT_ID_3, TASK_NAME_3));
        tasks.add(new Task(TASK_ID_4, PROJECT_ID_1, TASK_NAME_4));
        tasks.add(new Task(TASK_ID_5, PROJECT_ID_2, TASK_NAME_5));
        tasks.add(new Task(TASK_ID_6, PROJECT_ID_3, TASK_NAME_6));
        tasks.add(new Task(TASK_ID_7, PROJECT_ID_1, TASK_NAME_7));
        tasks.add(new Task(TASK_ID_8, PROJECT_ID_2, TASK_NAME_8));
        tasks.add(new Task(TASK_ID_9, PROJECT_ID_3, TASK_NAME_9));

        return tasks;
    }

    @NonNull
    private List<TasksViewState> getDefaultTasksViewState() {
        List<TasksViewState> tasksViewState = new ArrayList<>();

        tasksViewState.add(new TasksViewState(TASK_ID_1, TASK_NAME_1, PROJECT_COLOR_1));
        tasksViewState.add(new TasksViewState(TASK_ID_2, TASK_NAME_2, PROJECT_COLOR_2));
        tasksViewState.add(new TasksViewState(TASK_ID_3, TASK_NAME_3, PROJECT_COLOR_3));
        tasksViewState.add(new TasksViewState(TASK_ID_4, TASK_NAME_4, PROJECT_COLOR_1));
        tasksViewState.add(new TasksViewState(TASK_ID_5, TASK_NAME_5, PROJECT_COLOR_2));
        tasksViewState.add(new TasksViewState(TASK_ID_6, TASK_NAME_6, PROJECT_COLOR_3));
        tasksViewState.add(new TasksViewState(TASK_ID_7, TASK_NAME_7, PROJECT_COLOR_1));
        tasksViewState.add(new TasksViewState(TASK_ID_8, TASK_NAME_8, PROJECT_COLOR_2));
        tasksViewState.add(new TasksViewState(TASK_ID_9, TASK_NAME_9, PROJECT_COLOR_3));

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

    //endregion
}