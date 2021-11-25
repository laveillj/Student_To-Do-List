package com.example.student_to_do_list.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_to_do_list.R;
import com.example.student_to_do_list.databinding.FragmentProjectsBinding;
import com.example.student_to_do_list.ui.home.TasksRVAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    static ArrayList<String> projectList = new ArrayList<>();
    RecyclerView recyclerView;
    ProjectRVAdapter rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        //create RV adapter from data (fruits strings)
        rvAdapter = new ProjectRVAdapter(projectList);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_projects);

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
        ImageButton addProjectBT = (ImageButton) view.findViewById(R.id.addProjectButton);
        addProjectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You click on ADD NEW Project", Toast.LENGTH_SHORT).show();
                projectList.add("New Project");
                rvAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}