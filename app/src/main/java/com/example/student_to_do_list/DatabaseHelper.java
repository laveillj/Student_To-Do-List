// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "tasksAndProjectsDb";

    // Table Names
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_PROJECTS = "projects";

    // Common column names
    private static final String KEY_ID = "id";

    // TASKS Table - column names
    private static final String KEY_TASK = "task";
    private static final String KEY_T_DESC = "description";
    private static final String KEY_T_DEADLINE = "deadline";
    private static final String KEY_T_PROJECT = "projectID";

    // PROJECTS Table - column names
    private static final String KEY_PROJECT = "project";
    private static final String KEY_P_DESC = "description";
    private static final String KEY_P_DEADLINE = "deadline";

    // Table Create Statements
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE "
            + TABLE_TASKS + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT,"
            + KEY_T_DESC + " TEXT," + KEY_T_DEADLINE + " TEXT," + KEY_T_PROJECT + " TEXT)";

    private static final String CREATE_TABLE_PROJECTS = "CREATE TABLE "
            + TABLE_PROJECTS + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROJECT + " TEXT,"
            + KEY_P_DESC + " TEXT," + KEY_P_DEADLINE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_PROJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);

        // create new tables
        onCreate(db);
    }



    //
    // TASKS management functions
    //

    /*
     * Creating a task in db from a Task object
     */
    public long createTask(Task task) {
        Log.d(" ### ### ", "Inside createTask function");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task.getTitle());
        values.put(KEY_T_DESC, task.getDescription());
        values.put(KEY_T_DEADLINE, task.getDeadline());
        values.put(KEY_T_PROJECT, task.getProjectId());

        Log.d(" ### ### ", "At: " + KEY_TASK + " => " + task.getTitle());
        Log.d(" ### ### ", "At: " + KEY_T_PROJECT + " => " + task.getProjectId());
        Log.d(" ### ### ", "values to be inserted: " + values.toString());

        // insert row
        long task_id = db.insert(TABLE_TASKS, null, values);
        Log.d(" ### ", "values to be inserted: " + db.toString());

        Task addedTask = getTask(task_id);
        Log.d(" ### ", "task inserted with name: " + addedTask.getTitle() + " and project ID: "+ addedTask.getProjectId());

        return task_id;
    }

    /*
     * get single task from an id
     */
    public Task getTask(long task_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = " + task_id;

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String title = c.getString(c.getColumnIndex(KEY_TASK));
        String desc = c.getString(c.getColumnIndex(KEY_T_DESC));
        String deadline = c.getString(c.getColumnIndex(KEY_T_DEADLINE));
        long project = c.getInt(c.getColumnIndex(KEY_T_PROJECT));
        Task task = new Task(title, desc, deadline, project);

        task.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        return task;
    }

    /*
     * getting all tasks in db
     * */
    public List<Task> getAllTasks() {
        List<Task> listOfTasks = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(KEY_TASK));
                String desc = c.getString(c.getColumnIndex(KEY_T_DESC));
                String deadline = c.getString(c.getColumnIndex(KEY_T_DEADLINE));
                long project = c.getLong(c.getColumnIndex(KEY_T_PROJECT));
                Task task = new Task(title, desc, deadline, project);
                task.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to tasks list
                listOfTasks.add(task);
            } while (c.moveToNext());
        }
        return listOfTasks;
    }

    /*
     * getting all tasks under a specific project
     * */
    public List<Task> getAllTasksUnderProject(long projectId) {
        List<Task> listOfTasks = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_T_PROJECT + " = " + projectId;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(KEY_TASK));
                String desc = c.getString(c.getColumnIndex(KEY_T_DESC));
                String deadline = c.getString(c.getColumnIndex(KEY_T_DEADLINE));
                long project = c.getLong(c.getColumnIndex(KEY_T_PROJECT));
                Task task = new Task(title, desc, deadline, project);
                task.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to tasks list
                listOfTasks.add(task);
            } while (c.moveToNext());
        }
        return listOfTasks;
    }

    /*
     * Updating a task from a Task object
     */
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task.getTitle());
        values.put(KEY_T_DESC, task.getDescription());
        values.put(KEY_T_DEADLINE, task.getDeadline());
        values.put(KEY_T_PROJECT, task.getDeadline());

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    /*
     * Deleting a task from an id
     */
    public void deleteTask(long task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(task_id) });
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, null, null);
    }

    public void deleteAllTasksUnderProject(long projectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Task> allProjectTasks = getAllTasksUnderProject(projectId);
        // delete all tasks in the list
        for (Task task : allProjectTasks) {
            deleteTask(task.getId());
        }
    }




    //
    // PROJECTS management functions
    //

    /*
     * Creating a project in db from a Project object
     */
    public long createProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT, project.getTitle());
        values.put(KEY_P_DESC, project.getDescription());
        values.put(KEY_P_DEADLINE, project.getDeadline());

        // insert row
        long project_id = db.insert(TABLE_PROJECTS, null, values);

        return project_id;
    }

    /*
     * get single project from an id
     */
    public Project getProject(long project_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTS + " WHERE " + KEY_ID + " = " + project_id;

        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String title = c.getString(c.getColumnIndex(KEY_PROJECT));
        String desc = c.getString(c.getColumnIndex(KEY_P_DESC));
        String deadline = c.getString(c.getColumnIndex(KEY_P_DEADLINE));
        Project project = new Project(title, desc, deadline);

        project.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        return project;
    }

    /*
     * getting all projects in db
     * */
    public List<Project> getAllProjects() {
        List<Project> listOfProjects = new ArrayList<Project>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(KEY_PROJECT));
                String desc = c.getString(c.getColumnIndex(KEY_P_DESC));
                String deadline = c.getString(c.getColumnIndex(KEY_P_DEADLINE));
                Project project = new Project(title, desc, deadline);
                project.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to projects list
                listOfProjects.add(project);
            } while (c.moveToNext());
        }
        return listOfProjects;
    }

    /*
     * Updating a project from a Project object
     */
    public int updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT, project.getTitle());
        values.put(KEY_P_DESC, project.getDescription());
        values.put(KEY_P_DEADLINE, project.getDeadline());

        // updating row
        return db.update(TABLE_PROJECTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(project.getId()) });
    }

    /*
     * Deleting a project from an id
     */
    public void deleteProject(long project_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECTS, KEY_ID + " = ?",
                new String[] { String.valueOf(project_id) });
    }

    public void deleteAllProjects() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECTS, null, null);
    }

    
    //
    // closing database
    //
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
