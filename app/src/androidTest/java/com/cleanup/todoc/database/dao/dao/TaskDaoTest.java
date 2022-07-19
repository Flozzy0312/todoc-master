package com.cleanup.todoc.database.dao.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.dao.SaveMyTripDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TaskDaoTest {
    private static final long PROJECT1  = 1;
    private static final long PROJECT2  = 2;
    private SaveMyTripDatabase database;
    private TaskDao taskDao;


    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, SaveMyTripDatabase.class).build();
        taskDao = database.taskDao();
        taskDao.insertTask(new Task(1, PROJECT1, "Task 1", System.currentTimeMillis() ));
        taskDao.insertTask(new Task(2, PROJECT1, "Task 2", System.currentTimeMillis() ));
        taskDao.insertTask(new Task(3, PROJECT2, "Task 3", System.currentTimeMillis() ));
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void getTasksByProject() {
        List<Task> tasks = taskDao.getTasksByProject(1);
        assertEquals(tasks.size(),2);
        assertEquals(tasks.get(0).getId(), 1);
        assertEquals(tasks.get(1).getId(), 2);
    }

    @Test
    public void getAllTasks() {
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(tasks.size(),3);
    }

    @Test
    public void insertTask() {
        int initialTasksSize = taskDao.getAllTasks().size();
        Task task = new Task(5, PROJECT1, "New Task", System.currentTimeMillis() );
        taskDao.insertTask(task);
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(tasks.size(),initialTasksSize+1);
        assertTrue(tasks.contains(task));
    }

    @Test
    public void updateTask() {
        Task task = taskDao.getAllTasks().get(0);
        task.setName("New Name");
        taskDao.updateTask(task);
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(tasks.size(), 3);
        assertTrue(tasks.contains(task));
    }

    @Test
    public void deleteTask() {
        Task task = taskDao.getAllTasks().get(0);
        taskDao.deleteTask(task.getId());
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(tasks.size(), 2);
        assertFalse(tasks.contains(task));
    }
}