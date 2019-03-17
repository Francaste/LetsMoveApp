package com.g9.letsmoveapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Codelabs
    private final ArrayList<VisualViajes> listaViajes = new ArrayList<>();
    private RecyclerView recyclerViajes;
    private adaptadorDatos miAdapter;


    //Array de myRides
   /* ArrayList<VisualViajes> listaViajes;
    RecyclerView recyclerViajes;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuFragment()).commit();
    }
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
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            FragmentManager fragmentManager = getSupportFragmentManager();


            if (id == R.id.nav_inicio) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new MenuFragment()).commit();
            } else if (id == R.id.nav_perfil) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new ProfileFragment()).commit();
            } else if (id == R.id.nav_trayecto) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new NewRidesFragment()).commit();
            } else if (id == R.id.nav_viajes) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new MyRidesFragment()).commit();
            } else if (id == R.id.nav_notificacion) {

            } else if (id == R.id.nav_puntuacion) {

            } else if (id == R.id.nav_configuracion) {

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        public void actuales (View view){
            Intent intent = new Intent(this, ViajesActuales.class);
            startActivity(intent);
        }

        public void anteriores (View view){
            Intent intent = new Intent(this, ViajesAnteriores.class);
            startActivity(intent);
        }
        public void gastos (View view){
            Intent intent = new Intent(this, GastosViajes.class);
            startActivity(intent);
        }

        public void nuevoCoche (View view){
            Intent intent = new Intent(this, NuevoCoche.class);
            startActivity(intent);
         }

        public void misCoches (View view){
            Intent intent = new Intent(this, MisCoches.class);
            startActivity(intent);
        }

    }
