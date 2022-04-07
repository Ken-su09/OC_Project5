package com.suonk.oc_project5.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.model.AppDatabase;
import com.suonk.oc_project5.model.dao.ProjectDao;
import com.suonk.oc_project5.model.dao.TaskDao;
import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;
import com.suonk.oc_project5.repositories.project.ProjectRepository;
import com.suonk.oc_project5.repositories.project.ProjectRepositoryImpl;
import com.suonk.oc_project5.repositories.task.TaskRepository;
import com.suonk.oc_project5.repositories.task.TaskRepositoryImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Singleton
    @Provides
    public AppDatabase provideDatabase(@ApplicationContext Context context,
                                       Provider<ProjectDao> providerDao) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "app_database")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newFixedThreadPool(10).submit(() -> prepopulateDatabase(providerDao.get()));
                    }

                    public void prepopulateDatabase(ProjectDao projectDao) {
                        projectDao.insertProject(new Project(0, "Project Tartampion", R.drawable.ic_circle_beige));
                        projectDao.insertProject(new Project(0, "Project Lucidia", R.drawable.ic_circle_green));
                        projectDao.insertProject(new Project(0, "Project Circus", R.drawable.ic_circle_blue));
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    public TaskDao provideTaskDao(@NonNull AppDatabase database) {
        return database.taskDao();
    }

    @Singleton
    @Provides
    public ProjectDao provideProjectDao(@NonNull AppDatabase database) {
        return database.projectDao();
    }

    @Singleton
    @Provides
    public Executor provideExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
