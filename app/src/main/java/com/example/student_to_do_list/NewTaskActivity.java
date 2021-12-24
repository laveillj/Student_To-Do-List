// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student_to_do_list.ui.projects.ProjectsFragment;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";
    public static final String EXTRA_DESC = "Dc ";
    public static final String EXTRA_DEADLINE = "Dd ";
    public static final String EXTRA_TYPE = "Type ";
    private String strID;
    private boolean underProject;

    EditText etDate;
    EditText tsk_name;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // Define ActionBar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(getResources().getColor(R.color.theme_gold));
        actionBar.setBackgroundDrawable(colorDrawable);

        etDate = findViewById(R.id.editDate_new_task);
        tsk_name = findViewById(R.id.task_name);

        //On fait appel à un widget pour avoir un calendrier afin de sélectionner la deadline qui sera, au final, affiché sous forme de string dans un EditText
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Onclick listener sur l'edittext deadline du xml activity_new_task
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewTaskActivity.this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
                //définie la couleur en bleu des boutons "Annuler" et "OK" du calendrier
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
            }
        });

        //On récupère aussi le contenu d'un intent pour savoir si la tache doit se trouver sous un projet
        Intent in = getIntent();
        strID = in.getStringExtra("PROJECT_ID");
        Log.d("###", "PROJECT_ID from intent: "+ strID+"0");
        if(strID.equals("0")) //La nouvelle tache n'est pas associée à un projet
            this.underProject = false;
        else //La tache doit etre associée à un projet
            this.underProject = true;

        Log.d("###", "underProject boolean: "+ underProject);
    }

    //Fonction pour le bouton retour
    public void returnFromTask(View view) {
        Intent intent;
        if(underProject) {
            //Pour une tache qui devait se trouver sous un projet on relance l'activity ProjectViewContentActivity
            //et cela en précisant l'id du projet correspondant. L'affichage sera alors configuré en focntion dans la classe
            intent = new Intent(this, ProjectViewContentActivity.class).putExtra(ProjectsFragment.EXTRA_PROJECT_ID, strID);
        }
        else {
            //Dans le cas contraire on retourne simplement à l'activity principale
            intent = new Intent(this, MainActivity.class);
        }
        finish();
        startActivity(intent);
    }

    public void validateNewTask(View view) {
        if (tsk_name.getText().toString().isEmpty()) {
            Toast.makeText(NewTaskActivity.this, "Please fill NAME", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        //En fonction de si la tache est sous un projet ou non nous déclarons l'intent correspondant
        if(underProject)
            intent = new Intent(this, ProjectViewContentActivity.class).putExtra(ProjectsFragment.EXTRA_PROJECT_ID, strID);
        else
            intent = new Intent(this, MainActivity.class);

        //On va chercher les valeurs présent dans les cases EditText du layout "activity_new_project" et les attribuons à des variables.
        EditText taskName = (EditText) findViewById(R.id.task_name);
        EditText taskDesc = (EditText) findViewById(R.id.task_description);
        EditText taskDeadline = (EditText) findViewById(R.id.editDate_new_task);

        String strTaskName = taskName.getText().toString();
        String strTaskDesc = taskDesc.getText().toString();
        String strTaskDeadline = taskDeadline.getText().toString();

        intent.putExtra(EXTRA_NAME, strTaskName);
        intent.putExtra(EXTRA_DESC, strTaskDesc);
        intent.putExtra(EXTRA_DEADLINE, strTaskDeadline);
        intent.putExtra(EXTRA_TYPE, "TASK");

        finish();

        startActivity(intent);
    }
}