package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class NuevoCoche extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button button_camera;
    ImageView fotoCar;


    public void onCreate(Bundle savedInstanceState) {
        //Te deja controlar la actividad general "haciendo como que entras", coge la clase superior
        super.onCreate(savedInstanceState); setContentView(R.layout.nuevo_coche);
        button_camera = findViewById(R.id.cam_car);
        fotoCar=findViewById(R.id.foto_car);
        //------------BOTON: MI PERFIL----------------
        //Aquí se establece el escuchador OnCLick para el boton
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCamara(view);
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
    /*
    public void onClick(View view) {
        intentCamara();

    }
    */
// en codelabs era private
    public void intentCamara(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoCar.setImageBitmap(imageBitmap);
        }
    }
}
