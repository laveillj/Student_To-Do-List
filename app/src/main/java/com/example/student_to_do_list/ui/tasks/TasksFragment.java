// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list.ui.tasks;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.student_to_do_list.DatabaseHelper;
import com.example.student_to_do_list.R;
import com.example.student_to_do_list.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

//Fragment pour l'onglet Task List appartenant à la main activity, ce fragment gère une recycler view contenant les items des taches
public class TasksFragment extends Fragment {

    public View view;
    public TasksFragment mFragment;
    public List<Task> tasksList = new ArrayList<>();
    public RecyclerView recyclerView;
    public TasksRVAdapter rvAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasks, container, false);
        mFragment = this;

    //### RECYCLER VIEW ###
        //create RV adapter
        rvAdapter = new TasksRVAdapter(tasksList, getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tasks);

        //Enable animation on task expansion
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

        // set RV layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // set adapter to RV
        recyclerView.setAdapter(rvAdapter);
        // RV size doesn't depend on amount of content
        recyclerView.hasFixedSize();

        // Set the RV item divider decoration (strike between each item)
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL)
        );

        //Écoute des évenements sur chaque item
        rvAdapter.setOnItemClickListener(new TasksRVAdapter.OnItemClickListener() {
            //Pour un click sur l'item on exécute la focntion suivante
            @Override
            public void onItemClick(int position) {
                Log.d("test","Clicked item at position number " + tasksList.get(position) + " in RecyclerView");
            }

            //Pour un click sur la case de complétion de la tache on exécute la fonction suivante
            @Override
            public void onDeleteClick(int position) {
                Log.d("test","Deleted item at position number " + tasksList.get(position) + " in RecyclerView");
                View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                deleteItem(v, mFragment, tasksList.get(position), position); //(voir fonction deleteItem)
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        //La recyclerview est mise à jour à chaque fois que l'on affiche le fragment
        super.onResume();
        db = new DatabaseHelper(getContext());
        this.updateTasksFromDb(db);
    }

    //Cette fonction permet de mettre à jour la liste des taches dans la RV à partir de la database
    public void updateTasksFromDb(DatabaseHelper pDB) {
        //Get tasks from database
        List<Task> newList = pDB.getAllTasks();
        if(newList.size() != tasksList.size()) {
            //On efface la liste précédente avant de lui donner la valeurs de l'ensemble de taches récupéré
            tasksList.clear();
            tasksList.addAll(newList);
            rvAdapter.notifyDataSetChanged();  //Notify adapter
        }
    }

    //deleteItem() permet de supprimer une tache de la RV avec une animation (silde de l'item vers la droite)
    private void deleteItem(View rowView, TasksFragment pFragment, Task pTask, int position) {
        //Lors de l'animation la case correspondant à la tache complétée change (case cochée)
        Button task_status_button = rowView.findViewById(R.id.task_item_status_button);
        task_status_button.setForeground(getContext().getResources().getDrawable(R.drawable.task_status_1));
        //On initialise l'animation
        Animation anim = AnimationUtils.loadAnimation(requireContext(),
                android.R.anim.slide_out_right);
        anim.setDuration(300); //durée de l'animation
        rowView.startAnimation(anim);

        //Nous jouons ensuite l'animation
        new Handler().postDelayed(new Runnable() {
            public void run() {
                db = new DatabaseHelper(getContext());
                db.deleteTask(pTask.getId());  //On retire la tache correspondante de la base de données
                updateTasksFromDb(db); //Mise à jour le la RV
                task_status_button.setForeground(getContext().getResources().getDrawable(R.drawable.task_status_0));

                showSnackbarActionCall(db, pTask);  //On lance la snackbar
            }
        }, anim.getDuration());
    }

    //Cette fonction permet de générer une Snack Bar qui propose à l'utilisateur d'annuler la suppression d'une tache
    private void showSnackbarActionCall(DatabaseHelper pDB, Task pTask) {
        Snackbar snackbar = Snackbar
                .make(this.view, "1 Task complete", Snackbar.LENGTH_LONG)
                .setAction("CANCEL", new View.OnClickListener() {
                    //Si l'utilisateur clique sur le bouton CANCEL on regénère la tache qui vient d'être supprimé
                    @Override
                    public void onClick(View view) {
                        pDB.createTask(pTask);
                        updateTasksFromDb(pDB);
                        Snackbar snackbar1 = Snackbar.make(view, "Task is restored", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();  //Affichage de la snack bar
    }
}