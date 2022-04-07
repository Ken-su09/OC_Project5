package com.suonk.oc_project5.repositories.task;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project5.model.dao.TaskDao;
import com.suonk.oc_project5.model.data.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskDao taskDao = Mockito.mock(TaskDao.class);
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = new TaskRepositoryImpl(taskDao);
    }

    @Test
    public void getAllTasks() {
        LiveData<List<Task>> tasksLiveData = new MutableLiveData<>();
        when(taskRepository.getAllTasks()).thenReturn(tasksLiveData);

        LiveData<List<Task>> listOfTasks = taskRepository.getAllTasks();

        assertEquals(tasksLiveData, listOfTasks);
        Mockito.verify(taskDao).getAllTasks();
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void getTaskById() {
        LiveData<Task> taskLiveDate = new MutableLiveData<>();
        when(taskRepository.getTaskById(1L)).thenReturn(taskLiveDate);

        LiveData<Task> task = taskRepository.getTaskById(1L);

        assertEquals(taskLiveDate, task);
        Mockito.verify(taskDao).getTaskById(1L);
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void insertTask() {
        taskRepository.insertTask(new Task(0, 1L, "task1"));

        Mockito.verify(taskDao).insertTask(new Task(0, 1L, "task1"));
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void updateTask() {
        Task task = new Task(0, 1L, "task1");
        taskRepository.updateTask(task);

        Mockito.verify(taskDao).updateTask(task);
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void deleteTaskById() {
        taskRepository.deleteTask(1L);

        Mockito.verify(taskDao).deleteTask(1L);
        Mockito.verifyNoMoreInteractions(taskDao);
    }
}