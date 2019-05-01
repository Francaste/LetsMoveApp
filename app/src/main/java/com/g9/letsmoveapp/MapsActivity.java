package com.g9.letsmoveapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public static final String EXTRA_MAP_REPLY = "com.g9.letsmoveapp.mapsactivity.extra.REPLY";

    private GoogleMap mMap;
    private boolean isReady = false;//Para checkear que el mapa esta listo
    private final String TAG = MapsActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION = 123;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private Marker clickMarker = null;

    private String msg_extra;


    private EditText editText_buscar;
    private ImageButton button_buscar;
    private Button button_hecho;

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

        // Capturamos el intent que lanzó esta actividad y el mensaje
        Intent intent = getIntent();
        msg_extra = intent.getStringExtra(NewRidesFragment.EXTRA_MAPS);


        //Provider de localizacion
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        // Checkeamos permisos
        checkPermission();

        editText_buscar = findViewById(R.id.editText_buscar);
        button_buscar = findViewById(R.id.button_buscar);
        button_hecho = findViewById(R.id.button_hecho);

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
                //Peticion para actualizar la localizavion cada 5 minutos
                locationManager.requestLocationUpdates(provider, 1000 * 60 * 5, 25, this);
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    String text = "Coordenadas: LAT:" + location.getLatitude() + ", LONG: " + location.getLongitude();
                    //Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Coordenadas: " + text);
                } else {
                    Log.e(TAG, "Error location null...");
                }
            } else {
                Log.e(TAG, "Error obteniendo localizaión...");
            }
        }
    }

    /**
     * Al pausar la actividad Maps se desactivan las actualizaciones de localizacion
     */
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
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        isReady = true;
        mMap = googleMap;

        // Colocar marcadores
        marcadores(googleMap);

        // Al pulsar sobre el mapa
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Projection proj = mMap.getProjection();
                Point coord = proj.toScreenLocation(latLng);
                String toast = "Click\n" + "Lat: " + latLng.latitude + "\n" + "Lng: " + latLng.longitude + "\n" + "X: " + coord.x + " - Y: " + coord.y;
                Toast.makeText(MapsActivity.this, toast, Toast.LENGTH_SHORT).show();
                Log.d(TAG, toast);

            }
        });

        // Al hacer una pulsación larga sobre el mapa se actualiza el marcador click
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marcadorClick(mMap, latLng);
                Address address = getAddress(latLng);
                if (address != null) {
                    editText_buscar.setText(address.getAddressLine(0));
                }
            }
        });

        //CLick listener del boton lupa
        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_buscar = editText_buscar.getText().toString();
                LatLng buscador_location = searchExtraLocation(text_buscar);
                if (buscador_location != null) marcadorClick(mMap, buscador_location);
            }
        });

        //Click listener del boton HECHO
        button_hecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float lat, lng;
                // Si el clickMarker existe
                if (clickMarker != null) {
                    Address address = getAddress(clickMarker.getPosition());
                    if (address != null)
                        replyLocation(address);
                }
            }
        });

        //
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }

    /**
     * Actuliza el marcador clickMarker al hacer una pulsacion sobre el mapa
     */
    public void marcadorClick(GoogleMap googleMap, LatLng latLng) {
        mMap = googleMap;
        if (clickMarker != null) {
            clickMarker.remove();
            clickMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            clickMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    /**
     * En este método añadimos los marcadores FIJOS que deseemos en el mapa y la localización
     */
    public void marcadores(GoogleMap googleMap) {
        mMap = googleMap;
        // Añade un marcador y mueve la cámara
        // Checkea permisos y pone el punto azul con la localización
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            location = locationManager.getLastKnownLocation(provider);
        }

        if (!msg_extra.equals("")) {
            LatLng extraMarker = searchExtraLocation(msg_extra);
            mMap.addMarker(new MarkerOptions().position(extraMarker).title(msg_extra));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(extraMarker, 17));
        } else if (location != null) {
            LatLng latLng_location = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng_location, 17));
        } else {
            // Si no se consigue la localizacion aolicitada no la localizacion del dispositivo
            // UC3M Campus de Leganés LAT 40.332008867438645 LONG -3.765928643655343
            final LatLng leganes = new LatLng(40.332008, -3.765928);
            mMap.addMarker(new MarkerOptions().position(leganes).title("UC3M Campus Leganés"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(leganes, 17));
        }

    }

    /**
     * Verifica si los permisos estan concedidos. Si no lo estan los solicita
     * <p>
     * <p>
     * Fuente: https://stackoverflow.com/questions/40142331/how-to-request-location-permission-at-runtime
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
                                android.Manifest.permission.ACCESS_FINE_LOCATION,},
                        REQUEST_LOCATION);
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
                        locationManager.requestLocationUpdates(provider, 1000 * 60 * 5, 25, this);
                        //minTime en milisegundos
                        // minDistance en metros
                        location = locationManager.getLastKnownLocation(provider);

                        //Volvemos a llamar a onMapReady para refrescar y habilitar a visualización de la localización
                        //Verificar si ya se ha cargado el mapa
                        if (isReady) {
                            onMapReady(mMap);
                        }

                    } else {
                        Log.e(TAG, "Error obteniendo localizaión...");
                    }
                }


            } else {
                Log.e(TAG, "No hay permisos para obtener localización");
                //Acciones necesarias si aplica
            }
        }
    }

    /**
     * Obtiene coordenadas del lugra solicitado al buscar origen o destino
     */
    public LatLng searchExtraLocation(String site) {
        List<Address> addresses = null;
        if (Geocoder.isPresent()) {
            Geocoder gc = new Geocoder(this);
            try {
                addresses = gc.getFromLocationName(site, 2);
                Address address = addresses.get(0);
                // Devuelve un LatLng
                return new LatLng(address.getLatitude(), address.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //En caso de no conseguir las coordenadas devuelve null
        return null;
    }

    /**
     * Obtencion de la dirección a partir de las coordenadas
     */
    public Address getAddress(LatLng latLng) {
        List<Address> addresses = null;
        // chekea si el dispositivo tiene geocoder
        if (Geocoder.isPresent()) {
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                // Obtencion de la direccion
                addresses = gc.getFromLocation(latLng.latitude, latLng.longitude, 10);
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0);
                Log.d(TAG, "GEOCODING: Address:" + addressLine);
                Toast.makeText(MapsActivity.this, addressLine, Toast.LENGTH_SHORT).show();
                // Devuelve address
                return address;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error de geocoding");
            }

        }
        // si no se consigue obtener la direccion devuelve null
        return null;
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

    /**
     * Al pulsar el boton HECHO se ejecuta este metodo
     * Devuevuelve A NewRides la direccion completa y las coordenadas
     * Crea un intent de respuesta para NewRides y finaliza la actividad de mapas
     */
    public void replyLocation(Address address) {
        // TODO: devolver el nombre del municipio en el intent y las coordenadas, y la direccion completa

        Bundle bundle = new Bundle();
        if (address != null ) {
            //Bundle de respuesta
            bundle.putString("municipio", address.getLocality());
            bundle.putString("direccion", address.getAddressLine(0));
            bundle.putDouble("latitud", address.getLatitude());
            bundle.putDouble("longitud", address.getLongitude());
            bundle.putString("provincia", address.getSubAdminArea());//Provincia
            bundle.putString("pais", address.getCountryName());//Pais
            bundle.putString("calle", address.getThoroughfare());//Calle
            bundle.putString("numero", address.getSubThoroughfare());//Numero


        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(MapsActivity.EXTRA_MAP_REPLY, bundle);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
