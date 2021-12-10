package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";   //or "com.example.StudentToDoList.MESSAGE"
    public static final String EXTRA_DESC = "Dc ";
    public static final String EXTRA_DEADLINE = "Dd ";
    public static final String EXTRA_TYPE = "Type ";

    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


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


    }



    public void returnFromTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void validateNewTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
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