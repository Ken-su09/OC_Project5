package com.suonk.oc_project5.repositories.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.dao.ProjectDao;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ProjectRepository projectRepository;

    //region ============================================= MOCK =============================================

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);

    //endregion

    //region ======================================== DEFAULT VALUES ========================================

    private static final long PROJECT_ID_1 = 1;
    private static final String PROJECT_NAME_1 = "Project Tartampion";
    private static final int PROJECT_COLOR_1 = R.drawable.ic_circle_beige;

    private static final long PROJECT_ID_2 = 2;
    private static final String PROJECT_NAME_2 = "Project Lucidia";
    private static final int PROJECT_COLOR_2 = R.drawable.ic_circle_green;

    private static final long PROJECT_ID_3 = 3;
    private static final String PROJECT_NAME_3 = "Project Circus";
    private static final int PROJECT_COLOR_3 = R.drawable.ic_circle_blue;

    //endregion

    private final MutableLiveData<List<Project>> projectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Project> projectLiveData = new MutableLiveData<>();

    @Test
    public void getAllProjects() {
        // GIVEN
        doReturn(projectsLiveData).when(projectDao).getAllProjects();
        projectsLiveData.setValue(getDefaultProjects());

        projectRepository = new ProjectRepositoryImpl(projectDao);

        // WHEN
        List<Project> projects = TestUtils.getValueForTesting(projectRepository.getAllProjects());

        // THEN
        assertEquals(getDefaultProjects(), projects);
        verify(projectDao).getAllProjects();
        verifyNoMoreInteractions(projectDao);
    }

    @Test
    public void getProjectById() {
        // GIVEN
        doReturn(projectLiveData).when(projectDao).getProjectById(PROJECT_ID_1);
        projectLiveData.setValue(getDefaultProject_1());

        projectRepository = new ProjectRepositoryImpl(projectDao);

        // WHEN
        Project project = TestUtils.getValueForTesting(projectRepository.getProjectById(PROJECT_ID_1));

        // THEN
        assertEquals(getDefaultProject_1(), project);
        verify(projectDao).getProjectById(PROJECT_ID_1);
        verifyNoMoreInteractions(projectDao);
    }

    @Test
    public void insertProject() {
        // GIVEN
        doNothing().when(projectDao).insertProject(getDefaultProject_1());

        projectRepository = new ProjectRepositoryImpl(projectDao);

        // WHEN
        projectRepository.insertProject(getDefaultProject_1());

        // THEN
        verify(projectDao).insertProject(getDefaultProject_1());
        verifyNoMoreInteractions(projectDao);
    }

    //region ======================================== DEFAULT LISTS =========================================

    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();

        projects.add(getDefaultProject_1());
        projects.add(new Project(PROJECT_ID_2, PROJECT_NAME_2, PROJECT_COLOR_2));
        projects.add(new Project(PROJECT_ID_3, PROJECT_NAME_3, PROJECT_COLOR_3));

        return projects;
    }

    @NonNull
    private Project getDefaultProject_1() {
        return new Project(PROJECT_ID_1, PROJECT_NAME_1, PROJECT_COLOR_1);
    }

    //endregion
}