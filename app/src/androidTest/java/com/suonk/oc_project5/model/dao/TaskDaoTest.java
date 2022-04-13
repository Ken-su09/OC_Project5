package com.suonk.oc_project5.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.AppDatabase;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private AppDatabase database;
    private TaskDao taskDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDatabase() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        taskDao = database.taskDao();

        database.projectDao().insertProject(new Project(1L, "Project Tartampion", R.drawable.ic_circle_beige));
        database.projectDao().insertProject(new Project(2L, "Project Lucidia", R.drawable.ic_circle_green));
        database.projectDao().insertProject(new Project(3L, "Project Circus", R.drawable.ic_circle_blue));
    }

    @After
    public void closeDatabase() throws Exception {
        database.close();
    }

    @Test
    public void insert_and_get_one_task() throws InterruptedException {
        Task taskTest = new Task(1L, 1, "test1");
        taskDao.insertTask(taskTest);
        Task task = LiveDataTestUtil.getValue(taskDao.getTaskById(1L));

        assertEquals(taskTest, task);
    }

    @Test
    public void insert_two_and_get_two_tasks() throws InterruptedException {
        Task taskTest1 = new Task(1L, 1, "test1");
        Task taskTest2 = new Task(2L, 2, "test2");

        taskDao.insertTask(taskTest1);
        taskDao.insertTask(taskTest2);

        Task task1 = LiveDataTestUtil.getValue(taskDao.getTaskById(1L));
        Task task2 = LiveDataTestUtil.getValue(taskDao.getTaskById(2L));

        assertEquals(task1, taskTest1);
        assertEquals(task2, taskTest2);
    }

    @Test
    public void insert_two_and_get_all_tasks() throws InterruptedException {
        Task taskTest1 = new Task(1L, 1, "test1");
        Task taskTest2 = new Task(2L, 2, "test2");

        taskDao.insertTask(taskTest1);
        taskDao.insertTask(taskTest2);

        List<Task> tasks = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals(Arrays.asList(taskTest1, taskTest2), tasks);
    }

    @Test
    public void insert_and_delete() throws InterruptedException {
        Task taskTest1 = new Task(1L, 1L, "test1");

        taskDao.insertTask(taskTest1);
        taskDao.deleteTask(1L);

        List<Task> tasks = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals(0, tasks.size());
    }

    @Test
    public void insert_two_then_delete_one_and_get_all_tasks_should_contain_one() throws InterruptedException {
        Task taskTest1 = new Task(1L, 1L, "test1");
        Task taskTest2 = new Task(2L, 2L, "test2");

        taskDao.insertTask(taskTest1);
        taskDao.insertTask(taskTest2);

        taskDao.deleteTask(2L);

        List<Task> tasks = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals(1, tasks.size());
    }

    @Test
    public void insert_then_update_task() throws InterruptedException {
        Task taskTest1 = new Task(1L, 1, "test1");
        taskDao.insertTask(taskTest1);
        Task task = LiveDataTestUtil.getValue(taskDao.getTaskById(1L));

        assertEquals(taskTest1, task);

        taskDao.updateTask(new Task(1L, 1, "test1"));

        assertNotSame(taskTest1, task);
    }
}