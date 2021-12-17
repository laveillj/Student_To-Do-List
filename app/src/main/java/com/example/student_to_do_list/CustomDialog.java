package com.example.student_to_do_list;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student_to_do_list.ui.home.TasksFragment;

public class CustomDialog extends Dialog {

    private Button buttonCancel;
    public Context mContext;
    private TasksFragment mFragment;
    private Task mTask;
    DatabaseHelper db;

    public CustomDialog(Context pContext, TasksFragment pFragment, Task pTask) {
        super(pContext);
        this.mContext = pContext;
        this.mFragment = pFragment;
        this.mTask = pTask;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cancel);

        db = new DatabaseHelper(getContext());

        this.buttonCancel  = (Button) findViewById(R.id.button_cancel);

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "Cancel" button.
    private void buttonCancelClick()  {
        db.createTask(mTask);
        this.mFragment.updateTasksFromDb(db);
        this.dismiss();
    }
}