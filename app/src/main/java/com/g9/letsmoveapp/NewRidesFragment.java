package com.g9.letsmoveapp;

import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


//Imports referenciados a las bases de datos
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.g9.letsmoveapp.DatabaseAdapter;
import com.g9.letsmoveapp.ConexionDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NewRidesFragment extends AppCompatActivity {
    private static final String LOG_TAG = "LEER_RIDE";
    private static final String TAG = NewRidesFragment.class.getSimpleName();
    private static final int PERMISSION_CODE = 1000;

    EditText ride_name;
    EditText ride_origen;
    TextView ride_fechaSalida;
    TextView ride_horaSalida;
    EditText ride_destino;
    TextView ride_fechaLlegada;
    TextView ride_horaLlegada;
    EditText ride_tipo;
    EditText ride_horaLimite;
    EditText ride_precio;
    EditText ride_period;
    EditText ride_num_viaj;
    Button button_read_car;

    Button button_horasalida;
    Button button_horallegada;
    Button button_fechasalida;
    Button button_fechallegada;

    ImageButton button_maps_origen;
    ImageButton button_maps_destino;

    String ride_lat_origen = "";
    String ride_lat_destino = "";
    String ride_lng_origen = "";
    String ride_lng_destino = "";



    //--------------------Spinner coches variables-----------------

    ArrayList<CarsDataModel> carsDataModelArrayList;
    String ride_coche = "";



    //-------------------------------------------------------------

    //Intents Extra de Maps
    public static final String EXTRA_MAPS =
            "com.g9.letsmoveapp.MapsActivity.extra.MAPS";

    public static final int REQUEST_ORIGEN = 1;
    public static final int REQUEST_DESTINO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newrides);

        ride_name = (EditText) findViewById(R.id.name_ride);
        ride_origen = (EditText) findViewById(R.id.origen);
        ride_fechaSalida = (TextView) findViewById(R.id.fechaSalida);
        ride_horaSalida = (TextView) findViewById(R.id.horaSalida);
        ride_destino = (EditText) findViewById(R.id.destino);
        ride_fechaLlegada = (TextView) findViewById(R.id.fechaLlegada);
        ride_horaLlegada = (TextView) findViewById(R.id.horaLlegada);
        ride_tipo = (EditText) findViewById(R.id.tipo_ride);
        ride_horaLimite = (EditText) findViewById(R.id.hora_limite);
        ride_precio = (EditText) findViewById(R.id.precio);
        ride_period = (EditText) findViewById(R.id.period);
        ride_num_viaj = (EditText) findViewById(R.id.num_viaj);
        button_read_car = (Button) findViewById(R.id.button_read_rides);

        button_horallegada = (Button) findViewById(R.id.button_horallegada);
        button_horasalida = findViewById(R.id.button_horasalida);
        button_fechasalida = findViewById(R.id.button_fechasalida);
        button_fechallegada = findViewById(R.id.button_fechallegada);

        button_maps_origen = (ImageButton) findViewById(R.id.button_maps_origen);
        button_maps_destino = (ImageButton) findViewById(R.id.button_maps_destino);


        //----------------------Código para el spinner de los coches----------------------
/* Mío
        //ArrayList<String> spinnerArray = new ArrayList<>();
        ArrayList auxArrayList = getCars();
        ArrayList spinnerArray = new ArrayList();

        //En el tutorial crea un ArrayList listaPersonas y un ArrayList Usuarios, su usuarios es nuestro
        //coche y su listaPersonas es nuestro carsArrayList

*/
//--------------------Spinner

        //ArrayList<CarsDataModel> carsModelArrayList = getCars();

        int i = 0;
        String carModel;

        Spinner spinner = findViewById(R.id.spinnerCoches);
        //ArrayAdapter<CharSequence> adapter;
        final ArrayAdapter<String> adapter;

        carsDataModelArrayList = getCars();

        // Implementar metodo  getCars() que devuelva ArrayList con los nombres de los coches de la BBDD
        // De momento solo inserta elementos a pelo sin sacarlos de la base de datos

        List<String> spinnerArray = new ArrayList<>();

        while (carsDataModelArrayList.size() > i){
            carModel = (String) carsDataModelArrayList.get(i).getModel();
            //String modelo=carsModel.getModel();
            spinnerArray.add(carModel);

            Log.d(LOG_TAG, "coche añadido: " + carModel);
            i++;
        }
        //*****


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * Al seleccionar un elemento del spinner ->
             * */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    ride_coche = item.toString();
                    //TODO: aqui guardar el nombre del coche seleccionado
                    //Toast.makeText(NewRidesFragment.this, item.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//-----------------------------------------------------------------------------------------------------------

        /*
        int i = 0;
        CarsDataModel carsModel;

        while (auxArrayList.size() > i){
            carsModel = (CarsDataModel) auxArrayList.get(i);
            String modelo=carsModel.getModel();
            auxArrayList.add(modelo);

            Log.d(LOG_TAG, "coche añadido: " + modelo);
            i++;
        }*/
       // precio.setText(aux);

        //---------------------------------------------------------------------------------

