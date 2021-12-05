package com.example.student_to_do_list.ui.dashboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.student_to_do_list.R;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;




public class ProjectRVAdapter extends RecyclerView.Adapter<ProjectRVAdapter.ItemViewHolder2> {

   List<String> dataList;

   public class ItemViewHolder2 extends RecyclerView.ViewHolder {
       TextView txt;
       //TextView deadline;

       public ItemViewHolder2(View itemView) {
           super(itemView);
           txt = (TextView) itemView.findViewById(R.id.name_project);
           //deadline = (TextView) itemView.findViewById(R.id.deadline_project);
       }
   }

   public ProjectRVAdapter(List<String> dataList) { this.dataList = dataList; }

   @Override
   public ItemViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks_item, parent, false);

       return new ItemViewHolder2(view);
   }

    @Override
    public void onBindViewHolder(ItemViewHolder2 holder, int position) {
        holder.txt.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}