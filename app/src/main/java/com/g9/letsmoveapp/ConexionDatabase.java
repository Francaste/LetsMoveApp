package com.g9.letsmoveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ConexionDatabase extends SQLiteOpenHelper {

        //final String DB_CREATE_CARS="CREATE TABLE db_cars (id_cars INTEGER, name_c TEXT, modelo TEXT, plazas INTEGER, color TEXT, size TEXT, consumo REAL, antig INTEGER)";

        public ConexionDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Creamos lsas dos tablas que tenemos ahora mismo, que son CARS y RIDES
            db.execSQL(DatabaseAdapter.DB_CREATE_CARS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            //PREGUNTA: No sé dónde pasa la newVersion a ser oldVersion, se cambia sola?
            db.execSQL("DROP TABLE IF EXISTS "+DatabaseAdapter.DB_CARS);
            onCreate(db);
        }
    }
