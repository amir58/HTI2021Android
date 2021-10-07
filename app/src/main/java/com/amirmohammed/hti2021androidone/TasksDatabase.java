package com.amirmohammed.hti2021androidone;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

//    private TasksDatabase(){}

    private static final String DB_NAME = "appDatabase.db";
    private static volatile TasksDatabase instance;

    static synchronized TasksDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static TasksDatabase create(final Context context) {
        return Room.databaseBuilder(context, TasksDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

}
