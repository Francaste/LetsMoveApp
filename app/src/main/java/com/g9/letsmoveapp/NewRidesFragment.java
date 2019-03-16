package com.g9.letsmoveapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewRidesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newrides, container, false);

        Button button_horasalida = (Button)view.findViewById(R.id.button_horasalida);
        button_horasalida.setOnClickListener(new View.OnClickListener(){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(fragmentManager, "time picker");
            }
        });

        Button button_horallegada = (Button)view.findViewById(R.id.button_horallegada);
        button_horallegada.setOnClickListener(new View.OnClickListener(){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(fragmentManager, "time picker");
            }
        });

        return view;
    }

}