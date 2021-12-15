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

    public List<Task> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

    //### RECYCLER VIEW ###
        //create RV adapter from data (fruits strings)
        rvAdapter = new TasksRVAdapter(tasksList, getContext());

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

        rvAdapter.setOnItemClickListener(new TasksRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test","Clicked item at position number " + tasksList.get(position) + " in RecyclerView");
            }

            @Override
            public void onDeleteClick(int position) {
                Log.d("test","Deleted item at position number " + tasksList.get(position) + " in RecyclerView");
                View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                deleteItem(v, tasksList.get(position).getId());
            }
        });

        Button clearTasks = (Button) view.findViewById(R.id.clearTasksButton);
        clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All tasks are cleared", Toast.LENGTH_SHORT).show();
                db.deleteAllTasks();
                updateTasksFromDb(db);
                //rvAdapter = new TasksRVAdapter(tasksList);
                //recyclerView.setAdapter(rvAdapter);
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
        db = new DatabaseHelper(getContext());
        this.updateTasksFromDb(db);
    }

    public void updateTasksFromDb(DatabaseHelper pDB) {
        //Get tasks from database
        List<Task> newList = pDB.getAllTasks();

        if(newList.size() != tasksList.size()) {
            tasksList.clear();
            tasksList.addAll(newList);
            rvAdapter.notifyDataSetChanged();  //Notify adapter
        }
    }

    private void deleteItemSimple(final long position) {
        //db = new DatabaseHelper(getContext());
        db = new DatabaseHelper(getContext());
        db.deleteTask(position);  //Remove the current content from the array
        this.updateTasksFromDb(db);
    }

    private void deleteItem(View rowView, final long position) {
        Button task_status_button = rowView.findViewById(R.id.task_item_status_button);
        task_status_button.setForeground(getContext().getResources().getDrawable(R.drawable.task_status_1));
        Animation anim = AnimationUtils.loadAnimation(requireContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300);
        rowView.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getContext());
                db.deleteTask(position);  //Remove the current content from the array
                updateTasksFromDb(db);
            }

        }, anim.getDuration());
    }

}