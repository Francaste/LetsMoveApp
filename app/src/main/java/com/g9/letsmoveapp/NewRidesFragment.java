package com.g9.letsmoveapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
/*
HAY QUE RESOLVER EL PROBLEMA DEL ADAPTADOOR PORQUE NO DEJA METER EL THIS DE LA LECHE

    public void addNewRide(View view){
        //ET no es el extraterrestre, es EditText
        EditText nameET = view.findViewById(R.id.name_ride);
        String name = nameET.getText().toString();
        EditText origenET = view.findViewById(R.id.origen);
        String orig = origenET.getText().toString();
        EditText destinoET = view.findViewById(R.id.destino);
        String dest = destinoET.getText().toString();

        //Chanchullo a resolver
        Button salidaET = view.findViewById(R.id.button_horasalida);
        int hora_salida = 342342;
        Button llegadaET = view.findViewById(R.id.button_horasalida);
        int hora_llegada = 3119;

        EditText lat_origenET = view.findViewById(R.id.lat_orig);
        String lat_origen = lat_origenET.getText().toString();
        EditText lng_origenET = view.findViewById(R.id.lng_orig);
        String lng_origen = lng_origenET.getText().toString();
        EditText lat_destinoET = view.findViewById(R.id.lat_dest);
        String lat_dest = lat_destinoET.getText().toString();
        EditText lng_destinoET = view.findViewById(R.id.lng_dest);
        String lng_dest = lng_destinoET.getText().toString();
        EditText tipoET = view.findViewById(R.id.tipo_ride);
        String tipo = tipoET.getText().toString();
        EditText limiteET = view.findViewById(R.id.hora_limite);
        String limite = limiteET.getText().toString();
        EditText precioET = view.findViewById(R.id.precio);
        String precio = precioET.getText().toString();
        EditText periodET = view.findViewById(R.id.period);
        String period = periodET.getText().toString();
        EditText programET = view.findViewById(R.id.programacion);
        String program = programET.getText().toString();

        DatabaseAdapter DBadapter = new DatabaseAdapter();
        DBadapter.createRIDES(String name, String orig, String lat_origen, String lng_origen,
                String hora_llegada, String dest, String lat_dest, String lng_dest,
                String hora_salida,String tipo, String limite, String precio, String period, String program);
*/

        /*      name_ride / origen / destino / button_horasalida / button_horallegada
                /lat_orig /lng_orig /lat_dest /lng_dest /tipo_ride / hora_limite
                /precio / period / programacion --- add_button
        */

    }

}