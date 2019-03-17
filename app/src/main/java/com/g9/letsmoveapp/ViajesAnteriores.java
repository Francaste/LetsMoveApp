package com.g9.letsmoveapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class ViajesAnteriores extends Activity {

    //Array de myRides
    ArrayList<VisualViajes> listaViajesAnt;
    RecyclerView recyclerViajesAnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rides_anteriores);

        //Cosas de listas MyRides

        listaViajesAnt=new ArrayList<>();
        llenarViajesAnteriores(listaViajesAnt);
        recyclerViajesAnt=(RecyclerView)findViewById(R.id.recyclerAnt);
        recyclerViajesAnt.setLayoutManager(new LinearLayoutManager(this));
        adaptadorDatosAnt adapterAnt = new adaptadorDatosAnt(this,listaViajesAnt);
        recyclerViajesAnt.setAdapter(adapterAnt);
    }

    //Esto es de lo nuevo

    private void llenarViajesAnteriores( ArrayList<VisualViajes> listaViajesAnt){
        listaViajesAnt.add(new VisualViajes("Casa encantada","Leganés-Cuenca | 1/2/19 | 8:00-11:00 | 15'",R.mipmap.ic_scoobydoo_round));


    }

}
