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
    private static final String TAG = "DatabaseAdapter"; // Usado en los mensajes de Log

    //Nombre de la base de datos, tablas (en este caso una) y versión
    private static final String DB_NAME = "LETSMOVE_DB";
    private static final String DB_CARS = "CARS";
    private static final String DB_RIDES = "RIDES";

    private static final int DB_VERSION = 1;

    //campos de la tabla de la base de datos DE COCHES
    public static final String KEY_C_ID_PK = "ID_C";
    public static final String KEY_C_NAME = "NAME_C";
    public static final String KEY_MODELO = "MODELO";
    public static final String KEY_PLAZAS = "PLAZAS";
    public static final String KEY_COLOR = "COLOR";
    public static final String KEY_SIZE = "SIZE";
    public static final String KEY_CONSUMO = "CONSUMO"; //Litros cada 100km
    public static final String KEY_ANTIG = "ANTIG";

    //campos de la tabla de la base de datos DE RIDES
    public static final String KEY_R_ID_PK = "ID_R";
    public static final String KEY_R_NAME = "NAME_R";
    public static final String KEY_ORIGEN = "ORIGEN";
    public static final String KEY_LAT_ORIG = "LAT_ORIGEN"; //REZAMOS POR TENERLOS EN DECIMAL
    public static final String KEY_LNG_ORIG = "LNG_ORIGEN";
    public static final String KEY_FECHA_SALIDA = "FECHA_SALIDA";
    public static final String KEY_DESTINO = "DESTINO";
    public static final String KEY_LAT_DEST = "LAT_DESTINO";
    public static final String KEY_LNG_DEST = "LNG_DESTINO";
    public static final String KEY_FECHA_LLEGADA = "FECHA_LLEGADA";
    public static final String KEY_TIPO = "TIPO";
    public static final String KEY_FECHA_LIMITE = "FECHA_LIMITE";
    public static final String KEY_PRECIO = "PRECIO";
    public static final String KEY_PERIOD = "PERIODICIDAD";
    public static final String KEY_PROGRAM = "PROGRAMACION";


    // Sentencia SQL para crear las tablas de las bases de datos
    private static final String DB_CREATE_CARS = "create table " + DB_CARS + " (" +
            KEY_C_ID_PK +" integer primary key autoincrement, " +
            KEY_C_NAME + " text not null, " +
            KEY_MODELO + " text not null, "+
            KEY_PLAZAS + " integer not null, "+
            KEY_COLOR + " text not null, "+
            KEY_SIZE + " text not null, "+
            KEY_CONSUMO + " real not null, "+
            KEY_ANTIG + " integer not null);";

    private static final String DB_CREATE_RIDES = "create table " + DB_RIDES + " (" +
            KEY_R_ID_PK +" integer primary key autoincrement, " +
            KEY_R_NAME + " text not null, " +
            KEY_ORIGEN + " text not null, "+
            KEY_LAT_ORIG + " real not null, "+
            KEY_LNG_ORIG + " real not null, "+
            KEY_FECHA_SALIDA + " text not null, "+
            KEY_DESTINO + " text not null, "+
            KEY_LAT_DEST + " real not null, "+
            KEY_LNG_DEST + " real not null, "+
            KEY_FECHA_LLEGADA + " text not null, "+
            KEY_TIPO + " text not null, "+
            KEY_FECHA_LIMITE + " text not null, "+
            KEY_PRECIO + " integer not null, "+
            KEY_PERIOD + " text not null, "+
            KEY_PROGRAM + " text not null);";


    //Aquí se está creando la tabla, añadiendo a la tabla cada campo, y diciéndole que esta tabla con este nombre
    //Tendrá estos campos de aquí

    //Cada uno de los parámetros tine que tener su tipo, en el caso del id será un primary key autoincrement porque
    //tiene que tener un número único que será incrementado con la creación de otros elementos
    // título es de tipo text, no puede ser nulo y body igual

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;
// Desconocido, álex dice que es un manager, gestor de ayudas, android me ayuda amigablemente a hacer mi tarea

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            //buscar qué es la versión
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Creamos lsas dos tablas que tenemos ahora mismo, que son CARS y RIDES
            db.execSQL(DB_CREATE_CARS);
            db.execSQL(DB_CREATE_RIDES);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            //PREGUNTA: No sé dónde pasa la newVersion a ser oldVersion, se cambia sola?
            db.execSQL("DROP TABLE IF EXISTS " + DB_CARS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_RIDES);

            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the daatabase to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */

    public DatabaseAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created dfads
     */
    public DatabaseAdapter open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDBHelper.close();
    }

