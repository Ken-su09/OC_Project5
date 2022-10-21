package com.suonk.oc_project5.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;

import java.util.List;

@Dao
public interface ProjectDao {


    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM project WHERE id == :id")
    LiveData<Project> getProjectById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);
}
