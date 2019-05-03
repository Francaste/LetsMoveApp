package com.g9.letsmoveapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.g9.letsmoveapp.TestWeatherActivity.LOG_TAG;

public class GastosViajes extends AppCompatActivity {
    private static final String LOG_TAG = "GastosViajesLOG";
    //RidesDataModel ridesDataModel = null; //new RidesDataModel();//leemos todos los registros de base de datos
    RidesDataModel ridesDataModel = new RidesDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rides_gastos);

        //Del método getGastos voy a obtener un arraylist de objetos RidesDataModel con el precio
        //cambiado según la base de datos db_rides


        //RidesDataModel ridesDataModel = null;
        //creamos el objeto datamodel que iremos modificando

        ArrayList gastosArrayList =getGastos();
       // ArrayList<Integer> gastosArray = new ArrayList<>();

        int i = 0;
        int dinero_gastado=0;
        RidesDataModel ridesModel; //Será el objeto ridesdatamodel auxiliar
        TextView precio = (TextView) findViewById(R.id.precio_viaje);



            while (gastosArrayList.size() > i) {
                ridesModel = (RidesDataModel) gastosArrayList.get(i);
                int gastos = (int)ridesModel.getPrecio();
                //gastosArray.add(gastos);
                dinero_gastado = dinero_gastado + gastos;
                Log.d(LOG_TAG, "dinero_gastado: " + dinero_gastado);

                i++;
            }

        String aux = dinero_gastado + " €";
        precio.setText(aux);


    }

    public ArrayList getGastos() {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_RIDES, null, 1);
        //abres conexion bbdd
        SQLiteDatabase db_rides = conn.getReadableDatabase();//para leer

        String selectQuery = "SELECT * FROM " + DatabaseAdapter.DB_RIDES;
        //haces la sentencia de lectura de todos los registros

        Cursor cursor = db_rides.rawQuery(selectQuery, null, null);
        //utilizamos directamente la query

        /*
        ArrayList ridesModelArrayList = new ArrayList();//lista de resultados
        RidesDataModel ridesDataModel;//creamos datamodel
        */
        ArrayList ridesModelArrayList = new ArrayList();//lista de resultados
        if (cursor.moveToFirst()) {//se va al principio de la tabla
            RidesDataModel ridesDataModel;

            while (cursor.moveToNext()) {//si hay registros aún
                ridesDataModel=new RidesDataModel();
                //damos los valores del registro de base de datos a nuestro modelo de datos de coche temporal
                //Solamente nos interesa setear el modelo porque lo demás es prescindible para el spinner
                //No dará error porque en carsDataModel hay un objeto creado completo por defecto
                ridesDataModel.setPrecio(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_PRECIO)));
                Log.d(LOG_TAG, "Precio: " + ridesDataModel.getPrecio());
                //añadimos el modelo de datos a la lista
                ridesModelArrayList.add(ridesDataModel);

            }
        }
        cursor.close();//cerramos el cursor, dejamos de leer la tabla
        db_rides.close();//cerramos conexion

        return (ridesModelArrayList);
    }





/*
        finish();//cerramos la activity

        return gastosArray;


    }
*/


}
