package com.example.student_to_do_list.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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
import com.example.student_to_do_list.NewProjectActivity;
import com.example.student_to_do_list.Project;
import com.example.student_to_do_list.ProjectViewContentActivity;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.RVItemTouchListener;
import com.example.student_to_do_list.Task;
import com.example.student_to_do_list.databinding.FragmentProjectsBinding;
import com.example.student_to_do_list.ui.home.TasksRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment {

    public static final String EXTRA_PROJECT_ID = "PROJECT_ID";
    public List<Project> projectsList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProjectRVAdapter rvAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        //create RV adapter from data (fruits strings)
        rvAdapter = new ProjectRVAdapter(projectsList, getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_projects);

        // set RV layout: vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));     //, LinearLayoutManager.VERTICAL, false));
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        rvAdapter.setOnItemClickListener(new ProjectRVAdapter.OnProjectClickListener() {
            @Override
            public void onItemClick(int position) {
                Project project = projectsList.get(position);
                Log.d(" ### ", "Project got at position: " + project.getId());
                Intent intent = new Intent(getActivity(), ProjectViewContentActivity.class);
                String strProjectID = ""+project.getId();
                intent.putExtra(EXTRA_PROJECT_ID, strProjectID); //Nous récupérons l'ID de la database associé à l'item cliqué et nous ferons un post traitement dans le ProjectViewContentActivity pour afficher les informations nécessaires pour le projet en question
                Log.d(" ### ", "EXTRA putted in intent: " + intent.getStringExtra(EXTRA_PROJECT_ID));
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                Log.d("test","Deleted item at position number " + projectsList.get(position) + " in RecyclerView");
                View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                deleteItem(v, projectsList.get(position).getId());
            }

        });

        Button clearTasks = (Button) view.findViewById(R.id.clearProjectsButton);
        clearTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "All projects are cleared", Toast.LENGTH_SHORT).show();
                //int deletedRows = tasksDb.delete(TasksContract.TasksEntry.TABLE_NAME, null, null);
                db.deleteAllProjects();
                updateProjectsFromDb(db);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() { super.onDestroy(); }

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseHelper(getContext());
        this.updateProjectsFromDb(db);
    }

    public void updateProjectsFromDb() {
        //### Tasks Database init ###
        db = new DatabaseHelper(getContext());
        this.updateProjectsFromDb(db);
    }

    public void updateProjectsFromDb(DatabaseHelper pDB) {
        //Get projects from database
        List<Project> newList = pDB.getAllProjects();

        if(newList.size() != projectsList.size()) {
            projectsList.clear();
            projectsList.addAll(newList);
            rvAdapter.notifyDataSetChanged();  //Notify adapter
        }
    }

    private void deleteItem(View rowView, final long position) {
        //Button project_button = rowView.findViewById(R.id.project_popup);
        Animation anim = AnimationUtils.loadAnimation(requireContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300);
        rowView.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getContext());
                db.deleteProject(position);  //Remove the current content from the array
                db.deleteAllTasksUnderProject(position);
                updateProjectsFromDb(db);
            }
        }, anim.getDuration());
    }




}