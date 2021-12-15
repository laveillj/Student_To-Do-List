package com.example.student_to_do_list.ui.home;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;

import java.util.Collections;
import java.util.List;

public class TasksRVAdapter extends RecyclerView.Adapter<TasksRVAdapter.ItemViewHolder> {

    //List<String> dataList;
    private List<Task> tasksList;

    public TasksRVAdapter(List<Task> tasksList) {
        /*try {
            int listSize = tasksList.size();
            for(int i=0 ; i < listSize ; i++)
                this.dataList.add( tasksList.get(i).getTitle() );
            this.tasksList = tasksList;
        }catch (NullPointerException e) { this.tasksList = tasksList; }*/

        this.tasksList = tasksList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks_item, parent,
                false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Task task = tasksList.get(position);

        holder.bind(task);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = task.isExpanded();
            task.setExpanded(!expanded);
            notifyItemChanged(position);
        });
        //holder.title.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return tasksList == null ? 0 : tasksList.size();
        //return dataList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView deadline;
        private View subItem;
        private Button tasks_status_button;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tasks_item_title);
            description = (TextView) itemView.findViewById(R.id.task_sub_item_desc);
            deadline = (TextView) itemView.findViewById(R.id.task_sub_item_deadline);
            subItem = itemView.findViewById(R.id.task_sub_item);
            tasks_status_button = itemView.findViewById(R.id.task_item_status_button);


        }

        private void bind(Task task) {
            boolean expanded = task.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(task.getTitle());
            description.setText(task.getDescription());
            deadline.setText("\nDeadline: " + task.getDeadline());
        }
    }

}