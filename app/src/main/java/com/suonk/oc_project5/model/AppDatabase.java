package com.suonk.oc_project5.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.suonk.oc_project5.model.data.Project;
import com.suonk.oc_project5.model.data.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


}
