package com.g9.letsmoveapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {


    private GoogleMap mMap;
    private final String TAG = "MapsActivity";
    private static final int REQUEST_LOCATION = 123;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private boolean permission;

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        permission = checkPermission();



    }

    @Override
    protected void onResume() {
        super.onResume();
        // Si se han concedido los permisos obtenetmos la localización
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            provider = locationManager.getBestProvider(new Criteria(), false);

            //Solicitar localizacion
            if (provider != null) {
                //location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 400, 5, this);
                location = locationManager.getLastKnownLocation(provider);
                onMapReady(mMap);
            } else {
                Log.e(TAG, "Error obteniendo localizaión...");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcadores(googleMap);
    }

    /**
     * En este método añadimos los marcadores que deseemos en el mapa
     */
    public void marcadores(GoogleMap googleMap) {
        mMap = googleMap;
        // UC3M Campus de Leganés
        // LAT 40.332008867438645
        // LONG -3.765928643655343
        // Añade un marcador y mueve la cámara
        final LatLng leganes = new LatLng(40.332008, -3.765928);
        mMap.addMarker(new MarkerOptions().position(leganes).title("UC3M Campus Leganés"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leganes, 17));

        // Checkea permisos y pone el punto azul con la localización
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
    }

    /**
     * Verifica si los permisos estan concedidos. Si no lo estan los solicita
     */
    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //...Mostrar algun mensaje explicando porqué necesitamos permisos de localizacion
                Log.d(TAG, "Explanation: se requiere acceso a la ubicacion");
            } else {
                // Solicitar permisos de localización
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                        }, REQUEST_LOCATION);
            }
            return false;
        } else {
            Log.d(TAG, "Permisos de localización disponibles, obtniendo localización...");
            return true;
        }
    }


    /**
     * Este método se ejecuta al responder a la solicitud de permisos de localización
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Permisos de localización obtenidos, obteniendo localización...");
                //Acciones necesarias si aplica


                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    provider = locationManager.getBestProvider(new Criteria(), false);

                    //Solicitar localizacion
                    if (provider != null) {
                        locationManager.requestLocationUpdates(provider, 400, 5, this);
                        location = locationManager.getLastKnownLocation(provider);
                    } else {
                        Log.e(TAG, "Error obteniendo localizaión...");
                    }
                }


            } else {
                System.out.println("No hay permisos para obtener localización");
                //Acciones necesarias si aplica
            }
            return;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
