package com.example.student_to_do_list;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class NewProjectActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";   //or "com.example.StudentToDoList.MESSAGE"
    public static final String EXTRA_DESC = "Dc ";
    public static final String EXTRA_DEADLINE = "Dd ";
    public static final String EXTRA_TYPE = "Type ";

    EditText email;
    EditText pj_name;
    EditText pj_description;
    EditText deadline;
    Button buttonSend;
    String mail_content;
    String mail_subject;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        // Define ActionBar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(getResources().getColor(R.color.theme_gold));
        actionBar.setBackgroundDrawable(colorDrawable);

        email = findViewById(R.id.editCollaboratorMail);
        pj_name = findViewById(R.id.editProject_name);
        pj_description = findViewById(R.id.editProject_description);
        deadline = findViewById(R.id.editDeadline);
        buttonSend = findViewById(R.id.button_addcollab);

        deadline = findViewById(R.id.editDeadline);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewProjectActivity.this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        deadline.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() { //A l'appui du bouton send, on concatene le mail à envoyer avec les donnée qui ont été entrée par l'utilisateur (dans les edittext) puis on redirige ces données vers la boite mail
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !pj_name.getText().toString().isEmpty() && !pj_description.getText().toString().isEmpty() && !deadline.getText().toString().isEmpty())
                {
                    Intent intentmail = new Intent(Intent.ACTION_SEND);

                    mail_content = "Hello,\n\n" +  "Tu a été invité à participer au projet : " + pj_name.getText().toString() + "\n\n"
                            + "Description du projet : \n" +  pj_description.getText().toString() + "\n\n" + "La date limite pour ce projet est " + deadline.getText().toString()
                            + "\n\n" + "Nous t'invitons vivement à te retrousser les manches. \n" + "Bon courage soldat.";
                    mail_subject = "Invitation à collaborer pour le projet : " + pj_name.getText().toString();
                    
                    intentmail.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                    intentmail.putExtra(Intent.EXTRA_SUBJECT, mail_subject);
                    intentmail.putExtra(Intent.EXTRA_TEXT, mail_content);
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

    public void returnFromTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        finish();

        startActivity(intent);
    }

    public void validateNewProject(View view) {

        if (pj_name.getText().toString().isEmpty() || deadline.getText().toString().isEmpty() || pj_description.getText().toString().isEmpty()) {
            Toast.makeText(NewProjectActivity.this, "Please fill NAME, PROJECT and DEADLINE boxes", Toast.LENGTH_SHORT).show();
            return;
        }

        //En appuyant sur le bouton valider, nous enregistrons les différents variables déclaré par l'user puis allons vers la MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        //On va chercher les valeurs présent dans les cases EditText du layout "activity_new_project" et les attribuons à des variables.
        EditText editText_projectName = (EditText) findViewById(R.id.editProject_name);
        String projectName = editText_projectName.getText().toString();
        intent.putExtra(EXTRA_NAME, projectName);

        EditText editText_projectDescription = (EditText) findViewById(R.id.editProject_description);
        String Project_description = editText_projectDescription.getText().toString();
        intent.putExtra(EXTRA_DESC, Project_description);

        EditText editText_Deadline = (EditText) findViewById(R.id.editDeadline);
        String Deadline = editText_Deadline.getText().toString();
        intent.putExtra(EXTRA_DEADLINE, Deadline);

        /*EditText editText_collaborator = (EditText) findViewById(R.id.editCollaboratorMail);
        String CollaboratorMail = editText_collaborator.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, CollaboratorMail);*/

        intent.putExtra(EXTRA_TYPE, "PROJECT");

        finish();

        startActivity(intent);
    }
}