package com.g9.letsmoveapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

//Imports referenciados a las bases de datos
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.g9.letsmoveapp.DatabaseAdapter;
import com.g9.letsmoveapp.ConexionDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class NewRidesFragment extends AppCompatActivity {
    private static final String LOG_TAG = "LEER_RIDE";
    private static final int PERMISSION_CODE = 1000;

    EditText ride_name;
    EditText ride_origen;
    TextView ride_horaSalida;
    EditText ride_destino;
    TextView ride_horaLlegada;
    EditText ride_tipo;
    EditText ride_horaLimite;
    EditText ride_precio;
    EditText ride_period;
    EditText ride_num_viaj;
    Button button_read_car;

    ArrayList ridesTable;

    //Mientras no tenemos lat y lng destino y origen ponemos esto
    String ride_lat_origen = "";
    String ride_lat_destino = "";
    String ride_lng_origen = "";
    String ride_lng_destino = "";


    public static final String EXTRA_MESSAGE_MAP =
            "com.g9.letsmoveapp.MapsActivity.extra.MESSAGE_MAP";
    public static final int MAP_REQUEST = 1;

    //TODO Esto está ya apañao commo para que sea una activity, lo que da por culo ahora mismo aquí es el onClick
    //Que va a seguir dando por culo hasta que el timepickerfragment sea llamado como dios manda
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newrides);

        ride_name = (EditText) findViewById(R.id.name_ride);
        ride_origen = (EditText) findViewById(R.id.origen);
        ride_horaSalida = (TextView) findViewById(R.id.horaSalida);
        ride_destino = (EditText) findViewById(R.id.destino);
        ride_horaLlegada = (TextView) findViewById(R.id.horaLlegada);
        ride_tipo = (EditText) findViewById(R.id.tipo_ride);
        ride_horaLimite = (EditText) findViewById(R.id.hora_limite);
        ride_precio = (EditText) findViewById(R.id.precio);
        ride_period = (EditText) findViewById(R.id.period);
        ride_num_viaj = (EditText) findViewById(R.id.num_viaj);
        button_read_car = (Button) findViewById(R.id.button_read_rides);

        // CLick Listener para mostrar el TIiePicker
        Button button_horasalida = findViewById(R.id.button_horasalida);
        button_horasalida.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().commit();

                DialogFragment timePicker = new TimePickerFragment();
                int hour = 0;
                int minute = 0;
                // timePicker.onTimeSet(TimePicker view, hour, minute);
                timePicker.show(fragmentManager, "time picker");

            }
        });

        // CLick Listener para mostrar el TIiePicker
        Button button_horallegada = (Button) findViewById(R.id.button_horallegada);
        button_horallegada.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(fragmentManager, "time picker");
            }
        });

        // Click Listener para lanzar actividad MapsActivity y selsecionar ORIGEN
        ImageButton button_maps_origen = (ImageButton) findViewById(R.id.button_maps_origen);
        button_maps_origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_origen = ride_origen.getText().toString();
                Intent intent = new Intent(NewRidesFragment.this, MapsActivity.class);
                intent.putExtra(EXTRA_MESSAGE_MAP, msg_origen);
                startActivityForResult(intent, MAP_REQUEST);
            }
        });

        // Click Listener para lanzar actividad MapsActivity y seleCcionar DESTINO
        ImageButton button_maps_destino = (ImageButton) findViewById(R.id.button_maps_destino);
        button_maps_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_destino = ride_destino.getText().toString();
                Intent intent = new Intent(NewRidesFragment.this, MapsActivity.class);
                intent.putExtra(EXTRA_MESSAGE_MAP, msg_destino);
                startActivity(intent);
            }
        });
        //BOTON DE PRUEBA PARA VER SI SE LEEN LOS REGISTROS
        button_read_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ver permisos
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//check version
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //dar permiso
                        String[] permission = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission OK
                        ridesTable = getRides("");
                        //TODO Hay que desarrollar el método getRides para poder hacer lecturas de todos
                        // los registros y del registro con el título concreto
                        Log.d(LOG_TAG, "SIZE ARRAY: " + ridesTable.size());
                        RidesDataModel ridesDataModel;
                        int i = 0;
                        while (ridesTable.size() > i) {
                            ridesDataModel = (RidesDataModel) ridesTable.get(i);
                            Log.d(LOG_TAG, "Ride1: " + "Name: " + ridesDataModel.getR_name() + "Origen: " + ridesDataModel.getOrigen() + " -  Destino: "
                                    + ridesDataModel.getDestino() + " -  FechaSalida: " + ridesDataModel.getFecha_salida() + " - ");
                            i++;
                        }
                    }
                } else {
                    ridesTable = getRides("");
                    Log.d(LOG_TAG, "SIZE ARRAY: " + ridesTable.size());
                    RidesDataModel ridesDataModel;
                    int i = 0;
                    while (ridesTable.size() > i) {
                        ridesDataModel = (RidesDataModel) ridesTable.get(i);
                        Log.d(LOG_TAG, "Ride1: " + "Name: " + ridesDataModel.getR_name() + "Origen: " + ridesDataModel.getOrigen() + " -  Destino: "
                                + ridesDataModel.getDestino() + " -  FechaSalida: " + ridesDataModel.getFecha_salida() + " - ");
                        i++;
                    }
                }

            }
        });
    }
   /*
    KEY_LAT_ORIG + " real not null, " +
    KEY_LNG_ORIG + " real not null, " +
    KEY_FECHA_SALIDA + " text not null, " +
    KEY_LAT_DEST + " real not null, " +
    KEY_LNG_DEST + " real not null, " +
    KEY_FECHA_LLEGADA + " text not null, " +
  */

    public void add_ride(View view) {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_RIDES, null, 1);
        SQLiteDatabase db_rides = conn.getWritableDatabase();

        //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

        String insert = "INSERT INTO " + DatabaseAdapter.DB_RIDES
                + " ( " +
                DatabaseAdapter.KEY_R_NAME + ", " +
                DatabaseAdapter.KEY_ORIGEN + ", " +
                DatabaseAdapter.KEY_LAT_ORIG + ", " +
                DatabaseAdapter.KEY_LNG_ORIG + ", " +
                DatabaseAdapter.KEY_FECHA_SALIDA + ", " +
                DatabaseAdapter.KEY_DESTINO + ", " +
                DatabaseAdapter.KEY_LAT_DEST + ", " +
                DatabaseAdapter.KEY_LNG_DEST + ", " +
                DatabaseAdapter.KEY_FECHA_LLEGADA + ", " +
                DatabaseAdapter.KEY_TIPO + ", " +
                DatabaseAdapter.KEY_FECHA_LIMITE + ", " +
                DatabaseAdapter.KEY_PRECIO + ", " +
                DatabaseAdapter.KEY_PERIOD + ", " +
                DatabaseAdapter.KEY_NUM_VIAJ + ")" +
                " VALUES ('" +
                ride_name.getText().toString() + "','" +
                ride_origen.getText().toString() + "','" +
                ride_lat_origen + "','" +
                ride_lng_origen + "','" +
                ride_horaSalida.getText().toString() + "','" +
                ride_destino.getText().toString() + "','" +
                ride_lat_destino + "','" +
                ride_lng_destino + "','" +
                ride_horaLlegada.getText().toString() + "','" +
                ride_tipo.getText().toString() + "','" +
                ride_horaLimite.getText().toString() + "','" +
                ride_precio.getText().toString() + "','" +
                ride_period.getText().toString() + "','" +
                ride_num_viaj.getText().toString() + "')";
        //Hay algunos con comillas simples porque son textos y en sql se usan comilla simple, necesito ponerlas si no parseo números
        // modelo-texto, plazas-int, color-texto


        // car_modelo,car_plazas,car_size,car_color,car_antig,car_consumo;


        db_rides.execSQL(insert);
        String message = "Viaje: " + ride_name.getText().toString() + " creado correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        db_rides.close();
    }

