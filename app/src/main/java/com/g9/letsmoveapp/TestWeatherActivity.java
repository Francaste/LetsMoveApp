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

            InputStream inAemetURL, inData = null;


            //desarrollar metodo para buscar código del pueblo dado el nombre del pueblo en el editText de google maps buscan do en base de datos id nombre cp-pueblo
            String CP = "28109"; // Codigo pueblo de Pelayos de la Presa
            String stringURL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" +
                    CP + "/?api_key=" + AEMET_KEY;

            tv_1.setText(stringURL);
            Log.d(LOG_TAG, stringURL);
            //Cogemos la respuesta de la primera consulta a aemet
            inAemetURL = getJSONResponse(stringURL);

            String dataUrl = readAemetJson(inAemetURL);

            inData = getJSONResponse(dataUrl);

            //readWeatherDataJson(inData);

            //conseguimos respuesta https con los datos de aemet a partir de url datos


            return null;
        }


        public InputStream getJSONResponse(String stringURL) {
            URL url = null;
            String dataUrl = "";
            InputStream in = null;
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
                    //Leemos el InputStream
                    in = urlConnection.getInputStream();
                    /* ***************************
                    Antes haciamos esto...y no funcionaba
                    is = new BufferedInputStream(urlConnection.getInputStream());
                    streamToString(is);
                    Pero el jsonReader se tiene que crear con un InputStream, no con un BuferedInputStream
                    ******************************* */
                    // TODO: Cambiar streamToString para que lea el nuevo  tipo de dato InputStream
                    //streamToString(in);
                    //Leer campo datos del json, coger url y repetir el proceso


                    return in;


                    //////////////////////////////////////////
                    // COMENZAMOS SEGUNA CONEXION HTTPS para conseguir los datos del tiempo
                    //////////////////////////////////////////


                }
            } catch (IOException ioe) {
                System.out.println("IOException");
            }

            Log.d(LOG_TAG, "FIN getJSONResponse, dataURL: " + dataUrl);
            // Si hay algun error devuelve dataURL = "";
            return in;
        }
        //DESARROLLAR METODO PARA LEER CAMPOS DE JSON
        //PRIMERO PARA SACAR DEL CAMPO DATOS LA URL Y LUEGO PARA LOS CAMPOS ÚTILES DE LA PETICION AEMET

        //Convierte un InputStream a un String
        private void streamToString(InputStream in) {
            //Para hacer un buffer para
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));
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
                    in.close();
                    Log.d(LOG_TAG, sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String readAemetJson(InputStream in) {

            //COMIENZO DE LECTURA DEL PRIMER JSON PARA OBTENER URL DE DATOS DE TIEMPO
            int responseCodeJSON = 0;
            String dataURL = "";
            JsonReader reader = null;


            try {
                reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                reader.beginObject();
                //Se inicia un objeto por cada llave
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    // Busca la cadena "results"
                    Log.d(LOG_TAG, "JSON NAME: " + name);
                    switch (name) {
                        case "descripcion":
                            String descripcion = reader.nextString();
                            Log.d(LOG_TAG, "JSON Descripcion: " + descripcion);
                            break;
                        case "estado":
                            // si clave "name" guarda el valor
                            responseCodeJSON = reader.nextInt();
                            if (responseCodeJSON != 200) {
                                // TODO: Si codigo de respuesta no OK...
                                Log.d(LOG_TAG, "JSON Estado: " + responseCodeJSON);
                                return null;
                            }
                            break;
                        case "datos":
                            // si clave "name" guarda el valor
                            dataURL = reader.nextString();
                            Log.d(LOG_TAG, "URL DATOS: " + dataURL);
                            return dataURL;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                return dataURL;
            } catch (Exception e) {
                System.out.println("Exception");
                e.printStackTrace();
                return null;
            }
        }

        /*
        * Recibe el inputstream que contiene el json con los datos del tiempo
        * Lee el json y guarda los datos de interes en BBDD
        *
        * */
        public String readWeatherDataJson(InputStream in) {

            int responseCodeJSON = 0;
            String dataURL = "";
            JsonReader jsonReader = null;

            try {
                jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    // Busca la cadena "results"
                    if (name.equals("estado")) {
                        // comienza un array de objetos
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            String descr = "";
                            jsonReader.beginObject();
                            // comienza un objeto
                            while (jsonReader.hasNext()) {
                                name = jsonReader.nextName();
                                if (name.equals("name")) {
                                    // si clave "name" guarda el valor

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
                                                   // poi.setLatitude(jsonReader.nextString());
                                                    //System.out.println("PLACE LATITUDE:" + poi.getLatitude());
                                                } else if (name.equals("lng")) {
                                                    //poi.setLongitude(jsonReader.nextString());
                                                    //System.out.println("PLACE LONGITUDE:" + poi.getLongitude());
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
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    } else {
                        jsonReader.skipValue();
                    }
                }
                jsonReader.endObject();
            } catch (
                    Exception e) {
                System.out.println("Exception");
                return null;
            }
            return null;
        }

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
