package com.example.student_to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "N ";   //or "com.example.StudentToDoList.MESSAGE"
    public static final String EXTRA_DESC = "D ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void validateNewTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText taskName = (EditText) findViewById(R.id.task_name);
        EditText taskDesc = (EditText) findViewById(R.id.task_description);

        String strTaskName = taskName.getText().toString();
        String strTaskDesc = taskDesc.getText().toString();

        intent.putExtra(EXTRA_NAME, strTaskName);
        intent.putExtra(EXTRA_DESC, strTaskDesc);
        startActivity(intent);
    }
}