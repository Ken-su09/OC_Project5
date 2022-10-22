package com.suonk.oc_project5.repositories.task;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project5.model.dao.TaskDao;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.utils.TestUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskRepository taskRepository;

    //region ============================================= MOCK =============================================

    private final TaskDao taskDao = Mockito.mock(TaskDao.class);

    //endregion

    //region ======================================== DEFAULT VALUES ========================================

    private static final long PROJECT_ID_1 = 1;
    private static final long PROJECT_ID_2 = 2;
    private static final long PROJECT_ID_3 = 3;

    private static final long TASK_ID_1 = 1;
    private static final long TASK_ID_2 = 2;
    private static final long TASK_ID_3 = 3;
    private static final long TASK_ID_4 = 4;
    private static final long TASK_ID_5 = 5;
    private static final long TASK_ID_6 = 6;
    private static final long TASK_ID_7 = 7;
    private static final long TASK_ID_8 = 8;
    private static final long TASK_ID_9 = 9;

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

    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();
    private final MutableLiveData<Task> taskLiveData = new MutableLiveData<>();

    @Test
    public void getAllTasks() {
        // GIVEN
        doReturn(tasksLiveData).when(taskDao).getAllTasks();
        tasksLiveData.setValue(getDefaultTasks());

        taskRepository = new TaskRepositoryImpl(taskDao);

        // WHEN
        List<Task> tasks = TestUtils.getValueForTesting(taskRepository.getAllTasks());

        // THEN
        assertEquals(getDefaultTasks(), tasks);
        verify(taskDao).getAllTasks();
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void getTaskById() {
        // GIVEN
        doReturn(taskLiveData).when(taskDao).getTaskById(1L);
        taskLiveData.setValue(getDefaultTask_1());

        taskRepository = new TaskRepositoryImpl(taskDao);

        // WHEN
        Task task = TestUtils.getValueForTesting(taskRepository.getTaskById(1L));

        // THEN
        assertEquals(getDefaultTask_1(), task);
        verify(taskDao).getTaskById(1L);
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void insertTask() {
        // GIVEN
        doNothing().when(taskDao).insertTask(getDefaultTask_1());

        taskRepository = new TaskRepositoryImpl(taskDao);

        // WHEN
        taskRepository.insertTask(getDefaultTask_1());

        // THEN
        verify(taskDao).insertTask(getDefaultTask_1());
        verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void deleteTaskById() {
        // GIVEN
        doNothing().when(taskDao).deleteTask(TASK_ID_1);

        taskRepository = new TaskRepositoryImpl(taskDao);

        // WHEN
        taskRepository.deleteTask(TASK_ID_1);

        // THEN
        verify(taskDao).deleteTask(TASK_ID_1);
        verifyNoMoreInteractions(taskDao);
    }

    //region ======================================== DEFAULT LISTS =========================================

    @NonNull
    private List<Task> getDefaultTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.add(getDefaultTask_1());
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
    private Task getDefaultTask_1() {
        return new Task(TASK_ID_1, PROJECT_ID_1, TASK_NAME_1);
    }

    //endregion
}