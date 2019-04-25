package com.g9.letsmoveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyRidesFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myrides, container, false);

        // Click Listener para lanzar actividad ViajesActuales
        Button rides_actuales = view.findViewById(R.id.rides_actuales);
        rides_actuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViajesActuales.class);
                startActivity(intent);
            }
        });

        // Click Listener para lanzar actividad ViajesAnteriores
        Button rides_anteriores= view.findViewById(R.id.rides_anteriores);
        rides_actuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViajesAnteriores.class);
                startActivity(intent);
            }
        });

        // Click Listener para lanzar actividad GastosViajes
        Button rides_gastos = view.findViewById(R.id.rides_gastos);
        rides_actuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GastosViajes.class);
                startActivity(intent);
            }
        });

        // Click Listener para lanzar actividad TestWeatherActivity
        Button test_weather = view.findViewById(R.id.test_weather);
        rides_actuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TestWeatherActivity.class);
                startActivity(intent);
            }
        });





        return view;
    }
}