package com.example.student_to_do_list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.student_to_do_list.ui.home.TasksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.student_to_do_list.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(getResources().getColor(R.color.theme_gold));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_projects, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override   //intent su le resume au lieu du creates
    public void onResume() {
        super.onResume();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String strName = intent.getStringExtra(NewTaskActivity.EXTRA_NAME);
        String strDesc = intent.getStringExtra(NewTaskActivity.EXTRA_DESC);
        String strDeadline = intent.getStringExtra(NewTaskActivity.EXTRA_DEADLINE);
        String strType = intent.getStringExtra(NewTaskActivity.EXTRA_TYPE);


        // ### DATA BASE ###
        db = new DatabaseHelper(getApplicationContext());
        if(strName != null) {
            switch(strType){
                case "TASK":
                    Task task = new Task(strName, strDesc, strDeadline, 0);
                    long task_id = db.createTask(task);
                    break;
                case "PROJECT":
                    Project project = new Project(strName, strDesc, strDeadline);
                    long project_id = db.createProject(project);
                    break;
            }
            getIntent().removeExtra(NewTaskActivity.EXTRA_NAME);
        }
    }

    public void addNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class).putExtra("PROJECT_ID", "0");
        finish();
        startActivity(intent);
    }

    public void addNewProject(View view) {
        Intent intent = new Intent(this, NewProjectActivity.class); //On déclare l'intention d'aller vers cette activity NewProjectActivity
        finish();
        startActivity(intent);
    }

}