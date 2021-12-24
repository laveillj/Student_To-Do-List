// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_to_do_list.DatabaseHelper;
import com.example.student_to_do_list.Project;
import com.example.student_to_do_list.ProjectViewContentActivity;
import com.example.student_to_do_list.R;

import java.util.ArrayList;
import java.util.List;

//Fragment pour l'onglet Projects appartenant à la main activity, ce fragment gère une recycler view contenant pour item une project card
public class ProjectsFragment extends Fragment {

    //initialisation variable
    public static final String EXTRA_PROJECT_ID = "PROJECT_ID";
    public List<Project> projectsList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProjectRVAdapter rvAdapter;
    DatabaseHelper db;

    //Création de la view du fragment project
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        //create RV adapter
        rvAdapter = new ProjectRVAdapter(projectsList, getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_projects);

        // set RV layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        rvAdapter.setOnProjectClickListener(new ProjectRVAdapter.OnProjectClickListener() {
            @Override
            //Fonction permettant de démarrer une activité "observation des détails du projet" à partir d'un click sur un item dans la RV et d'affiché les informations pertinent au projet sélectionné
            public void onItemClick(int position) {
                Project project = projectsList.get(position);
                Log.d(" ### ", "Project got at position: " + project.getId());
                Intent intent = new Intent(getActivity(), ProjectViewContentActivity.class);
                String strProjectID = ""+project.getId();
                intent.putExtra(EXTRA_PROJECT_ID, strProjectID); //Nous récupérons l'ID de la database associé à l'item cliqué et nous ferons un post traitement dans le ProjectViewContentActivity pour afficher les informations nécessaires pour le projet en question
                Log.d(" ### ", "EXTRA putted in intent: " + intent.getStringExtra(EXTRA_PROJECT_ID));
                startActivity(intent);
            }

            @Override
            //Fonction d'effaçant d'item projet, cette methode est appelé dans la RVAdapter des projets.
            public void onDeleteClick(int position) {
                Log.d("test","Deleted item at position number " + projectsList.get(position) + " in RecyclerView");
                View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                deleteItem(v, projectsList.get(position).getId());
            }

        });

        return view;
    }

    @Override
    public void onDestroy() { super.onDestroy(); }

    @Override
    //A la reprise du fragment projet, la recyclerview se met à jour avec les donnée de la database pour afficher des données pertinentes
    public void onResume() {
        super.onResume();
        db = new DatabaseHelper(getContext());
        this.updateProjectsFromDb(db);
    }

    public void updateProjectsFromDb(DatabaseHelper pDB) {
        //Get projects from database
        List<Project> newList = pDB.getAllProjects();

        if(newList.size() != projectsList.size()) {
            projectsList.clear();
            projectsList.addAll(newList);
            rvAdapter.notifyDataSetChanged();  //Notify adapter
        }
    }

    //Fonction d'effaçage d'un item dans la recyclerview appelé en haut dans le onDeleteClick()
    private void deleteItem(View rowView, final long position) {
        //On initialise l'animation
        Animation anim = AnimationUtils.loadAnimation(requireContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300); //durée de l'animation
        rowView.startAnimation(anim);

        //Nous jouons ensuite l'animation
        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getContext());
                db.deleteProject(position);  //On retire la tache correspondante de la base de données
                db.deleteAllTasksUnderProject(position); //On supprime aussi toutes les taches sous le projet
                updateProjectsFromDb(db); //Mise à jour le la RV
            }
        }, anim.getDuration());
    }

}