package com.cleanup.todoc.database.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.database.dao.dao.ProjectDao;
import com.cleanup.todoc.database.dao.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    // --- SINGLETON ---

    private static volatile SaveMyTripDatabase INSTANCE;

    // --- DAO ---

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();

    public SaveMyTripDatabase() {

    }
    // --- INSTANCE ---

    public static SaveMyTripDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (SaveMyTripDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            SaveMyTripDatabase.class, "MyDatabase.db")
                            .allowMainThreadQueries()
                            .build();

                }

            }

        }

        return INSTANCE;

    }


}