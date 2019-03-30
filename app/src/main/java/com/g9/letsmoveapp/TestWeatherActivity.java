package com.g9.letsmoveapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class TestWeatherActivity extends AppCompatActivity {

    TextView tv_1, tv_2, tv_3;
    final String AEMET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAzNDYzMjNAYWx1bW5vcy51YzNtL" +
            "mVzIiwianRpIjoiN2VmNjBlNGMtMzk3ZS00NDJkLWIzZjQtODY0ZDcxODk5NzIyIiwiaXNzIjoiQUVN" +
            "RVQiLCJpYXQiOjE1NTE3Nzg2MjIsInVzZXJJZCI6IjdlZjYwZTRjLTM5N2UtNDQyZC1iM2Y0LTg2NGQ" +
            "3MTg5OTcyMiIsInJvbGUiOiIifQ.jWl4VQIzvLxsrWWVO5Dy2-h-70Mw_zbdpeMvYnPwFg8\n";
    public static final String LOG_TAG = "TestWeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weather);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);

        new AemetWeather().execute();


    }

    public class AemetWeather extends AsyncTask<Object, Void, Object> {

        @Override
        protected String doInBackground(Object[] objects) {
            String CP = "28109"; // Codigo postal de Pelayos de la Presa
            String stringURL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" +
                    CP + "/?api_key=" + AEMET_KEY;

            tv_1.setText(stringURL);
            Log.d(LOG_TAG, stringURL);
            String json= getJSONResponse(stringURL);
            Log.d(LOG_TAG, json);
            //Leer campo datos del json, coger url y repetir el proceso
            JsonReader jsonReader;


            return null;
        }

        public String getJSONResponse(String stringURL){
            URL url = null;
            InputStream is = null;
            JSONObject jObj = null;
            String json = "";

            try {
                url = new URL(stringURL);
            } catch (Exception ex) {
                System.out.println("Malformed URL");
            }

            try {
                if (url != null) {
                    //Abrimos conexión HTTPS, con método GET y nos conectamos
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    //Sacamos el código de respuesta
                    int responseCode = urlConnection.getResponseCode();
                    Log.d(LOG_TAG, "The response is: " + responseCode);

                    //Leemos el input stream
                    is = new BufferedInputStream(urlConnection.getInputStream());
                    //Convertimos inputStream a String
                    json = streamToString(is);
                }
            } catch (IOException ioe) {
                System.out.println("IOException");
            }
            return json;
        }
        //Convierte un InputStream a un String
        public String streamToString(InputStream is) {
            //Para hacer un buffer para
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();//

            String responseStr = null;
            try {
                //Mientras que la línea que se ha leído no sea nula, sigue leyendo
                while ((responseStr = buffReader.readLine()) != null) {
                    sb.append(responseStr + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //Cierra el stream
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
      //DESARROLLAR METODO PARA LEER CAMPOS DE JSON
        //PRIMERO PARA SACAR DEL CAMPO DATOS LA URL Y LUEGO PARA LOS CAMPOS ÚTILES DE LA PETICION AEMET
      /* *********************************
        public String readDataJson(InputStream is, JsonReader jsonReader){
            try {
                jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    // Busca la cadena "results"
                    if (name.equals("descripcion")) {
                        // comienza un array de objetos
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            String descr="";
                            jsonReader.beginObject();
                            // comienza un objeto
                            while (jsonReader.hasNext()) {
                                name = jsonReader.nextName();
                                if (name.equals("name")) {
                                    // si clave "name" guarda el valor
                                    poi.setName(jsonReader.nextString());
                                    System.out.println("PLACE NAME:" + poi.getName());
                                } else if (name.equals("geometry")) {
                                    // Si clave "geometry" empieza un objeto
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        name = jsonReader.nextName();
                                        if (name.equals("location")) {
                                            // dentro de "geometry", si clave "location" empieza un objeto
                                            jsonReader.beginObject();
                                            while (jsonReader.hasNext()) {
                                                name = jsonReader.nextName();
                                                // se queda con los valores de "lat" y "long" de ese objeto
                                                if (name.equals("lat")) {
                                                    poi.setLatitude(jsonReader.nextString());
                                                    System.out.println("PLACE LATITUDE:" + poi.getLatitude());
                                                } else if (name.equals("lng")) {
                                                    poi.setLongitude(jsonReader.nextString());
                                                    System.out.println("PLACE LONGITUDE:" + poi.getLongitude());
                                                } else {
                                                    jsonReader.skipValue();
                                                }
                                            }
                                            jsonReader.endObject();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }
                                    jsonReader.endObject();
                                } else{
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                            temp.add(poi);
                        }
                        jsonReader.endArray();
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
            }
            catch (Exception e) {
            System.out.println("Exception");
            return new ArrayList<GooglePlace>();
        }
        ******************************** */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}
