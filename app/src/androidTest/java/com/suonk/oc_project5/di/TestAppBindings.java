package com.suonk.oc_project5.di;

import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.project.ProjectRepositoryImpl;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.repositories.task.TaskRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.testing.TestInstallIn;

@TestInstallIn(components = SingletonComponent.class,
        replaces = AppBindings.class)
@Module
public abstract class TestAppBindings {

    @Binds
    abstract TaskRepository bindTaskRepository(TaskRepositoryImpl impl);


    @Binds
    abstract ProjectRepository bindProjectRepository(ProjectRepositoryImpl impl);
}
