// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;

import java.util.List;

//Adapteur pour le recycler view présent dans l'onglet task
public class TasksRVAdapter extends RecyclerView.Adapter<TasksRVAdapter.ItemViewHolder> {

    private List<Task> tasksList;
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
    //Fonction définissant le style de l'item qui sera utilisé lors de sa création dans la recyclerview tache (voir fragment_tasks_item.xml)
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks_item, parent,
                false);
        return new ItemViewHolder(view, mListener, mContext);
    }

    @Override
    //On affiche la tache à la position correspondante et avec le bon status d'expansion
    //Expansion lors du click sur l'item => Description et Deadline visible
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Task task = tasksList.get(position);

        holder.bind(task);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = task.isExpanded();
            task.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return tasksList == null ? 0 : tasksList.size();
    }

    //Création d'une classe pour l'item task avec l'ensemble de ces éléments
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView task_project;
        private TextView description;
        private TextView deadline;
        private View subItem;
        private Button task_status_button;

        public ItemViewHolder(View itemView, final OnItemClickListener pListener, Context pContext) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tasks_item_title);
            task_project = (TextView) itemView.findViewById(R.id.tasks_item_project);
            description = (TextView) itemView.findViewById(R.id.task_sub_item_desc);
            deadline = (TextView) itemView.findViewById(R.id.task_sub_item_deadline_value);
            subItem = itemView.findViewById(R.id.task_sub_item);
            task_status_button = itemView.findViewById(R.id.task_item_status_button);

            //Gestion de l'appui sur la case de completion de la tache
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

        //Gestion de la visibilité de tous les éléments pour l'expansion de la tache
        private void bind(Task task) {
            boolean expanded = task.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(task.getTitle());
            description.setText(task.getDescription());
            deadline.setText(task.getDeadline());

            //On affiche aussi le numéro correspond si la tache est associée à un projet
            long projectID = task.getProjectId();
            if(projectID != 0)
                task_project.setText("P" + projectID);
            else
                task_project.setText("");
        }
    }

}