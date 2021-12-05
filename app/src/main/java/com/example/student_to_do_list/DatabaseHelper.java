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
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tasksAndProjectsDb";

    // Table Names
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_PROJECTS = "projects";

    // Common column names
    private static final String KEY_ID = "id";
    //private static final String KEY_CREATED_AT = "created_at";

    // TASKS Table - column names
    private static final String KEY_TASK = "task";
    private static final String KEY_T_DESC = "description";
    private static final String KEY_T_DEADLINE = "deadline";

    // PROJECTS Table - column names
    private static final String KEY_PROJECT = "project";
    private static final String KEY_P_DESC = "description";
    private static final String KEY_P_DEADLINE = "deadline";

    // Table Create Statements
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE "
            + TABLE_TASKS + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT,"
            + KEY_T_DESC + " TEXT," + KEY_T_DEADLINE + " TEXT)";

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
     * Creating a task
     */
    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task.getTitle());
        values.put(KEY_T_DESC, task.getDescription());
        values.put(KEY_T_DEADLINE, task.getDeadline());

        // insert row
        long task_id = db.insert(TABLE_TASKS, null, values);

        return task_id;
    }

    /*
     * get single task
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
        Task task = new Task(title, desc, deadline);

        task.setId(c.getInt(c.getColumnIndex(KEY_ID)));

        return task;
    }

    /*
     * getting all tasks
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
                Task task = new Task(title, desc, deadline);
                task.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                // adding to tasks list
                listOfTasks.add(task);
            } while (c.moveToNext());
        }
        return listOfTasks;
    }

    /*
     * Updating a task
     */
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task.getTitle());
        values.put(KEY_T_DESC, task.getDescription());
        values.put(KEY_T_DEADLINE, task.getDeadline());

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    /*
     * Deleting tasks
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


    //
    // PROJECTS management functions
    //


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
