package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class NuevoCoche extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_coche);
    }

    public void add_car(View view) {
        EditText editText = (EditText) findViewById(R.id.nombre_nuevo_coche);
        String message = "Coche: " + editText.getText().toString() + " añadido correctamente";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        finish();

        // Cuando acaba una actividad vuelve a la actividad padre. No hace falta hacer un intent

        //Intent intent = new Intent(this, MisCoches.class);
        //startActivity(intent);
    }
}
