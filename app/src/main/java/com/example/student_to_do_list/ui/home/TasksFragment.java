package com.example.student_to_do_list.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_to_do_list.MainActivity;
import com.example.student_to_do_list.NewTaskActivity;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.TasksContract;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private SQLiteDatabase tasksDb;
    public ArrayList<String> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

    //### RECYCLER VIEW ###
        //create RV adapter from data (fruits strings)
        rvAdapter = new TasksRVAdapter(tasksList);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tasks);

        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);

        // set RV layout: vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();


        // Set the RV item divider decoration.
        // OptionalRVDividerItemDecoration works exactly the same as std DividerItemDecoration
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL)
        );

        //Add new task button listener
        /*ImageButton addTaskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You click on ADD NEW TASK", Toast.LENGTH_SHORT).show();
                tasksList.add("New Task");
                rvAdapter.notifyDataSetChanged();
            }
        });*/

        Button clearTasks = (Button) view.findViewById(R.id.clearButton);
        clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All tasks are cleared", Toast.LENGTH_SHORT).show();
                int deletedRows = tasksDb.delete(TasksContract.TasksEntry.TABLE_NAME, null, null);
                tasksList = new ArrayList<>();
                rvAdapter.notifyDataSetChanged();
                tasksDb.close();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        //### Tasks Database init ###
        TasksContract.TasksDbHelper tasksDbHelper = new TasksContract.TasksDbHelper(getContext());
        tasksDb = tasksDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                TasksContract.TasksEntry.COLUMN_NAME_TITLE,
                TasksContract.TasksEntry.COLUMN_NAME_DESCRIPTION,
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = TasksContract.TasksEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                TasksContract.TasksEntry.COLUMN_NAME_TITLE;

        Cursor cursor = tasksDb.query(
                TasksContract.TasksEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //Add all tasks in database to tasksList Array
        while(cursor.moveToNext()) {
            String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow(TasksContract.TasksEntry.COLUMN_NAME_TITLE));
            tasksList.add(taskTitle);
        }
        rvAdapter.notifyDataSetChanged();
        cursor.close();
    }

}