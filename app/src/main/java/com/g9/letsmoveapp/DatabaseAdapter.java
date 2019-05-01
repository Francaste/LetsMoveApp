package com.g9.letsmoveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// Clase adaptadora que nos va a facilitar el uso de la BD
public class DatabaseAdapter {
    public static final String TAG = "DatabaseAdapter"; // Usado en los mensajes de Log

    //Nombre de las base de datos, tablas (en este caso una) y versión
    //Ponemos static para poder acceder desde cualquier parte del código
    //hay que poner la extension
    public static final String DB_CARS = "CARS";
    public static final String DB_RIDES = "RIDES";
    // Version de la base de datos
    public static final int DB_VERSION = 1;
    //campos de la tabla de la base de datos DE COCHES
    public static final String KEY_C_ID_PK = "ID_C";
    public static final String KEY_MODELO = "MODELO";
    public static final String KEY_PLAZAS = "PLAZAS";
    public static final String KEY_COLOR = "COLOR";
    public static final String KEY_SIZE = "SIZE";
    public static final String KEY_CONSUMO = "CONSUMO"; //Litros cada 100km
    public static final String KEY_ANTIG = "ANTIG";
    public static final String KEY_URI="URI_FOTO";

    //campos de la tabla de la base de datos DE RIDES
    public static final String KEY_R_ID_PK = "ID_R";
    public static final String KEY_R_NAME = "NAME_R";
    public static final String KEY_ORIGEN = "ORIGEN";
    public static final String KEY_LAT_ORIG = "LAT_ORIGEN"; //REZAMOS POR TENERLOS EN DECIMAL
    public static final String KEY_LNG_ORIG = "LNG_ORIGEN";
    public static final String KEY_FECHA_SALIDA = "FECHA_SALIDA";
    public static final String KEY_HORA_SALIDA = "HORA_SALIDA";
    public static final String KEY_DESTINO = "DESTINO";
    public static final String KEY_LAT_DEST = "LAT_DESTINO";
    public static final String KEY_LNG_DEST = "LNG_DESTINO";
    public static final String KEY_FECHA_LLEGADA = "FECHA_LLEGADA";
    public static final String KEY_HORA_LLEGADA = "HORA_LLEGADA";
    public static final String KEY_TIPO = "TIPO";
    public static final String KEY_FECHA_LIMITE = "FECHA_LIMITE";
    public static final String KEY_PRECIO = "PRECIO";
    public static final String KEY_PERIOD = "PERIODICIDAD";
    public static final String KEY_NUM_VIAJ = "NUM_VIAJ";


    // Sentencia SQL para crear las tablas de las bases de datos, tenemos que hacerlas públicas
    // si queremos acceder a ellas desde ConexionDatabase
    public static final String DB_CREATE_CARS = "create table " + DB_CARS + " (" +
            KEY_C_ID_PK + " integer primary key autoincrement, " +
            KEY_MODELO + " text not null, " +
            KEY_PLAZAS + " integer not null, " +
            KEY_SIZE + " text not null, " +
            KEY_COLOR + " text not null, " +
            KEY_ANTIG + " real not null, " +
            KEY_URI + " text not null, " +
            KEY_CONSUMO + " integer not null);";



    public static final String DB_CREATE_RIDES = "create table " + DB_RIDES + " ( " +
            KEY_R_ID_PK + " integer primary key autoincrement, " +
            KEY_R_NAME + " text not null, " +
            KEY_ORIGEN + " text not null, " +
            KEY_LAT_ORIG + " real not null, " +
            KEY_LNG_ORIG + " real not null, " +
            KEY_FECHA_SALIDA + " text not null, " +
            KEY_HORA_SALIDA + " text not null, " +
            KEY_DESTINO + " text not null, " +
            KEY_LAT_DEST + " real not null, " +
            KEY_LNG_DEST + " real not null, " +
            KEY_FECHA_LLEGADA + " text not null, " +
            KEY_HORA_LLEGADA + " text not null, " +
            KEY_TIPO + " text not null, " +
            KEY_FECHA_LIMITE + " text not null, " +
            KEY_PRECIO + " integer not null, " +
            KEY_PERIOD + " text not null, " +
            KEY_NUM_VIAJ + " text not null);";

}


