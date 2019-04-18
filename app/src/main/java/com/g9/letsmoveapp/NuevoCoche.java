package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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


public class NuevoCoche extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button button_camera;
    ImageView fotoCar;
    private final static String CLAVE_RUTA_IMAGEN = "CLAVE_RUTA_IMAGEN";
    public static final String LOG_TAG = "nuevoCoche";

    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


    public void onCreate(Bundle savedInstanceState) {
        //Te deja controlar la actividad general "haciendo como que entras", coge la clase superior
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);

        button_camera = findViewById(R.id.cam_car);
        fotoCar=findViewById(R.id.foto_car);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(takePictureIntent);
                dispatchTakePictureIntent(takePictureIntent);
            }
        });
    }
/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);
    }
*/

    public void add_car(View view) {
        EditText editText = (EditText) findViewById(R.id.nombre_nuevo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();

        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

        //Intent intent = new Intent(this, MisCoches.class);
        //startActivity(intent);
    }

/*en codelabs era private
  Este es el método que estaba ayer cuando la cámara funcionaba sin problemas
    public void intentCamara(View view) {
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/

    //Este es el método que estaba ayer cuando la cámara funcionaba sin problemas pero no se usaba
    // de por sí en ningún sitio y ahora con la nueva, es el código que da problemas
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
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
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.g9.letsmoveapp",
                        photoFile);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(photoIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}
