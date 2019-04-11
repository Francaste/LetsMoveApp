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


            // TODO: desarrollar metodo para buscar código del pueblo dado el nombre del pueblo en el editText de google maps buscan do en base de datos id nombre cp-pueblo
            String CP = "28109"; // Codigo pueblo de Pelayos de la Presa
            String stringURL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" +
                    CP + "/?api_key=" + AEMET_KEY;

            tv_1.setText(stringURL);
            Log.d(LOG_TAG, stringURL);
            //Cogemos la respuesta de la primera consulta a aemet
            inAemetURL = getJSONResponse(stringURL);

            String dataUrl = readAemetJson(inAemetURL);

            inData = getJSONResponse(dataUrl);

            readWeatherDataJson(inData);

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
                System.out.println("Malformed URL");//PONER ERROR STACK TRACE
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
                    Log.d(LOG_TAG, "1-JSON NAME: " + name);
                    switch (name) {
                        case "descripcion":
                            String descripcion = reader.nextString();
                            Log.d(LOG_TAG, "1-JSON Descripcion: " + descripcion);
                            break;
                        case "estado":
                            // si clave "name" guarda el valor
                            responseCodeJSON = reader.nextInt();
                            if (responseCodeJSON != 200) {
                                // TODO: Si codigo de respuesta no OK...
                                Log.d(LOG_TAG, "1-JSON Estado: " + responseCodeJSON);
                                return null;
                            }
                            break;
                        case "datos":
                            // si clave "name" guarda el valor
                            dataURL = reader.nextString();
                            Log.d(LOG_TAG, "1-URL DATOS: " + dataURL);
                            return dataURL;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                return dataURL; //prueba
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
                jsonReader.beginArray(); //añadido

                if(jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        //Esto es un "mientras haya más elementos"
                        String name = jsonReader.nextName();

                        // Busca la cadena "results"
                        Log.d(LOG_TAG, "2-JSON NAME: " + name);

                        switch (name) {

                            case "origen":
                                jsonReader.beginObject();
                                while(jsonReader.hasNext()){
                                    jsonReader.skipValue();
                                }
                                jsonReader.endObject();
                                break;
                            case "nombre":
                                String nombre = jsonReader.nextString();
                                break;
                            case "provincia":
                                String provincia = jsonReader.nextString();
                                break;
                            case "prediccion":

                                jsonReader.beginObject();

                                String name_dia = jsonReader.nextName();
                                // Para debuggear, vamos viendo qué estamos creando
                                Log.d(LOG_TAG, "2-JSON NAME: " + name_dia);

                                if (name_dia.equals("dia")) {

                                    jsonReader.beginArray();
                                    jsonReader.beginObject();


                                    while (jsonReader.hasNext()) {
                                        //propiedades del dia

                                        //Una vez dentro del array dia, tenemos muchas propiedades del día
                                        //que serán objetos diferentes

                                        String prop_dia = jsonReader.nextName();

                                        Log.d(LOG_TAG, "2-JSON NAME: " + prop_dia);

                                        switch (prop_dia) {

                                            case "probPrecipitacion":
                                                jsonReader.beginArray();

                                                jsonReader.beginObject();
                                                try {

                                                    String props = jsonReader.nextName();
                                                    switch (props) {
                                                        case "value":
                                                            String value_probPrep = jsonReader.nextName();
                                                            break;
                                                        case "periodo":
                                                            String periodo_probPrep = jsonReader.nextName();
                                                            break;

                                                    }
                                                    //cerramos el primer objeto (periodo 0-24h) del probPrecipitacion

                                                    while (jsonReader.hasNext()) {
                                                        //No se hace begin object ni end object porque no estamos
                                                        //entrando al objeto para hacerle skip
                                                        jsonReader.skipValue();

                                                    }

                                                } catch (Exception e) {
                                                    System.out.println("Exception");
                                                    e.printStackTrace();
                                                    return null;
                                                }
                                                jsonReader.endObject();
                                                jsonReader.endArray();
                                                break;

                                            case "CotaNieveProv":
                                                jsonReader.beginArray();
                                                jsonReader.beginObject();
                                                try {
                                                    String props = jsonReader.nextName();
                                                    switch (props) {
                                                        case "value":
                                                            String value_CotaNieve = jsonReader.nextName();
                                                            break;
                                                        case "periodo":
                                                            String periodo_CotaNieve = jsonReader.nextName();
                                                            break;

                                                    }
                                                    //cerramos el primer objeto (periodo 0-24h) del probPrecipitacion
                                                    jsonReader.endObject();

                                                    while (jsonReader.hasNext()) {
                                                        //No se hace begin object ni end object porque no estamos
                                                        //entrando al objeto para hacerle skip
                                                        jsonReader.skipValue();

                                                    }


                                                } catch (Exception e) {
                                                    System.out.println("Exception");
                                                    e.printStackTrace();
                                                    return null;
                                                }

                                                jsonReader.endArray();
                                                break;

                                            case "estadoCielo":
                                                jsonReader.beginArray();
                                                jsonReader.beginObject();
                                                try {
                                                    String props = jsonReader.nextName();
                                                    switch (props) {
                                                        case "value":
                                                            String value_estadoCielo = jsonReader.nextName();
                                                            break;
                                                        case "periodo":
                                                            String periodo_estadoCielo = jsonReader.nextName();
                                                            break;
                                                        case "descripcion":
                                                            String descrip_estadoCielo = jsonReader.nextName();
                                                            break;

                                                    }
                                                    //cerramos el primer objeto (periodo 0-24h) del probPrecipitacion
                                                    jsonReader.endObject();

                                                    while (jsonReader.hasNext()) {
                                                        //No se hace begin object ni end object porque no estamos
                                                        //entrando al objeto para hacerle skip
                                                        jsonReader.skipValue();

                                                    }

                                                } catch (Exception e) {
                                                    System.out.println("Exception");
                                                    e.printStackTrace();
                                                    return null;
                                                }

                                                jsonReader.endArray();
                                                break;

                                            case "temperatura":
                                                jsonReader.beginObject();
                                                try {
                                                    String props = jsonReader.nextName();
                                                    switch (props) {
                                                        case "maxima":
                                                            String maxima_temp = jsonReader.nextName();
                                                            break;
                                                        case "minima":
                                                            String minima_temp = jsonReader.nextName();
                                                            break;
                                                        default:
                                                            jsonReader.skipValue();
                                                            Log.d(LOG_TAG, "Obviamos lo demás y ya");
                                                            break;

                                                    }
                                                    //Podemos obviar este while porque acabamos de leer con "dato"
                                                    jsonReader.endObject();

                                                } catch (Exception e) {
                                                    System.out.println("Exception");
                                                    e.printStackTrace();
                                                    return null;
                                                }

                                                break;

                                            case "fecha":
                                                String fecha = jsonReader.nextString();
                                                break;

                                            default:
                                                jsonReader.skipValue();
                                                Log.d(LOG_TAG, "Obviamos lo demás");
                                                break;
                                        }
                                    }
                                    jsonReader.endObject();
                                }
                                break;

                            case "id":
                                String id = jsonReader.nextString();
                                break;

                            default:
                                jsonReader.skipValue();
                                Log.d(LOG_TAG, "Obviamos lo demás");
                                break;


                        }
                        //////


                    }
                    jsonReader.endObject();
                    jsonReader.endArray();
                }

            } catch (Exception e) {
                System.out.println("Exception: readWeatherDataJSON");
                e.printStackTrace();
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
