package com.example.student_to_do_list;

import android.content.Intent;
import android.os.Bundle;

import com.example.student_to_do_list.ui.home.TasksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.view.View;

import com.example.student_to_do_list.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_projects, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*
        TasksFragment tasksFragment = new TasksFragment();
        tasksFragment.setDatalist(DataListUtils.getDatalist());
        getSupportFragmentManager().beginTransaction()
            .add(R.id.firstpane_placeholder, tasksFragment)
            .commit();
         */


        //################# Add tasks #########################
        // IL FAUT MAINTENANT : pouvoir envoyer le message du intent (ou le intent direct) vers le fragment pour modifier la recycler view

        //Fragment tasksFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        //TasksFragment tasksFragment = (TasksFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String strTaskName = intent.getStringExtra(NewTaskActivity.EXTRA_MESSAGE);

        /*Bundle bundleToTasksFragment = new Bundle();
        bundleToTasksFragment.putString("newTaskName", strTaskName);
        tasksFragment.setArguments(bundleToTasksFragment);*/

        /*if(tasksFragment != null && tasksFragment.isAdded()) {
            tasksFragment.tasksList.add(strTaskName);
            tasksFragment.rvAdapter.notifyDataSetChanged();
        }*/


    }

    public void addNewTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void addNewProject(View view) {
        Intent intent = new Intent(this, NewProjectActivity.class); //On déclare l'intention d'aller vers cette activity NewProjectActivity
        startActivity(intent);
    }

}