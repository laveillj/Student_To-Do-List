package com.example.student_to_do_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProjectViewContentActivity extends AppCompatActivity {

    private static final String TAG = "ProjectViewContentActivity";
    DatabaseHelper db;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectviewcontent);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("PROJECT_ID")){
            String S_project_id = getIntent().getStringExtra("PROJECT_ID");
            setContent(S_project_id);
        }
    }

    public void returnFromTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setContent (String S_PROJECT_ID) {
        long l_project_id = Long.parseLong(S_PROJECT_ID,10);
        db = new DatabaseHelper(getApplicationContext()); //On associe la variable db le contexte de son application présent dans sa classe DatabaseHelper
        project = db.getProject(l_project_id); //On utilise ensuite une méthode de la classe DatabaseHelper afin de récupérer le projet associé à l'ID correspond à l'item cliqué dans la recyclerview puis on l'associe à une variable project dont la classe est Project.java
        TextView VC_project_name = findViewById(R.id.Name_project_VC_value); //On récupère le textview pour le nom du projet dans l'activity_projectviewcontent.xml
        TextView VC_project_description = findViewById(R.id.Description_title_VC_value);
        TextView VC_project_deadline = findViewById(R.id.Deadline_VC_Value);

        VC_project_name.setText(project.getTitle());
        VC_project_description.setText(project.getDescription());
        VC_project_deadline.setText(project.getDeadline());

    }



}
