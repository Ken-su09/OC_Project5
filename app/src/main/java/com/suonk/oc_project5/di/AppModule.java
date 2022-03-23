package com.suonk.oc_project5.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.suonk.oc_project5.model.AppDatabase;
import com.suonk.oc_project5.model.dao.ProjectDao;
import com.suonk.oc_project5.model.dao.TaskDao;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.project.ProjectRepositoryImpl;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.repositories.task.TaskRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Provides
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "app_database")
                .allowMainThreadQueries()
                .addMigrations()
                .build();
    }

    @Provides
    public TaskDao provideTaskDao(@NonNull AppDatabase database) {
        return database.taskDao();
    }

    @Provides
    public TaskRepository provideTaskRepository(@NonNull TaskDao dao) {
        return new TaskRepositoryImpl(dao);
    }

    @Provides
    public ProjectDao provideProjectDao(@NonNull AppDatabase database) {
        return database.projectDao();
    }

    @Provides
    public ProjectRepository provideProjectRepository(@NonNull ProjectDao dao) {
        return new ProjectRepositoryImpl(dao);
    }
}
