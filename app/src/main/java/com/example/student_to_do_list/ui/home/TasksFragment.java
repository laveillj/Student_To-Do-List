package com.example.student_to_do_list.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_to_do_list.MainActivity;
import com.example.student_to_do_list.R;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    public static ArrayList<String> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            String newTaskName = bundle.getString("newTaskName");
        }
        tasksList.add("abc");*/
    }

}