//Comentao
    //Creamos la tabla de los viajes
    public long createRIDES(String NAME_R, String ORIGEN, String LAT_ORIGEN, String LNG_ORIGEN, String FECHA_LLEGADA,
                           String DESTINO, String LAT_DEST, String LNG_DEST, String FECHA_SALIDA,String TIPO,
                            String FECHA_LIMITE, String PRECIO, String PERIODICIDAD, String PROGRAMACION) {

        ContentValues initialValues_R = new ContentValues();
        initialValues_R.put(KEY_R_NAME, NAME_R);
        initialValues_R.put(KEY_ORIGEN, ORIGEN);
        initialValues_R.put(KEY_LAT_ORIG, LAT_ORIGEN);
        initialValues_R.put(KEY_LNG_DEST, LNG_ORIGEN);
        initialValues_R.put(KEY_FECHA_LLEGADA, FECHA_LLEGADA);
        initialValues_R.put(KEY_DESTINO, DESTINO);
        initialValues_R.put(KEY_LAT_DEST, LAT_DEST);
        initialValues_R.put(KEY_LNG_DEST, LNG_DEST);
        initialValues_R.put(KEY_FECHA_SALIDA, FECHA_SALIDA);
        initialValues_R.put(KEY_TIPO, TIPO);
        initialValues_R.put(KEY_FECHA_LIMITE, FECHA_LIMITE);
        initialValues_R.put(KEY_PRECIO, PRECIO);
        initialValues_R.put(KEY_PERIOD, PERIODICIDAD);
        initialValues_R.put(KEY_PROGRAM, PROGRAMACION);

        return mDB.insert(DB_RIDES, null, initialValues_R);
    }

    //Creamos la tabla de los viajes
    public long createCARS(String NAME_C, String MODELO, String PLAZAS, String COLOR, String SIZE,
                           String CONSUMO, String ANTIG) {

        ContentValues initialValues_C = new ContentValues();
        initialValues_C.put(KEY_C_NAME, NAME_C);
        initialValues_C.put(KEY_MODELO, MODELO);
        initialValues_C.put(KEY_PLAZAS, PLAZAS);
        initialValues_C.put(KEY_COLOR, COLOR);
        initialValues_C.put(KEY_SIZE, SIZE);
        initialValues_C.put(KEY_CONSUMO, CONSUMO);
        initialValues_C.put(KEY_ANTIG, ANTIG);

        return mDB.insert(DB_CARS, null, initialValues_C);
    }

     /** Delete the note with the given rowId
     *
     * @param rowId_R id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteRIDES(long rowId_R) {

        return mDB.delete(DB_RIDES, KEY_R_ID_PK + "=" + rowId_R, null) > 0;
    }

    public boolean deleteCARS(long rowId_C) {

        return mDB.delete(DB_CARS, KEY_C_ID_PK + "=" + rowId_C, null) > 0;
    }


    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */

    public Cursor fetchALLRIDES() {

        return mDB.query(DB_RIDES, new String[] {KEY_R_ID_PK, KEY_R_NAME, KEY_ORIGEN, KEY_LAT_ORIG,
                KEY_LNG_ORIG, KEY_FECHA_SALIDA, KEY_DESTINO, KEY_LAT_DEST, KEY_LNG_DEST, KEY_FECHA_LLEGADA,
                KEY_TIPO, KEY_FECHA_LIMITE, KEY_PRECIO, KEY_PERIOD, KEY_PROGRAM}, null,
                null, null, null, null);
    }

    public Cursor fetchALLCARS() {

        return mDB.query(DB_CARS, new String[] {KEY_C_ID_PK, KEY_C_NAME, KEY_MODELO, KEY_PLAZAS,
                KEY_COLOR, KEY_SIZE, KEY_CONSUMO, KEY_ANTIG}, null, null,
                null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId_R id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */



    public Cursor fetchRIDES(long rowId_R) throws SQLException {

        Cursor mCursor =

                mDB.query(true, DB_RIDES, new String[] {KEY_R_ID_PK, KEY_R_NAME, KEY_ORIGEN, KEY_LAT_ORIG,
                                KEY_LNG_ORIG, KEY_FECHA_SALIDA, KEY_DESTINO, KEY_LAT_DEST, KEY_LNG_DEST, KEY_FECHA_LLEGADA,
                                KEY_TIPO, KEY_FECHA_LIMITE, KEY_PRECIO, KEY_PERIOD, KEY_PROGRAM},
                        KEY_R_ID_PK + "=" + rowId_R, null,
                        null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor fetchCARS(long rowId_C) throws SQLException {

        Cursor mCursor =

                mDB.query(true, DB_RIDES, new String[] {KEY_C_ID_PK, KEY_C_NAME, KEY_MODELO,
                                KEY_PLAZAS, KEY_COLOR, KEY_SIZE, KEY_CONSUMO, KEY_ANTIG},
                        KEY_C_ID_PK + "=" + rowId_C, null,
                        null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     *
     * @param rowId_R o rowId_C id of note to update
     * @param //title value to set note title to
     * @param //body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */

    public boolean updateRIDES(long rowId_R, String ID_R, String NAME_R, String ORIGEN,
                               String LAT_ORIGEN, String LNG_ORIGEN, String FECHA_SALIDA, String DESTINO,
                               String LAT_DESTINO, String LNG_DESTINO, String FECHA_LLEGADA,
                               String TIPO, String FECHA_LIMITE, String PRECIO, String PERIODICIDAD,
                               String PROGRAMACION) {

        ContentValues args = new ContentValues();
        args.put(KEY_R_ID_PK, ID_R);
        args.put(KEY_R_NAME, NAME_R);
        args.put(KEY_ORIGEN, ORIGEN);
        args.put(KEY_LAT_ORIG, LAT_ORIGEN);
        args.put(KEY_LNG_ORIG, LNG_ORIGEN);
        args.put(KEY_FECHA_SALIDA, FECHA_SALIDA);
        args.put(KEY_DESTINO, DESTINO);
        args.put(KEY_LAT_DEST, LAT_DESTINO);
        args.put(KEY_LNG_DEST, LNG_DESTINO);
        args.put(KEY_FECHA_LLEGADA, FECHA_LLEGADA);
        args.put(KEY_TIPO, TIPO);
        args.put(KEY_FECHA_LIMITE, FECHA_LIMITE);
        args.put(KEY_PRECIO, PRECIO);
        args.put(KEY_PERIOD, PERIODICIDAD);
        args.put(KEY_PROGRAM, PROGRAMACION);

        return mDB.update(DB_RIDES, args, KEY_R_ID_PK + "=" + rowId_R, null) > 0;
    }

    public boolean updateCARS(long rowId_C, String NAME_C, String MODELO, String PLAZAS, String COLOR, String SIZE,
                              String CONSUMO, String ANTIG) {

        ContentValues args = new ContentValues();
        args.put(KEY_C_NAME, NAME_C);
        args.put(KEY_MODELO, MODELO);
        args.put(KEY_PLAZAS, PLAZAS);
        args.put(KEY_COLOR, COLOR);
        args.put(KEY_SIZE, SIZE);
        args.put(KEY_CONSUMO, CONSUMO);
        args.put(KEY_ANTIG, ANTIG);

        return mDB.update(DB_CARS, args, KEY_C_ID_PK + "=" + rowId_C, null) > 0;
    }
}