//este método recibe parámetro un string, que sacamos del text view título de la tarjeta de viajes actuales.

    public ArrayList getRides(String r_nombre) {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_RIDES, null, 1);
        //abres conexion bbdd
        SQLiteDatabase db_rides = conn.getReadableDatabase();//para leer

//TODO: podemos utilizar distintos métodos para la lectura de nuestra base de datos. ¿Cómo?
//TODO Muy fácil, pasamos como parámetro la sentencia sql de lectura que queremos aplicar.
//TODO Por ejemplo, elegir todo o elegir siguiendo alguna regla de los campos de bbdd

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
                ridesDataModel.setFecha_salida(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DESTINO)));
                Log.d(LOG_TAG, "FechaSalida: " + ridesDataModel.getFecha_salida());
                ridesDataModel.setDestino(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DESTINO)));
                Log.d(LOG_TAG, "Destino: " + ridesDataModel.getDestino());
                ridesDataModel.setLat_dest(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_LAT_DEST)));
                Log.d(LOG_TAG, "LatDest: " + ridesDataModel.getLat_dest());
                ridesDataModel.setLng_dest(cursor.getDouble(cursor.getColumnIndex(DatabaseAdapter.KEY_LNG_DEST)));
                Log.d(LOG_TAG, "LngDest: " + ridesDataModel.getLng_dest());
                ridesDataModel.setFecha_llegada(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_FECHA_LLEGADA)));
                Log.d(LOG_TAG, "FechaLlegada: " + ridesDataModel.getFecha_llegada());
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
        finish();//cerramos la activity
        return ridesModelArrayList;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST){
            if (resultCode == RESULT_OK){
                String reply = data.getStringExtra(MapsActivity.EXTRA_MAP_REPLY);
                Log.d(LOG_TAG, reply);
                //Toast.makeText(this,  reply, Toast.LENGTH_SHORT).show();
                Address address = new Address(Locale.getDefault());
                address.setAddressLine(0, reply);

                Log.d(LOG_TAG, "Maps Reply: "+ address.getAddressLine(0));
                //TODO: crear 2 cogidos de respuesta para diferenciar respuesta origen y destino y settear los editText
            }
        }
    }
}