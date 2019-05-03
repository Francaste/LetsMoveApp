package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.net.Uri.parse;

public class MisCoches extends Activity {
    private static final String LOG_TAG = "LEER_COCHE";
    ArrayList<CarsDataModel> carsTable;
    Uri photouri;
    RecyclerView recyclerViajes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_coches);

        //Cosas de listas MisCoches

        carsTable = get_cars();

        photouri = getUri();

        recyclerViajes = (RecyclerView) findViewById(R.id.recyclerCar);
        recyclerViajes.setLayoutManager(new LinearLayoutManager(this));
        adaptadorDatosCars adapter = new adaptadorDatosCars(this, carsTable, photouri);
        recyclerViajes.setAdapter(adapter);
    }

    //************Vuelve aquí a revisar esto
    public void nuevoCoche(View view) {
        Intent intent = new Intent(this, NuevoCoche.class);
        startActivity(intent);
    }

    public ArrayList get_cars() {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_CARS, null, 1);
        //abres conecxion bbdd
        SQLiteDatabase db_cars = conn.getReadableDatabase();//para leer

        String selectQuery = "SELECT * FROM " + DatabaseAdapter.DB_CARS;
        //haces la sentencia de lectura de todos los registros

        Cursor cursor = db_cars.rawQuery(selectQuery, null, null);
        //utilizamos directamente la query
        ArrayList carsModelArrayList = new ArrayList();//lista de resultados

        if (cursor.moveToFirst()) {//se va al principio de la tabla
            CarsDataModel carsDataModel;//creamos datamodel
            while (cursor.moveToNext()) {//si hay registros aún
                carsDataModel = new CarsDataModel();//leemos todos los registros de base de datos
                //damos los valores del registro de base de datos a nuestro modelo de datos de coche temporal
                carsDataModel.setModel(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_MODELO)));
                Log.d(LOG_TAG, "Model: " + carsDataModel.getModel());
                carsDataModel.setColor(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_COLOR)));
                Log.d(LOG_TAG, "Color: " + carsDataModel.getColor());
                carsDataModel.setPlazas(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_PLAZAS)));
                Log.d(LOG_TAG, "Plazas: " + carsDataModel.getPlazas());
                carsDataModel.setSize(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_SIZE)));
                Log.d(LOG_TAG, "Size: " + carsDataModel.getSize());
                carsDataModel.setAntig(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_ANTIG)));
                Log.d(LOG_TAG, "Antig: " + carsDataModel.getAntig());
                carsDataModel.setUri(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_URI)));
                Log.d(LOG_TAG, "Uri: " + carsDataModel.getUri());
                carsDataModel.setConsumo(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_CONSUMO)));
                Log.d(LOG_TAG, "Consumo: " + carsDataModel.getConsumo());

                //añadimos el modelo de datos a la lista
                carsModelArrayList.add(carsDataModel);
            }
        }
        cursor.close();//cerramos el cursor, dejamos de leer la tabla

        String message = "Coches leídos correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        db_cars.close();//cerramos conexion
        //finish();//cerramos la activity
        return carsModelArrayList;

    }

    public Uri getUri() {
        Uri uriPhoto=null;
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_CARS, null, 1);
        //abres conecxion bbdd
        SQLiteDatabase db_cars = conn.getReadableDatabase();//para leer

        String selectQuery = "SELECT * FROM " + DatabaseAdapter.DB_CARS;
        //haces la sentencia de lectura de todos los registros

        Cursor cursor = db_cars.rawQuery(selectQuery,null,null);
        //utilizamos directamente la query
        ArrayList carsModelArrayList = new ArrayList();//lista de resultados

        if(cursor.moveToFirst()){//se va al principio de la tabla
            CarsDataModel carsDataModel;//creamos datamodel
            while (cursor.moveToNext()) {//si hay registros aún
                carsDataModel= new CarsDataModel();//leemos todos los registros de base de datos
                //damos los valores del registro de base de datos a nuestro modelo de datos de coche temporal
                //carsDataModel.setUri(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_URI)));
                uriPhoto=parse(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_URI)));
                Log.d(LOG_TAG,"Uri: "+carsDataModel.getUri());

                //añadimos el modelo de datos a la lista
                carsModelArrayList.add(carsDataModel);
            }
        }
        cursor.close();//cerramos el cursor, dejamos de leer la tabla

        String message = "Coches leídos correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        db_cars.close();//cerramos conexion
        //finish();//cerramos la activity
        return uriPhoto;

    }

}
