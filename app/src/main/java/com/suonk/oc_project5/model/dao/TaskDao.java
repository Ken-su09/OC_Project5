package com.suonk.oc_project5.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.suonk.oc_project5.model.data.Task;

import java.util.List;

@Dao
public interface TaskDao {

    /**
     *  getAllTasks() = task1, task2, task3....
     */
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    /**
     *  getTaskById(id) = task
     */
    @Query("SELECT * FROM task WHERE id == :id")
    LiveData<Task> getTaskById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);
}
