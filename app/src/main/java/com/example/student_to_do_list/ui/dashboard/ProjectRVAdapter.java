package com.example.student_to_do_list.ui.dashboard;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.student_to_do_list.Project;
import com.example.student_to_do_list.ProjectViewContentActivity;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;




public class ProjectRVAdapter extends RecyclerView.Adapter<ProjectRVAdapter.ItemViewHolder2> {

    //List<String> dataList;
    private List<Project> projectsList;
    private Context mContext;

    public ProjectRVAdapter(List<Project> projectsList, Context context) {
        this.projectsList = projectsList;
        mContext = context;
    }

    @Override
    public ItemViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_card, parent, false);
       return new ItemViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder2 holder, int position) {
        Project project = projectsList.get(position); //Récupère la position lié à l'item présent dans la recyclerview
        holder.project_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lorsqu'on clique sur un project_card, on genère un nouvel intent vers projectviewcontentActivity
                Intent intent = new Intent(mContext, ProjectViewContentActivity.class);
                intent.putExtra("PROJECT_ID",project.getId()); //Nous récupérons l'ID de la database associé à l'item cliqué et nous ferons un post traitement dans le ProjectViewContentActivity pour afficher les informations nécessaires pour le projet en question
                mContext.startActivity(intent);
            }
        });

        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return projectsList == null ? 0 : projectsList.size();
        //return dataList.size();
    }

    public class ItemViewHolder2 extends RecyclerView.ViewHolder {

        private TextView title;
        //private TextView description;
        private TextView deadline;
        private View subItem;
        RelativeLayout project_layout;

        public ItemViewHolder2(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name_project);
            project_layout = itemView.findViewById(R.id.layout_project_card);
            //description = (TextView) itemView.findViewById(R.id.task_sub_item_desc);
            deadline = (TextView) itemView.findViewById(R.id.deadline_project_value);
        }
        private void bind(Project project) {

            title.setText(project.getTitle());
            //description.setText(project.getDescription());
            deadline.setText(project.getDeadline());
        }
    }


}