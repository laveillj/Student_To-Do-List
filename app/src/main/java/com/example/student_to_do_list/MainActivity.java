// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.View;

import com.example.student_to_do_list.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //db correspondra à la base de donnée créée à partir de la classe DatabaseHelper
    DatabaseHelper db;

    // Fonction onCreate() qui s'exécute lorsque la main activity est lancée
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation de l'Action Bar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // On défini la couleur à partir des ressources contenues dans le fichier color.xml
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.theme_gold));
        actionBar.setBackgroundDrawable(colorDrawable);

        // Mise en place du menu de navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // La barre de navigation est configurée avec les trois onglets
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tasks, R.id.navigation_projects, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    // onResume() s'exécute à chaque retour sur la main activity
    @Override
    public void onResume() {
        super.onResume();

        // On récupère le Intent qui a lancé l'activity et les données qu'il contient
        Intent intent = getIntent();
        // Ici on récupère les données d'une tache ou d'un projet, on a donc les String suivantes
        String strName = intent.getStringExtra(NewTaskActivity.EXTRA_NAME);
        String strDesc = intent.getStringExtra(NewTaskActivity.EXTRA_DESC);
        String strDeadline = intent.getStringExtra(NewTaskActivity.EXTRA_DEADLINE);
        String strType = intent.getStringExtra(NewTaskActivity.EXTRA_TYPE);


        // ### DATA BASE ###
        db = new DatabaseHelper(getApplicationContext());   //Gestion de la db via la classe DatabesHelper
        if(strName != null) {
            switch(strType){
                //Si on a reçu une tache on ajoute un objet Task dans la table correspondante de la db
                case "TASK":
                    Task task = new Task(strName, strDesc, strDeadline, 0);
                    long task_id = db.createTask(task);
                    break;
                //Si on a reçu un projet on ajoute un objet Project dans la table correspondante de la db
                case "PROJECT":
                    Project project = new Project(strName, strDesc, strDeadline);
                    long project_id = db.createProject(project);
                    break;
            }
            //Pour éviter de répéter l'action d'ajout d'un élément on retire enfin la String NAME du Intent
            getIntent().removeExtra(NewTaskActivity.EXTRA_NAME);
        }
    }

    // Lors de l'appui sur le bouton d'ajout de tache on lance la fonction suivante
    public void addNewTask(View view) {
        //On déclare l'intention d'aller vers cette activity NewTaskActivity
        Intent intent = new Intent(this, NewTaskActivity.class).putExtra("PROJECT_ID", "0");
        finish();
        startActivity(intent);
    }

    // Lors de l'appui sur le bouton d'ajout de projet on lance la fonction suivante
    public void addNewProject(View view) {
        //On déclare l'intention d'aller vers cette activity NewProjectActivity
        Intent intent = new Intent(this, NewProjectActivity.class);
        finish();
        startActivity(intent);
    }

}