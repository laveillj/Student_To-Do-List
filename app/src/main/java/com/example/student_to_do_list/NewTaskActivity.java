package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";   //or "com.example.StudentToDoList.MESSAGE"
    public static final String EXTRA_DESC = "Dc ";
    public static final String EXTRA_DEADLINE = "Dd ";
    public static final String EXTRA_TYPE = "Type ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
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