package com.g9.letsmoveapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class ViajesActuales extends Activity {

    //Array de myRides
    ArrayList<VisualViajes> listaViajes;
    RecyclerView recyclerViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rides_actuales);


        //Cosas de listas MyRides


        listaViajes=new ArrayList<>();
        llenarViajesActuales(listaViajes);
        recyclerViajes=(RecyclerView)findViewById(R.id.recyclerAct);
        recyclerViajes.setLayoutManager(new LinearLayoutManager(this));
        adaptadorDatos adapter = new adaptadorDatos(this,listaViajes);
        recyclerViajes.setAdapter(adapter);
    }

    //Esto es de lo nuevo

    private void llenarViajesActuales( ArrayList<VisualViajes> listaViajes){
        listaViajes.add(new VisualViajes("Universidad","Illescas-Leganés | 15/3/19 | 8:00-9:00 | 15'",R.mipmap.ic_delorean_round));
        listaViajes.add(new VisualViajes("Sede Microsoft","Leganés-Pozuelo | 14/3/19 | 15:00-19:00 | 5'",R.mipmap.ic_cochefantastico_round));
        listaViajes.add(new VisualViajes("Carreras Toretto","Leganés-Circuito Jarama 16/3/19| 9:00-14:00 | 10'",R.mipmap.ic_toretto_round));
        listaViajes.add(new VisualViajes("Visita yaya","Illescas-Toledo | 22/3/19 | 11:00-12:00 | 10'",R.mipmap.ic_herbie_round));
        listaViajes.add(new VisualViajes("Quedada supers","Illescas-Plaza España | 23/3/19 | 17:00-17:30 | 15'",R.mipmap.ic_batmobile_round));
        listaViajes.add(new VisualViajes("Viaje family","Illescas-Benidorm | 4/6/19 | 9:00-13:00 | 20'",R.mipmap.ic_sunshine_round));

    }

}
