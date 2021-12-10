package com.example.student_to_do_list.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.student_to_do_list.DatabaseHelper;
import com.example.student_to_do_list.Project;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;
import com.example.student_to_do_list.databinding.FragmentProjectsBinding;
import com.example.student_to_do_list.ui.home.TasksRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment {

    public List<Project> projectsList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProjectRVAdapter rvAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        this.updateProjectsFromDb();

        //create RV adapter from data (fruits strings)
        rvAdapter = new ProjectRVAdapter(projectsList);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_projects);

        // set RV layout: vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));     //, LinearLayoutManager.VERTICAL, false));
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        Button clearTasks = (Button) view.findViewById(R.id.clearProjectsButton);
        clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All projects are cleared", Toast.LENGTH_SHORT).show();
                //int deletedRows = tasksDb.delete(TasksContract.TasksEntry.TABLE_NAME, null, null);
                db.deleteAllProjects();
                projectsList = new ArrayList<>();
                rvAdapter = new ProjectRVAdapter(projectsList);
                recyclerView.setAdapter(rvAdapter);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() { super.onDestroy(); }

    @Override
    public void onResume() {
        super.onResume();
        this.updateProjectsFromDb();
        rvAdapter = new ProjectRVAdapter(projectsList);
        recyclerView.setAdapter(rvAdapter);
    }

    public void updateProjectsFromDb() {
        //### Tasks Database init ###
        db = new DatabaseHelper(getContext());
        projectsList = db.getAllProjects();
    }
}