package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";   //or "com.example.StudentToDoList.MESSAGE"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void validateNewTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText taskName = (EditText) findViewById(R.id.task_name);
        String strTaskName = taskName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, strTaskName);
        startActivity(intent);
    }
}