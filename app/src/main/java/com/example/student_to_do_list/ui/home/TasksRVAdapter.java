package com.example.student_to_do_list.ui.home;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.student_to_do_list.R;

import java.util.Collections;
import java.util.List;

public class TasksRVAdapter extends RecyclerView.Adapter<TasksRVAdapter.ItemViewHolder> {

    List<String> dataList;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.fragment_tasks_item_title);
        }
    }

    public TasksRVAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks_item, parent,
                false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txt.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}