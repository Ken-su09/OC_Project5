package com.suonk.oc_project5.di;

import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.project.ProjectRepositoryImpl;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.repositories.task.TaskRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public abstract class AppBindings {

    @Binds
    abstract TaskRepository bindTaskRepository(TaskRepositoryImpl impl);


    @Binds
    abstract ProjectRepository bindProjectRepository(ProjectRepositoryImpl impl);
}
