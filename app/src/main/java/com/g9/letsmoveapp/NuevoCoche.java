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

    //Variables para el coche
    EditText car_nombre,car_plazas,car_size,car_antig,car_color,car_consumo;


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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);

        car_nombre= (EditText) findViewById(R.id.nombre_nuevo_coche);
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


    //Añadimos el coche
    public void add_car(View view) {
        EditText editText = (EditText) findViewById(R.id.nombre_nuevo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();

        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

    }
}
/*

*/
/*
/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);
    }
*//*


    public void add_car(View view) {
        EditText editText = (EditText) findViewById(R.id.nombre_nuevo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        //Cascar aquí el adaptador datos del coche addCarsData
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();

        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

        //Intent intent = new Intent(this, MisCoches.class);
        //startActivity(intent);
    }

*/
/*en codelabs era private
  Este es el método que estaba ayer cuando la cámara funcionaba sin problemas
    public void intentCamara(View view) {
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*//*


    //Este es el método que estaba ayer cuando la cámara funcionaba sin problemas pero no se usaba
    // de por sí en ningún sitio y ahora con la nueva, es el código que da problemas
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(LOG_TAG, "Data: "+data.toString());
            // Continue only if the File was successfully created
            Uri photoUri = data.getData();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fotoCar.setImageBitmap(imageBitmap);
        }
    }


    //Guardar la foto en la galería o donde sea, hemos importao File, IOException, SimpleDateFormat
    //Y Environment, que se ha importado más arriba con los otros import (URI y FileProvider
    // también están arriba)


//Esto es nuevo
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  */
/* prefix *//*

                ".jpg",         */
/* suffix *//*

                storageDir      */
/* directory *//*

        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//Nuevo
    static final int REQUEST_TAKE_PHOTO = 1;

    //Una vez que tenemos un método para crear ficheros, lanzamos la cámara

    //Nuevo
    private void dispatchTakePictureIntent(Intent takePictureIntent) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI =null;
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("Exception");
                ex.printStackTrace();
            }
           // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI =
                        FileProvider.getUriForFile(this,
                        "com.g9.letsmoveapp",
                        photoFile);
                //photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(photoIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}
*/
