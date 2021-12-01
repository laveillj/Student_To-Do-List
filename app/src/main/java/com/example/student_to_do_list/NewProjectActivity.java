package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewProjectActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "";

    EditText email;
    EditText pj_name;
    EditText pj_description;
    EditText deadline;
    Button buttonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        email = findViewById(R.id.editCollaboratorMail);
        pj_name = findViewById(R.id.editProject_name);
        pj_description = findViewById(R.id.editProject_description);
        deadline = findViewById(R.id.editDeadline);

        buttonSend = findViewById(R.id.button_addcollab);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !pj_name.getText().toString().isEmpty() && !pj_description.getText().toString().isEmpty())
                {
                    Intent intentmail = new Intent(Intent.ACTION_SEND);
                    intentmail.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                    intentmail.putExtra(Intent.EXTRA_SUBJECT, pj_name.getText().toString());
                    intentmail.putExtra(Intent.EXTRA_TEXT, pj_description.getText().toString());
                    intentmail.setType("message/rfc822");
                    if(intentmail.resolveActivity(getPackageManager()) != null) {
                        startActivity(intentmail);
                    }
                    else {
                        Toast.makeText(NewProjectActivity.this, "Il n'existe pas d'application boîte mail supportant cette action.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(NewProjectActivity.this, "Veuillez remplir les cases", Toast.LENGTH_SHORT).show();
                }
            }
        });
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