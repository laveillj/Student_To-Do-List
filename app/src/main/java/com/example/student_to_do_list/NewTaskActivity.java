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

import com.example.student_to_do_list.ui.dashboard.ProjectsFragment;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";   //or "com.example.StudentToDoList.MESSAGE"
    public static final String EXTRA_DESC = "Dc ";
    public static final String EXTRA_DEADLINE = "Dd ";
    public static final String EXTRA_TYPE = "Type ";
    private String strID;
    private boolean underProject;

    EditText etDate;
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
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
            }
        });

        Intent in = getIntent();
        strID = in.getStringExtra("PROJECT_ID");
        Log.d("###", "PROJECT_ID from intent: "+ strID+"0");
        if(strID.equals("0"))
            this.underProject = false;
        else
            this.underProject = true;

        Log.d("###", "underProject boolean: "+ underProject);
    }

    public void returnFromTask(View view) {
        Intent intent;
        if(underProject)
            intent = new Intent(this, ProjectViewContentActivity.class).putExtra(ProjectsFragment.EXTRA_PROJECT_ID, strID);
        else
            intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void validateNewTask(View view) {
        Intent intent;
        if(underProject)
            intent = new Intent(this, ProjectViewContentActivity.class).putExtra(ProjectsFragment.EXTRA_PROJECT_ID, strID);
        else
            intent = new Intent(this, MainActivity.class);
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
        startActivity(intent);
    }
}