/*

        //ArrayAdapter<CharSequence> adapter;
        final ArrayAdapter<String> adapter;

       // List<String> spinnerArray = new ArrayList<>();
        // Implementar metodo  getCars() que devuelva ArrayList con los nombres de los coches de la BBDD
        // De momento solo inserta elementos a pelo sin sacarlos de la base de datos
        spinnerArray = getCars();
        //
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, auxArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            */
/*
             * Al seleccionar un elemento del spinner ->
             * *//*

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    ride_coche = item.toString();
                    //TODO: aqui guardar el nombre del coche seleccionado
                    //Toast.makeText(NewRidesFragment.this, item.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
*/


        // Click listener para mostrar DatePicker de fecha salida
        button_fechasalida.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().commit();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.datePickerType = 1;//ponemos este campo distinto de 0 para lanzar el código de fecha salida
                datePickerFragment.show(fragmentManager, "date picker");
            }
        });

        // Click listener para mostrar DatePicker de fecha llegada
        button_fechallegada.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().commit();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.datePickerType = 0;//ponemos este campo a 0 para lanzar el código de fecha llegada
                datePickerFragment.show(fragmentManager, "date picker");
            }
        });

        // Click Listener para mostrar el TimePicker
        button_horasalida.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().commit();
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.timePickerType = 1;//ponemos este campo distinto de 0 para lanzar el código de hora salida
                timePicker.show(fragmentManager, "time picker");
            }
        });

        // CLick Listener para mostrar el TimePicker
        button_horallegada.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().commit();
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.timePickerType = 0;//ponemos este campo a 0 para lanzar el código de hora llegada
                timePicker.show(fragmentManager, "time picker");
            }
        });

        /**
         Click Listener para lanzar actividad MapsActivity y selseccionar ORIGEN
         */
        button_maps_origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_origen = ride_origen.getText().toString();
                Intent intent = new Intent(NewRidesFragment.this, MapsActivity.class);
                intent.putExtra(EXTRA_MAPS, msg_origen);
                startActivityForResult(intent, REQUEST_ORIGEN);
            }
        });

        /**
         Click Listener para lanzar actividad MapsActivity y seleccionar DESTINO
         */
        button_maps_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_destino = ride_destino.getText().toString();
                Intent intent = new Intent(NewRidesFragment.this, MapsActivity.class);
                intent.putExtra(EXTRA_MAPS, msg_destino);
                startActivityForResult(intent, REQUEST_DESTINO);
            }
        });
        /*//BOTON DE PRUEBA PARA VER SI SE LEEN LOS REGISTROS
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
        });*/
    }

    public void add_ride(View view) {
        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_RIDES, null, 1);
        SQLiteDatabase db_rides = conn.getWritableDatabase();
        Log.d(LOG_TAG, "TABLE " + db_rides.toString());

        //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

        String insert = "INSERT INTO " + DatabaseAdapter.DB_RIDES
                + " (" +
                DatabaseAdapter.KEY_R_NAME + "," +
                DatabaseAdapter.KEY_ORIGEN + "," +
                DatabaseAdapter.KEY_LAT_ORIG + "," +
                DatabaseAdapter.KEY_LNG_ORIG + "," +
                DatabaseAdapter.KEY_FECHA_SALIDA + "," +
                DatabaseAdapter.KEY_HORA_SALIDA + "," +
                DatabaseAdapter.KEY_DESTINO + "," +
                DatabaseAdapter.KEY_LAT_DEST + "," +
                DatabaseAdapter.KEY_LNG_DEST + "," +
                DatabaseAdapter.KEY_FECHA_LLEGADA + "," +
                DatabaseAdapter.KEY_HORA_LLEGADA + "," +
                DatabaseAdapter.KEY_TIPO + "," +
                DatabaseAdapter.KEY_FECHA_LIMITE + "," +
                DatabaseAdapter.KEY_PRECIO + "," +
                DatabaseAdapter.KEY_PERIOD + "," +
                DatabaseAdapter.KEY_NUM_VIAJ + ")" +
                " VALUES('" +
                ride_name.getText().toString() + "','" +
                ride_origen.getText().toString() + "','" +
                ride_lat_origen + "','" +
                ride_lng_origen + "','" +
                ride_fechaSalida.getText().toString() + "','" +
                ride_horaSalida.getText().toString() + "','" +
                ride_destino.getText().toString() + "','" +
                ride_lat_destino + "','" +
                ride_lng_destino + "','" +
                ride_fechaLlegada.getText().toString() + "','" +
                ride_horaLlegada.getText().toString() + "','" +
                ride_tipo.getText().toString() + "','" +
                ride_horaLimite.getText().toString() + "','" +
                ride_precio.getText().toString() + "','" +
                ride_period.getText().toString() + "','" +
                ride_num_viaj.getText().toString() + "')";
        //Hay algunos con comillas simples porque son textos y en sql se usan comilla simple, necesito ponerlas si no parseo números
        // modelo-texto, plazas-int, color-texto
        Log.d(LOG_TAG, "InsertSQL " + insert);

        db_rides.execSQL(insert);
        String message = "Viaje: " + ride_name.getText().toString() + " creado correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        db_rides.close();


    }
    /**
     * Extraer nombres de coches de la BBDD de coches
     */
    public ArrayList<CarsDataModel> getCars() {


        //TODO: aqui hay que añadir los nombres de los coches extraidos de la BBDD de coches al ArrayList

        ConexionDatabase conn = new ConexionDatabase(this, DatabaseAdapter.DB_CARS, null, 1);
        //abres conexion bbdd
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
                //Solamente nos interesa setear el modelo porque lo demás es prescindible para el spinner
                //No dará error porque en carsDataModel hay un objeto creado completo por defecto
                carsDataModel.setModel(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_MODELO)));
                Log.d(LOG_TAG, "Model: " + carsDataModel.getModel());
                //añadimos el modelo de datos a la lista
                carsModelArrayList.add(carsDataModel);

            }
        }


        cursor.close();//cerramos el cursor, dejamos de leer la tabla
        db_cars.close();//cerramos conexion
