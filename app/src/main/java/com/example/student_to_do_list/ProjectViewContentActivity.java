package com.example.student_to_do_list;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.student_to_do_list.ui.dashboard.ProjectRVAdapter;
import com.example.student_to_do_list.ui.dashboard.ProjectsFragment;
import com.example.student_to_do_list.ui.home.TasksRVAdapter;

import java.util.ArrayList;
import java.util.List;

//Activité permettant d'afficher les détails complètes d'un projet spécifique
public class ProjectViewContentActivity extends AppCompatActivity {

    private static final String TAG = "### ProjectViewContentActivity ###";
    public List<Task> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;
    DatabaseHelper db;
    private String strID;
    private Project project;
    private long projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectviewcontent);

        String project_id_string = "not set";
        Log.d(TAG, "project_content_debug : creating activity for project view content");

        // Define ActionBar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(getResources().getColor(R.color.theme_gold));
        actionBar.setBackgroundDrawable(colorDrawable);

        // Get intent content
        Intent intent = getIntent();


        //L'accès à l'activité ProjectViewContentActivity recommende un ID afin de pouvoir déterminé sur quel projet de la database nous nous basons afin d'afficher les éléments.
        //Cette ID peut être renvoyé depuis 3 "trigger", depuis la RV du fragment projet sur le click d'un item, depuis l'edition d'un projet à l'appui sur le bouton retour et depuis l'édition d'un projet à l'appui sur le bouton validé
        //Il semble plus sécurisé de déterminer depuis où l'intent est généré afin de trier en conséquence.
        if (intent.hasExtra(ProjectsFragment.EXTRA_PROJECT_ID) ){
            strID = intent.getStringExtra(ProjectsFragment.EXTRA_PROJECT_ID);
            projectID = Long.parseLong(strID);
            Log.d(TAG, "long projectID: " + strID);
        }

        else if (intent.hasExtra("RETURN_PROJECT_ID")){
            strID = intent.getStringExtra("RETURN_PROJECT_ID");
            projectID = Long.parseLong(strID);
            Log.d(TAG, "long projectID: " + strID);
        }

        else if (intent.hasExtra("VALI_PROJECT_ID")){
            strID = intent.getStringExtra("VALI_PROJECT_ID");
            projectID = Long.parseLong(strID);
            Log.d(TAG, "long projectID: " + strID);
        }

        // Project for Database
        db = new DatabaseHelper(this); //On associe la variable db le contexte de son application présent dans sa classe DatabaseHelper
        project = db.getProject(projectID); //On utilise ensuite une méthode de la classe DatabaseHelper afin de récupérer le projet associé à l'ID correspond à l'item cliqué dans la recyclerview puis on l'associe à une variable project dont la classe est Project.java

        Log.d(TAG, "project name: " + project.getTitle());

        TextView VC_project_name = findViewById(R.id.Name_project_VC_value); //On récupère le textview pour le nom du projet dans l'activity_projectviewcontent.xml
        TextView VC_project_description = findViewById(R.id.Description_title_VC_value);
        TextView VC_project_deadline = findViewById(R.id.Deadline_VC_Value);

        VC_project_name.setText(project.getTitle());
        VC_project_description.setText(project.getDescription());
        VC_project_deadline.setText(project.getDeadline());


        String strName = intent.getStringExtra(NewTaskActivity.EXTRA_NAME);
        String strDesc = intent.getStringExtra(NewTaskActivity.EXTRA_DESC);
        String strDeadline = intent.getStringExtra(NewTaskActivity.EXTRA_DEADLINE);
        if(strName != null) {
            Log.d(" ### ### ", "Name for new TASK: " + strName);
            Task task = new Task(strName, strDesc, strDeadline, projectID);
            long task_id = db.createTask(task);
            getIntent().removeExtra(NewTaskActivity.EXTRA_NAME);
        }

    //### RECYCLER VIEW POUR LES TACHES AJOUTABLES POUR UN PROJET ###
        //Les fonctions ci-après sont similaires à celle dans le TaskFragment (fragment pour les tâches)

        //create RV adapter from data (fruits strings)
        rvAdapter = new TasksRVAdapter(tasksList, this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_tasks_under_VC);

        //Enable animation on task expansion
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

        // set RV layout: vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //, LinearLayoutManager.VERTICAL, false
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        // Set the RV item divider decoration.
        // OptionalRVDividerItemDecoration works exactly the same as std DividerItemDecoration
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
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

        this.updateTasksFromDb(db);

    }

    public void returnFromTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void editProject(View view) {
        Intent intent = new Intent(this, ModifyProjectActivity.class);
        intent.putExtra("MPROJECT_ID", this.strID);
        finish();
        startActivity(intent);
    }

    public void addNewTaskToProject(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class).putExtra("PROJECT_ID", strID);
        finish();
        startActivity(intent);
    }

    public void updateTasksFromDb(DatabaseHelper pDB) {
        //Get tasks from database
        List<Task> newList = pDB.getAllTasksUnderProject(this.projectID);
        if(newList.size() != tasksList.size()) {
            tasksList.clear();
            tasksList.addAll(newList);
            rvAdapter.notifyDataSetChanged();  //Notify adapter
        }
    }

    private void deleteItem(View rowView, final long position) {
        Button task_status_button = rowView.findViewById(R.id.task_item_status_button);
        task_status_button.setForeground(getBaseContext().getResources().getDrawable(R.drawable.task_status_1));
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300);
        rowView.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getBaseContext());
                db.deleteTask(position);  //Remove the current content from the array
                updateTasksFromDb(db);
            }

        }, anim.getDuration());
    }

}
