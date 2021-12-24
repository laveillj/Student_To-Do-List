// Student To-Do-List - Unité "IHM et programmation d'applications graphiques"
// Jean-Michel HA et Jérémy LAVEILLE - E4FE ESIEE Paris 2021

package com.example.student_to_do_list.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.student_to_do_list.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}