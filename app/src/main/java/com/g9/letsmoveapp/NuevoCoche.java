package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Imports para la base de datos
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import com.g9.letsmoveapp.DatabaseAdapter;
import com.g9.letsmoveapp.ConexionDatabase;


public class NuevoCoche extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE =1001 ;
    Button button_camera;
    ImageView fotoCar;
    private final static String CLAVE_RUTA_IMAGEN = "CLAVE_RUTA_IMAGEN";
    public static final String LOG_TAG = "nuevoCoche";
    Uri photoUri;//esta uri la guardamos en la base de datos en el registro de cada coche
    String portaURI="";

    //Variables para el coche
    EditText car_modelo,car_plazas,car_size,car_color,car_antig,car_consumo;
    Button button_new_car;

    public void onCreate(Bundle savedInstanceState) {
        //Parte correspondiente a la cámara de esta clase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);

        button_camera = findViewById(R.id.cam_car);
        fotoCar = findViewById(R.id.foto_car);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ver permisos
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//check version
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //dar permiso
                        String[] permission = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission OK
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });

    //Parte corresondiente a añadir coches

        car_modelo= (EditText) findViewById(R.id.modelo_coche);
        car_plazas= (EditText) findViewById(R.id.num_plazas);
        car_size= (EditText) findViewById(R.id.size);
        car_antig= (EditText) findViewById(R.id.antiguedad);
        car_color= (EditText) findViewById(R.id.color);
        car_consumo= (EditText) findViewById(R.id.consumo);

    }

    private void openCamera() {
        ContentValues values=new ContentValues();//creamos contenedor
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ltsmvapp_"+ "_" + timeStamp ;//creamos un nombre para las fotos
        values.put(MediaStore.Images.Media.TITLE,imageFileName);//añadimos nombre
        values.put(MediaStore.Images.Media.DESCRIPTION,"Let's MoveApp");//añadimos descripción
        photoUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);//insertamos en la uri externa titulo y descri

        portaURI=photoUri.toString();

        //Intent de la cámara
        Intent photoIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//crear intent
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);//poner extras
        startActivityForResult(photoIntent, IMAGE_CAPTURE_CODE);//empezamos intent para obtener resultado
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){//si se recibe result OK
            Log.d(LOG_TAG, "photoUri: "+photoUri.toString());

            fotoCar.setImageURI(photoUri);//mostramos en la ImageView la imagen guardada en la uri
        }
    }

    @Override
    //para los permisos
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
switch(requestCode){
    case PERMISSION_CODE:{
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            openCamera();
        }else{
            Toast.makeText(this,"No hay permiso para abrir cámara",Toast.LENGTH_LONG).show();
        }
    }
}
    }

/*
    //Aquí está lo relacionado con coches en esta actividad
    public void add_car_Sql() {
        ConexionDatabase conn=new ConexionDatabase(this,"db_cars",null,1);
        SQLiteDatabase db_cars=conn.getWritableDatabase();

        //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

        String insert="INSERT INTO "+DatabaseAdapter.DB_CARS
                +" ( " +DatabaseAdapter.KEY_MODELO+","+DatabaseAdapter.KEY_PLAZAS+"," +
                ""+DatabaseAdapter.KEY_SIZE+","+DatabaseAdapter.KEY_COLOR+","+DatabaseAdapter.KEY_ANTIG+","+DatabaseAdapter.KEY_CONSUMO+")" +
                " VALUES ('"+car_modelo.getText().toString()+"', "+car_plazas.getText().toString()+",'"+car_color.getText().toString()+
                "','"+car_size.getText().toString()+"','"+car_consumo.getText().toString()+"','"+car_antig.getText().toString()+"')";
        //Hay algunos con comillas simples porque son textos, necesito ponerlas si no parseo números
        // modelo-texto, plazas-int, color-texto


        db_cars.execSQL(insert);
        db_cars.close();
    }
*/
    //Al método add_car lo está llamando el onClick del xml, no hace falta poner escuchador
    public void add_car(View view) {
        ConexionDatabase conn=new ConexionDatabase(this,"db_cars",null,1);
        SQLiteDatabase db_cars=conn.getWritableDatabase();

        //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

        String insert="INSERT INTO "+DatabaseAdapter.DB_CARS
                +" ( " +
                DatabaseAdapter.KEY_MODELO+", "+
                DatabaseAdapter.KEY_PLAZAS+", " +
                DatabaseAdapter.KEY_SIZE+", "+
                DatabaseAdapter.KEY_COLOR+", "+
                DatabaseAdapter.KEY_ANTIG+", "+
                DatabaseAdapter.KEY_URI+", "+
                DatabaseAdapter.KEY_CONSUMO+")" +
                " VALUES ('"+
                car_modelo.getText().toString()+"','"+
                car_plazas.getText().toString()+"','"+
                car_size.getText().toString()+ "','"+
                car_color.getText().toString()+"','"+
                car_antig.getText().toString()+"','"+
                portaURI+"','"+
                car_consumo.getText().toString()+"')";
        //Hay algunos con comillas simples porque son textos y en sql se usan comilla simple, necesito ponerlas si no parseo números
        // modelo-texto, plazas-int, color-texto


        // car_modelo,car_plazas,car_size,car_color,car_antig,car_consumo;


        db_cars.execSQL(insert);
        String message = "Coche: " + car_modelo.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        db_cars.close();

       /*
        EditText editText = (EditText) findViewById(R.id.modelo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
        */


        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

    }
}