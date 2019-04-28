package com.g9.letsmoveapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class NewRidesFragment extends AppCompatActivity {

    EditText ride_name;
    EditText ride_origen;
    TextView ride_horaSalida;
    EditText ride_destino;
    TextView ride_horaLlegada;
    EditText ride_tipo;
    EditText ride_horaLimite;
    EditText ride_precio;
    EditText ride_period;
    EditText ride_program;

    //Mientras no tenemos lat y lng destino y origen ponemos esto
    String ride_lat_origen = "";
    String ride_lat_destino = "";
    String ride_lng_origen = "";
    String ride_lng_destino = "";


    public static final String EXTRA_MESSAGE_MAP =
            "com.g9.letsmoveapp.MapsActivity.extra.MESSAGE_MAP";

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
        ride_program = (EditText) findViewById(R.id.programacion);


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
                startActivity(intent);
            }
        });

        // Click Listener para lanzar actividad MapsActivity y selsecionar DESTINO
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
        ConexionDatabase conn = new ConexionDatabase(this, "db_rides", null, 1);
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
                DatabaseAdapter.KEY_PROGRAM + ")" +
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
                ride_program.getText().toString() + "')";
        //Hay algunos con comillas simples porque son textos y en sql se usan comilla simple, necesito ponerlas si no parseo números
        // modelo-texto, plazas-int, color-texto


        // car_modelo,car_plazas,car_size,car_color,car_antig,car_consumo;


        db_rides.execSQL(insert);
        String message = "Viaje: " + ride_name.getText().toString() + " creado correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        db_rides.close();



/*
        EditText editText = (EditText) findViewById(R.id.modelo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        *//*



        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

    }

*/


    }
}