//----
        return carsModelArrayList;





        //finish();//cerramos la activity

        //return spinnerArray;

        //---------------------------SPINNER--------------------------------

        //ArrayList<String> spinnerArray = new ArrayList<>();
/*
        spinnerArray.add("coche 1");
        spinnerArray.add("coche 2");
        spinnerArray.add("coche 3");
*/




    }
    //--------------------------Para spinner--------------------------------------


    /**
     * Controla los resultados que devuelven los intents de maps
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundleReply;
        //Address address = new Address(Locale.getDefault());
        String direccion, municipio, calle, numero, provincia, pais;
        Double lat, lng;

        if (resultCode == RESULT_OK) {
            //Settear los campos necesarios que se guardaran en bbdd
            bundleReply = data.getBundleExtra(MapsActivity.EXTRA_MAP_REPLY);
            municipio = (String) bundleReply.get("municipio");
            calle = (String) bundleReply.get("calle");
            direccion = (String) bundleReply.get("direccion");
            numero = (String) bundleReply.get("numero");
            provincia = (String) bundleReply.get("provincia");
            pais = (String) bundleReply.get("pais");

            Log.d(TAG, "Bundle reply ORIGEN: " + "; " + municipio + "; " + calle + "; " + numero + "; " + provincia + "; " + pais);
            Log.d(TAG, "Bundle reply ORIGEN: Direccion completa" + direccion);

            lat = (Double) bundleReply.get("latitud");
            lng = (Double) bundleReply.get("longitud");

            switch (requestCode) {
                // Guardar resultados para ORIGEN
                case REQUEST_ORIGEN:
                    if (municipio != null && numero != null && calle != null) {
                        String stringText = municipio + ", " + calle + " " + numero;
                        ride_origen.setText(stringText);
                    }
                    if (lat != null && lng != null) {
                        ride_lat_origen = Double.toString(lat);
                        ride_lng_origen = Double.toString(lng);
                    }
                    break;

                // Guarar resultados para DESTINO
                case REQUEST_DESTINO:
                    if (municipio != null && numero != null && calle != null) {
                        String stringText = municipio + ", " + calle + " " + numero;
                        ride_destino.setText(stringText);
                    }
                    if (lat != null && lng != null) {
                        ride_lat_destino = Double.toString(lat);
                        ride_lng_destino = Double.toString(lng);
                    }
                    break;
            }
        }
    }
}