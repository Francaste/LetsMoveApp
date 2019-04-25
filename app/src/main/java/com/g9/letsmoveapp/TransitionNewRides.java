package com.g9.letsmoveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TransitionNewRides extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Obtenemos el view layout para poder coger los elementos
        final View view = inflater.inflate(R.layout.fragment_transition_new_rides, container, false);
        // Click Listener para lanzar actividad NewRides
        Button planificar_viaje = (Button) view.findViewById(R.id.planificar_viaje);
        planificar_viaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewRidesFragment.class);
                startActivity(intent);
            }
        });
        return view;
    }
}