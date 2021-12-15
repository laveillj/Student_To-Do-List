package com.example.student_to_do_list.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.student_to_do_list.R;
import com.example.student_to_do_list.RVItemTouchListener;
import com.example.student_to_do_list.Task;

import java.util.Collections;
import java.util.List;

public class TasksRVAdapter extends RecyclerView.Adapter<TasksRVAdapter.ItemViewHolder> {

    private List<Task> tasksList;
    //private AdapterView.OnItemClickListener mListener;
    //private RVItemTouchListener.ItemTouchListener mListener;
    private OnItemClickListener mListener;
    public Context mContext;

    public interface OnItemClickListener {
        public void onItemClick(int position);
        public void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener pListener) {
        this.mListener = pListener;
    }

    public TasksRVAdapter(List<Task> tasksList, Context pContext) {
        this.tasksList = tasksList;
        this.mContext = pContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks_item, parent,
                false);
        return new ItemViewHolder(view, mListener, mContext);
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder { //to become static??

        private TextView title;
        private TextView description;
        private TextView deadline;
        private View subItem;
        private Button task_status_button;

        public ItemViewHolder(View itemView, final OnItemClickListener pListener, Context pContext) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tasks_item_title);
            description = (TextView) itemView.findViewById(R.id.task_sub_item_desc);
            deadline = (TextView) itemView.findViewById(R.id.task_sub_item_deadline);
            subItem = itemView.findViewById(R.id.task_sub_item);
            task_status_button = itemView.findViewById(R.id.task_item_status_button);

            task_status_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            pListener.onDeleteClick(position);
                        }
                    }
                }
            });
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