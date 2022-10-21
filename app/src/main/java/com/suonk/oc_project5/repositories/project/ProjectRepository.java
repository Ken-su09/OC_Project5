package com.suonk.oc_project5.repositories.project;

import androidx.lifecycle.LiveData;

import com.suonk.oc_project5.model.data.Project;

import java.util.List;

public interface ProjectRepository {

    LiveData<List<Project>> getAllProjects();

    LiveData<Project> getProjectById(Long id);

    void insertProject(Project project);
}
