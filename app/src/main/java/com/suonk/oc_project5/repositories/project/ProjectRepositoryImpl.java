package com.suonk.oc_project5.repositories.project;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.suonk.oc_project5.model.dao.ProjectDao;
import com.suonk.oc_project5.model.data.Project;

import java.util.List;

import javax.inject.Inject;

public class ProjectRepositoryImpl implements ProjectRepository {

    @NonNull
    private final ProjectDao dao;

    @Inject
    public ProjectRepositoryImpl(@NonNull ProjectDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Project>> getAllProjects() {
        return dao.getAllProjects();
    }

    @Override
    public LiveData<Project> getProjectById(Long id) {
        return dao.getProjectById(id);
    }

    @Override
    public void insertProject(Project project) {
        dao.insertProject(project);
    }
}