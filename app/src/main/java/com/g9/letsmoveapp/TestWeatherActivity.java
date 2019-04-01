package com.g9.letsmoveapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;



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

    public static final String LOG_TAG = "TestWeatherActivity";
    final String AEMET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAzNDYzMjNAYWx1bW5vcy51YzNtL" +
            "mVzIiwianRpIjoiN2VmNjBlNGMtMzk3ZS00NDJkLWIzZjQtODY0ZDcxODk5NzIyIiwiaXNzIjoiQUVN" +
            "RVQiLCJpYXQiOjE1NTE3Nzg2MjIsInVzZXJJZCI6IjdlZjYwZTRjLTM5N2UtNDQyZC1iM2Y0LTg2NGQ" +
            "3MTg5OTcyMiIsInJvbGUiOiIifQ.jWl4VQIzvLxsrWWVO5Dy2-h-70Mw_zbdpeMvYnPwFg8\n";
    TextView tv_1, tv_2, tv_3;

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
            //desarrollar metodo para buscar código del pueblo dado el nombre del pueblo en el editText de google maps buscan do en base de datos id nombre cp-pueblo
            String CP = "28109"; // Codigo pueblo de Pelayos de la Presa
            String stringURL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" +
                    CP + "/?api_key=" + AEMET_KEY;

            tv_1.setText(stringURL);
            Log.d(LOG_TAG, stringURL);
            //Cogemos la respuesta de la primera consulta a aemet
            String dataUrl = getJSONResponse(stringURL);

            Log.d(LOG_TAG, "URL is: " + dataUrl);


            //conseguimos respuesta https con los datos de aemet a partir de url datos


            return null;
        }

        public String getJSONResponse(String stringURL) {
            URL url = null;
            InputStream is = null;
            JSONObject jObj = null;
            JsonReader jsonReader = null;
            String dataUrl="";
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
                    //Ponemos un json reader
                    streamToString(is);
                   // jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));

                    //Leer campo datos del json, coger url y repetir el proceso
                    dataUrl=readAemetJson(new JsonReader(new InputStreamReader(is, "UTF-8")));
                    Log.d(LOG_TAG, dataUrl);
                }
            } catch (IOException ioe) {
                System.out.println("IOException");
            }
            return dataUrl;
        }
        //DESARROLLAR METODO PARA LEER CAMPOS DE JSON
        //PRIMERO PARA SACAR DEL CAMPO DATOS LA URL Y LUEGO PARA LOS CAMPOS ÚTILES DE LA PETICION AEMET

        //Convierte un InputStream a un String
        public void streamToString(InputStream is) {
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
                    Log.d(LOG_TAG, sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String readAemetJson(JsonReader jsonReader) {
            int responseCode = 0;
            String dataURL = "";
            try {
                jsonReader.beginObject();
                //Se inicia un objeto por cada llave
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    // Busca la cadena "results"
                    if (name.equals("estado")) {
                        // si clave "name" guarda el valor
                        responseCode = jsonReader.nextInt();
                        Log.d(LOG_TAG, "Response Code: " + responseCode);
                    } else if (name.equals("datos")) {
                        // si clave "name" guarda el valor
                        dataURL = jsonReader.nextString();
                        Log.d(LOG_TAG, "Url: " + dataURL);
                    } else {
                        jsonReader.skipValue();
                    }
                }

                jsonReader.endObject();
                return dataURL;
            } catch (Exception e) {
                System.out.println("Exception");
                return "";
            }
        }
        /* *************
        public String readWeatherDataJson(JsonReader jsonReader){

                try {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String name = jsonReader.nextName();
                        // Busca la cadena "results"
                        if (name.equals("estado")) {
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
                ********************** */
            @Override
            protected void onPreExecute () {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute (Object o){
                super.onPostExecute(o);
            }
        }

    }
