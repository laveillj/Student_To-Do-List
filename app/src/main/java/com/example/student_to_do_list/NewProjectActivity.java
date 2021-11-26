package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewProjectActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
    }

    public void validateNewProject(View view) {
        //En appuyant sur le bouton valider, nous enregistrons les différents variables déclaré par l'user puis allons vers la MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        //On va chercher les valeurs présent dans les cases EditText du layout "activity_new_project" et les attribuons à des variables.
        EditText editText_projectName = (EditText) findViewById(R.id.editProject_name);
        String projectName = editText_projectName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, projectName);

        EditText editText_Deadline = (EditText) findViewById(R.id.editDeadline);
        String Deadline = editText_Deadline.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, Deadline);

        EditText editText_projectDescription = (EditText) findViewById(R.id.editProject_description);
        String Project_description = editText_projectDescription.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, Project_description);

        EditText editText_collaborator = (EditText) findViewById(R.id.editCollaboratorMail);
        String CollaboratorMail = editText_collaborator.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, CollaboratorMail);

        startActivity(intent);
    }
}