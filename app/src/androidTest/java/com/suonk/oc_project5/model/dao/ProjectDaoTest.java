package com.suonk.oc_project5.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.suonk.oc_project5.model.AppDatabase;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private AppDatabase database;
    private ProjectDao projectDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDatabase() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        projectDao = database.projectDao();
    }

    @After
    public void closeDatabase() throws Exception {
        database.close();
    }

    @Test
    public void insert_and_get_one_project() throws InterruptedException {
        Project projectTest = new Project(1L, "test1", 1);
        projectDao.insertProject(projectTest);
        Project project = LiveDataTestUtil.getValue(projectDao.getProjectById(1L));

        assertEquals(projectTest, project);
    }

    @Test
    public void insert_two_and_get_one_project() throws InterruptedException {
        Project projectTest1 = new Project(1L, "test1", 1);
        Project projectTest2 = new Project(2L, "test2", 2);
        projectDao.insertProject(projectTest1);
        projectDao.insertProject(projectTest2);
        Project project = LiveDataTestUtil.getValue(projectDao.getProjectById(2L));

        assertEquals(projectTest2, project);
    }

    @Test
    public void insert_two_and_get_all_projects() throws InterruptedException {
        Project projectTest1 = new Project(1L, "test1", 1);
        Project projectTest2 = new Project(2L, "test2", 2);
        projectDao.insertProject(projectTest1);
        projectDao.insertProject(projectTest2);
        List<Project> projects = LiveDataTestUtil.getValue(projectDao.getAllProjects());

        assertEquals(Arrays.asList(projectTest1, projectTest2), projects);
    }

    @Test
    public void insert_and_delete() throws InterruptedException {
        Project projectTest1 = new Project(1L, "test1", 1);
        projectDao.insertProject(projectTest1);
        projectDao.deleteProject(projectTest1);

        List<Project> projects = LiveDataTestUtil.getValue(projectDao.getAllProjects());

        assertEquals(0, projects.size());
    }

    @Test
    public void insert_two_then_delete_one_and_get_all_projects_should_contain_one() throws InterruptedException {
        Project projectTest1 = new Project(1L, "test1", 1);
        Project projectTest2 = new Project(2L, "test2", 2);
        projectDao.insertProject(projectTest1);
        projectDao.insertProject(projectTest2);

        projectDao.deleteProject(projectTest2);

        List<Project> projects = LiveDataTestUtil.getValue(projectDao.getAllProjects());

        assertEquals(1, projects.size());
    }

    @Test
    public void insert_then_update_project() throws InterruptedException {
        Project projectTest = new Project(1L, "test1", 1);
        projectDao.insertProject(projectTest);
        Project project = LiveDataTestUtil.getValue(projectDao.getProjectById(1L));

        assertEquals(projectTest, project);

        projectDao.updateProject(new Project(1L, "test2", 2));

        assertNotSame(projectTest, project);
    }
}