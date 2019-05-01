package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MiPerfil extends AppCompatActivity {
    EditText campoUsuario, campoExperiencia, campoTelefono;
    TextView txtUsuario, txtExperiencia, txtTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicia_perfil);

        campoUsuario = (EditText) findViewById(R.id.nombre_entrada);
        campoExperiencia = (EditText) findViewById(R.id.experiencia_entrada);
        campoTelefono = (EditText) findViewById(R.id.telefono_entrada);

        cargarPreferencias();
        Button guardar_pref = (Button) findViewById(R.id.btnGuardar);

        guardar_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPreferencias();
            }
        });

    }
    /*
public void onClick(View view){

    Button guardar_pref = (Button) view.findViewById(R.id.btnGuardar);

    guardar_pref.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            guardarPreferencias();
        }
    });
*/
    /*
        switch(view.getId()){
            case R.id.btnGuardar:
                guardarPreferencias();
                break;

            default:
                cargarPreferencias();
                break;

        }

}
*/


private void cargarPreferencias(){
SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
String usuario = preferences.getString("usuario", "Pepe");
String experiencia = preferences.getString("experiencia", "5");
String telefono = preferences.getString("telefono", "666666666");

campoUsuario.setText(usuario);
campoExperiencia.setText(experiencia);
campoTelefono.setText(telefono);

}


private void guardarPreferencias(){

    SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

    String usuario=campoUsuario.getText().toString();
    String experiencia = campoExperiencia.getText().toString();
    String telefono = campoTelefono.getText().toString();

    SharedPreferences.Editor editor=preferences.edit();
    editor.putString("usuario",usuario);
    editor.putString("experiencia",experiencia);
    editor.putString("telefono",telefono);

    campoUsuario.setText(usuario);
    campoExperiencia.setText(experiencia);
    campoTelefono.setText(telefono);

    editor.commit();
    cargarPreferencias();

}

}
