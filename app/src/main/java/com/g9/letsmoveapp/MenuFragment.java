package com.g9.letsmoveapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Button button_profile = view.findViewById(R.id.button_profile);
        Button button_myrides = view.findViewById(R.id.button_myrides);
        Button button_newrides = view.findViewById(R.id.button_newrides);

        //------------BOTON: MI PERFIL----------------
        //Aquí se establece el escuchador OnCLick para el boton
        button_profile.setOnClickListener(new View.OnClickListener(){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, new ProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //------------BOTON: MIS VIAJES----------------
        //Aquí se establece el escuchador OnCLick para el boton
        button_myrides.setOnClickListener(new View.OnClickListener(){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, new MyRidesFragment());
                //Con esto vamos hacia el fragment anterior
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //------------BOTON: NUEVO VIAJE----------------
        //Aquí se establece el escuchador OnCLick para el boton
        button_newrides.setOnClickListener(new View.OnClickListener(){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, new TransitionNewRides());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });





        return view;
    }





}