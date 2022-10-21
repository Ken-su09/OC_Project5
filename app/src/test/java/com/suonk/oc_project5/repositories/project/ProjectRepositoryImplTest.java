package com.suonk.oc_project5.repositories.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project5.model.dao.ProjectDao;
import com.suonk.oc_project5.model.data.Project;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        projectRepository = new ProjectRepositoryImpl(projectDao);
    }

    @Test
    public void getAllProjects() {
        LiveData<List<Project>> projectsLiveData = new MutableLiveData<>();
        when(projectRepository.getAllProjects()).thenReturn(projectsLiveData);

        LiveData<List<Project>> listOfProjects = projectRepository.getAllProjects();

        assertEquals(projectsLiveData, listOfProjects);
        Mockito.verify(projectDao).getAllProjects();
        Mockito.verifyNoMoreInteractions(projectDao);
    }

    @Test
    public void getProjectById() {
        LiveData<Project> projectLiveData = new MutableLiveData<>();
        when(projectRepository.getProjectById(1L)).thenReturn(projectLiveData);

        LiveData<Project> project = projectRepository.getProjectById(1L);

        assertEquals(projectLiveData, project);
        Mockito.verify(projectDao).getProjectById(1L);
        Mockito.verifyNoMoreInteractions(projectDao);
    }

    @Test
    public void insertProject() {
        projectRepository.insertProject(new Project(0, "task1", 0));

        Mockito.verify(projectDao).insertProject(new Project(0, "task1", 0));
        Mockito.verifyNoMoreInteractions(projectDao);
    }
}