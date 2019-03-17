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

        // llenarViajesActuales();

      /*  listaViajes=new ArrayList<>();
        recyclerViajes=(RecyclerView)findViewById(R.id.recycler);
        recyclerViajes.setLayoutManager(new LinearLayoutManager(this));
        llenarViajesActuales();
        adaptadorDatos adapter = new adaptadorDatos(listaViajes);
        recyclerViajes.setAdapter(adapter);
*/
    }

    //Esto es de lo nuevo
    /*
    private void llenarViajesActuales(){
        listaViajes.add(new VisualViajes("Universidad","Illescas-Leganés | 15/3/19 | 8:00-9:00 | 15'",R.drawable.delorean));
        listaViajes.add(new VisualViajes("Sede Microsoft","Leganés-Pozuelo | 14/3/19 | 15:00-19:00 | 5'",R.drawable.cochefantastico));
        listaViajes.add(new VisualViajes("Carreras Toretto","Leganés-Circuito Jarama 16/3/19| 9:00-14:00 | 10'",R.drawable.cochetoretto));
        listaViajes.add(new VisualViajes("Visita yaya","Illescas-Toledo | 22/3/19 | 11:00-12:00 | 10'",R.drawable.herbie));
        listaViajes.add(new VisualViajes("Quedada supers","Illescas-Plaza España | 23/3/19 | 17:00-17:30 | 15'",R.drawable.batmobile));

    }
*/
}
