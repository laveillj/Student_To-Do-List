package com.example.student_to_do_list.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.student_to_do_list.DatabaseHelper;
import com.example.student_to_do_list.MainActivity;
import com.example.student_to_do_list.NewTaskActivity;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;
import com.example.student_to_do_list.TasksContract;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private SQLiteDatabase tasksDb;
    //public ArrayList<String> tasksList = new ArrayList<>();
    public List<Task> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        this.updateTasksFromDb();

    //### RECYCLER VIEW ###
        //create RV adapter from data (fruits strings)
        rvAdapter = new TasksRVAdapter(tasksList);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tasks);

        //Enable animation on task expansion
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

        // set RV layout: vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //, LinearLayoutManager.VERTICAL, false
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        // Set the RV item divider decoration.
        // OptionalRVDividerItemDecoration works exactly the same as std DividerItemDecoration
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL)
        );

        Button clearTasks = (Button) view.findViewById(R.id.clearTasksButton);
        clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All tasks are cleared", Toast.LENGTH_SHORT).show();
                //int deletedRows = tasksDb.delete(TasksContract.TasksEntry.TABLE_NAME, null, null);
                db.deleteAllTasks();
                tasksList = new ArrayList<>();
                rvAdapter = new TasksRVAdapter(tasksList);
                recyclerView.setAdapter(rvAdapter);
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
        this.updateTasksFromDb();
        rvAdapter = new TasksRVAdapter(tasksList);
        recyclerView.setAdapter(rvAdapter);
    }

    public void updateTasksFromDb() {
        //### Tasks Database init ###
        db = new DatabaseHelper(getContext());
        tasksList = db.getAllTasks();
    }

    private void deleteItem(View rowView, final long position) {

        Animation anim = AnimationUtils.loadAnimation(requireContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300);
        rowView.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getContext());
                db.deleteTask(position);  //Remove the current content from the array
                rvAdapter = new TasksRVAdapter(tasksList);
                recyclerView.setAdapter(rvAdapter);
            }

        }, anim.getDuration());
    }

}