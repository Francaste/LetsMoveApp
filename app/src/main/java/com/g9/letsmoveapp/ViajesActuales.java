package com.g9.letsmoveapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import static android.net.Uri.parse;

public class ViajesActuales extends Activity {
    private static final String LOG_TAG = "LEER_VIAJ_aCT";

    //Array de myRides
    ArrayList<RidesDataModel> ridesTable;
    Uri photouri;
    RecyclerView recyclerViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rides_actuales);


        //Cosas de listas MyRides

        ridesTable = getRides("");

        photouri=getUri();
        recyclerViajes = (RecyclerView) findViewById(R.id.recyclerAct);
        recyclerViajes.setLayoutManager(new LinearLayoutManager(this));
        adaptadorDatos adapter = new adaptadorDatos(this, ridesTable,photouri);
        recyclerViajes.setAdapter(adapter);
    }

    /*//Esto es de lo nuevo

    private void llenarViajesActuales(ArrayList resultList) {
        RidesDataModel rideModel;
        int i=0;
        while (resultList.size() > i) {
            rideModel=(RidesDataModel)resultList.get(i);
            resultList.add(new VisualViajes(rideModel.getR_name(), rideModel.getOrigen()+"-"+rideModel.getDestino()
                    +" | " +rideModel.getFecha_salida()+" | "+rideModel.getHora_salida()+"-"+rideModel.getHora_llegada()+
                    " | "+rideModel.getHora_limite()+"'", R.mipmap.ic_delorean_round));
        i++;
        }
    }*/
//este método recibe parámetro un string, que sacamos del text view título de la tarjeta de viajes actuales.

    public ArrayList getRides(String r_nombre) {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_RIDES, null, 1);
        //abres conexion bbdd
        SQLiteDatabase db_rides = conn.getReadableDatabase();//para leer

//*****************************PARA RIDES********************
        //ACTUALES
        //SELECCIONAR TODOS LOS REGISTROS, POR CADA REGISTRO CREAS UN LINEAR LAYOUT CON LOS CAMPOS
        //
        //CLICK EN UNO CONCRETO
        //SELECCIONAR EN BBDD REGISTROS QUE TENGAN EL NOMBRE DEL TÍTULO DEL LINEAR LAYOUT

        //cómo	cogemos el valor del textview, en el linear layout de cada tarjeta y lo pasamos al query de sql


        String selectCondicional = " WHERE " + DatabaseAdapter.KEY_R_NAME + " IN '" + r_nombre + "'";
        //se usa para leer el registro metiendo el título de cada viaje
        //seleccionamos los registros para los que el valor del campo R_NAME
        // coincide con la variable nombre del textview de la tarjeta
        String selectQuery = "SELECT * FROM " + DatabaseAdapter.DB_RIDES;
        // + selectCondicional;
        //haces la sentencia de lectura de todos los registros

        Cursor cursor = db_rides.rawQuery(selectQuery, null, null);
        //utilizamos directamente la query
        ArrayList ridesModelArrayList = new ArrayList();//lista de resultados

        if (cursor.moveToFirst()) {//se va al principio de la tabla
            RidesDataModel ridesDataModel;//creamos datamodel
            while (cursor.moveToNext()) {//si hay registros aún
                ridesDataModel = new RidesDataModel();//leemos todos los registros de base de datos
                //damos los valores del registro de base de datos a nuestro modelo de datos de coche temporal
                ridesDataModel.setR_name(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_R_NAME)));
                Log.d(LOG_TAG, "Name: " + ridesDataModel.getR_name());
                ridesDataModel.setOrigen(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_ORIGEN)));
                Log.d(LOG_TAG, "Origen: " + ridesDataModel.getOrigen());
                ridesDataModel.setLat_orig(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_LAT_ORIG)));
                Log.d(LOG_TAG, "LatOr: " + ridesDataModel.getLat_orig());
                ridesDataModel.setLng_orig(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_LNG_ORIG)));
                Log.d(LOG_TAG, "LngOr: " + ridesDataModel.getLng_orig());
                ridesDataModel.setFecha_salida(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_FECHA_SALIDA)));
                Log.d(LOG_TAG, "FechaSalida: " + ridesDataModel.getFecha_salida());
                ridesDataModel.setHora_salida(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_HORA_SALIDA)));
                Log.d(LOG_TAG, "HoraSalida: " + ridesDataModel.getHora_salida());
                ridesDataModel.setDestino(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DESTINO)));
                Log.d(LOG_TAG, "Destino: " + ridesDataModel.getDestino());
                ridesDataModel.setLat_dest(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_LAT_DEST)));
                Log.d(LOG_TAG, "LatDest: " + ridesDataModel.getLat_dest());
                ridesDataModel.setLng_dest(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_LNG_DEST)));
                Log.d(LOG_TAG, "LngDest: " + ridesDataModel.getLng_dest());
                ridesDataModel.setFecha_llegada(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_FECHA_LLEGADA)));
                Log.d(LOG_TAG, "FechaLlegada: " + ridesDataModel.getFecha_llegada());
                ridesDataModel.setHora_llegada(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_HORA_LLEGADA)));
                Log.d(LOG_TAG, "HoraSalida: " + ridesDataModel.getHora_llegada());
                ridesDataModel.setTipo(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_TIPO)));
                Log.d(LOG_TAG, "Tipo: " + ridesDataModel.getTipo());
                ridesDataModel.setHora_limite(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_FECHA_LIMITE)));
                Log.d(LOG_TAG, "HoraLimite: " + ridesDataModel.getHora_limite());
                ridesDataModel.setPrecio(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_PRECIO)));
                Log.d(LOG_TAG, "Precio: " + ridesDataModel.getPrecio());
                ridesDataModel.setPeriod(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_PERIOD)));
                Log.d(LOG_TAG, "Periodicidad: " + ridesDataModel.getPeriod());
                ridesDataModel.setNum_viajerxs(cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_NUM_VIAJ)));
                Log.d(LOG_TAG, "NumViajerxs: " + ridesDataModel.getNum_viajerxs());


                //añadimos el modelo de datos a la lista
                ridesModelArrayList.add(ridesDataModel);
            }
        }
        cursor.close();//cerramos el cursor, dejamos de leer la tabla

        String message = "Viajes leídos correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        db_rides.close();//cerramos conexion
        //finish();//cerramos la activity
        return ridesModelArrayList;

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
