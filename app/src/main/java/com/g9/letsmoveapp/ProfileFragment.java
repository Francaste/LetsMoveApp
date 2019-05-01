package com.g9.letsmoveapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //return inflater.inflate(R.layout.fragment_profile, container, false);
        //Obtenemos el view layout para poder coger los elementos
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Click Listener para lanzar actividad NewRides

        Button miPerfil = (Button) view.findViewById(R.id.button_mi_perfil);
        Button misCoches = (Button) view.findViewById(R.id.button_mis_coches);

       miPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MiPerfil.class);
                startActivity(intent);
            }
        });

        misCoches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MisCoches.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
