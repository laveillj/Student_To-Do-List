// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list.ui.projects;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.student_to_do_list.Project;
import com.example.student_to_do_list.R;

import java.util.List;

//Adapteur pour le recycler view présent dans l'onglet Project
public class ProjectRVAdapter extends RecyclerView.Adapter<ProjectRVAdapter.ItemViewHolder2> {

    private List<Project> projectsList;
    private static final String TAG = "ProjectRVAdapter";
    private OnProjectClickListener mListener;
    public Context mContext;

    public interface OnProjectClickListener {
        public void onItemClick(int position);
        public void onDeleteClick(int position);
    }

    public void setOnProjectClickListener(OnProjectClickListener pListener) {
        this.mListener = pListener;
    }

    public ProjectRVAdapter(List<Project> projectsList, Context context) {
        this.projectsList = projectsList;
        this.mContext = context;
    }

    @Override
    //Fonction définissant le style de l'item qui sera utilisé lors de sa création dans la recyclerview projet (voir project_cart.xml)
    public ItemViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_card, parent, false);
       return new ItemViewHolder2(view, mListener, mContext);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder2 holder, int position) {
        Project project = projectsList.get(position); //Récupère la position lié à l'item présent dans la recyclerview
        holder.bind(project);
    }

    @Override
    //Fonction permettant de récupérer le nombre d'item crée dans la recyclerview projet
    public int getItemCount() {
        return projectsList == null ? 0 : projectsList.size();
    }

    //Création d'une classe pour l'item project card avec l'ensemble de ces éléments
    public class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private TextView title;
        private TextView project_id;
        private TextView deadline;
        Button proj_popup;
        private View subItem;
        RelativeLayout project_layout;

        public ItemViewHolder2(View itemView, final OnProjectClickListener pListener, Context pContext) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name_project);
            project_id = (TextView) itemView.findViewById(R.id.id_project);
            deadline = (TextView) itemView.findViewById(R.id.deadline_project_value);
            project_layout = itemView.findViewById(R.id.layout_project_card);
            proj_popup = itemView.findViewById(R.id.project_popup);
            proj_popup.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            pListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        private void bind(Project project) {
            title.setText(project.getTitle());
            project_id.setText("P" + project.getId());
            deadline.setText(project.getDeadline());
        }

        @Override
        //Fonction pop up menu lors du click sur l'item
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        //Fonction pour les choix qui sont présent à l'interieur du pop up menu
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.deletePJ:
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                    return true;

                default:
                    return false;
            }
        }
    }




}