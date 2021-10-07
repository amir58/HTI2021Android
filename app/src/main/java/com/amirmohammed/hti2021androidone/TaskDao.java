package com.amirmohammed.hti2021androidone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

 @Query("SELECT * FROM tasks")
 List<Task> getTasks();

 @Insert
 void insertTask(Task task);

 @Update
 void updateTask(Task task);

 @Delete
 void deleteTask(Task task);

}
