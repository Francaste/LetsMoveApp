package com.g9.letsmoveapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

//import com.g9.letsmoveapp.DatabaseAdapter.ConexionDatabase;
import com.g9.letsmoveapp.NuevoCoche;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuFragment()).commit();

        //Triplecito para el context del adaptador

        //Creamos base de datos
/*
        try {
            DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
            dbAdapter.mDBHelper.onCreate(dbAdapter.mDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        //Creamos la conexiÃ³n de acuerdo a la Ãºltima versiÃ³n que vamos a llevar a cabo
        //ConexionDatabase conn =new ConexionDatabase(this,"bd_usuarios",null,1);

    }

    //Triple unido al a anterior lÃ­nea del context del adaptador


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            String message = "Esta aplicación es propiedad de Álex Trigueros, Fran Castellano, Sara Escribano";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();


        if (id == R.id.nav_inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuFragment()).commit();
        } else if (id == R.id.nav_perfil) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ProfileFragment()).commit();
        } else if (id == R.id.nav_trayecto) {
            //Dentro del fragment se llama a la actividad NewRidesFragments(antes era fragment, ahora es una activity)
            fragmentManager.beginTransaction().replace(R.id.contenedor, new TransitionNewRides()).commit();
        }
        else if (id == R.id.nav_viajes) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MyRidesFragment()).commit();
        }

        else if (id == R.id.nav_notificacion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new NotificationFragment()).commit();
        } else if (id == R.id.nav_puntuacion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new PuntuaFragment()).commit();
        } else if (id == R.id.nav_configuracion) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new SettingsFragment()).commit();
        } else if (id == R.id.nav_compartir) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ShareFragment()).commit();
        } else if (id == R.id.nav_faq) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new FaqFragment()).commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void misPerfil(View view) {
        Intent intent = new Intent(this, MiPerfil.class);
        startActivity(intent);
    }

    public void misCoches(View view) {
        Intent intent = new Intent(this, MisCoches.class);
        startActivity(intent);
    }
    public void actuales(View view) {
        Intent intent = new Intent(this, ViajesActuales.class);
        startActivity(intent);
    }

    public void anteriores(View view) {
        Intent intent = new Intent(this, ViajesAnteriores.class);
        startActivity(intent);
    }

    public void gastos(View view) {
        Intent intent = new Intent(this, GastosViajes.class);
        startActivity(intent);
    }
    // Este metodo lanza una actividad para testear la API AEMET
// al pulsar el boton TEST WEATHER que esta en Mis Viajes
    public void testWeather(View view) {
        Intent intent = new Intent(this, TestWeatherActivity.class);
        startActivity(intent);
    }

}
