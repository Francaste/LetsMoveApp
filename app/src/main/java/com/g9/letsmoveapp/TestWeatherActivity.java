package com.g9.letsmoveapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class TestWeatherActivity extends AppCompatActivity {

    public static final String LOG_TAG = TestWeatherActivity.class.getSimpleName();
    final String AEMET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAzNDYzMjNAYWx1bW5vcy51YzNtL" +
            "mVzIiwianRpIjoiN2VmNjBlNGMtMzk3ZS00NDJkLWIzZjQtODY0ZDcxODk5NzIyIiwiaXNzIjoiQUVN" +
            "RVQiLCJpYXQiOjE1NTE3Nzg2MjIsInVzZXJJZCI6IjdlZjYwZTRjLTM5N2UtNDQyZC1iM2Y0LTg2NGQ" +
            "3MTg5OTcyMiIsInJvbGUiOiIifQ.jWl4VQIzvLxsrWWVO5Dy2-h-70Mw_zbdpeMvYnPwFg8\n";
    TextView tv_prevision;
    String previsionTiempo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weather);

        tv_prevision = findViewById(R.id.textView_prevision);

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
            Log.d(LOG_TAG, stringURL);

            //Cogemos la respuesta de la primera consulta a aemet
            inAemetURL = getJSONResponse(stringURL);

            String dataUrl = readAemetJson(inAemetURL);

            inData = getJSONResponse(dataUrl);

            previsionTiempo = readWeatherDataJson(inData);
            Log.d(LOG_TAG, "Weather data response: " + previsionTiempo);


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

            //Vamos recopilando los campos del json que nos interesan en un bundle
            Bundle bundle_respuestas = new Bundle();
            Bundle bundle_definitivo = new Bundle();
            try {
                jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                jsonReader.beginArray(); //añadido

                if (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        //Esto es un "mientras haya más elementos"
                        String name = jsonReader.nextName();

                        // Busca la cadena "results"
                        Log.d(LOG_TAG, "2-JSON NAME: " + name);

                        switch (name) {

                            case "origen":
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    jsonReader.skipValue();
                                }
                                jsonReader.endObject();
                                break;
                            case "nombre":
                                String nombre = jsonReader.nextString();
                                bundle_respuestas.putString("nombre", nombre);
                                break;
                            case "provincia":
                                String provincia = jsonReader.nextString();
                                bundle_respuestas.putString("provincia", provincia);
                                break;
                            case "prediccion":

                                jsonReader.beginObject();

                                String name_dia = jsonReader.nextName();
                                // Para debuggear, vamos viendo qué estamos creando
                                Log.d(LOG_TAG, "2-JSON NAME: " + name_dia);

                                if (name_dia.equals("dia")) {
                                    jsonReader.beginArray();

                                    while (jsonReader.hasNext()) {//tiene el array más elementos?

                                        jsonReader.beginObject();


                                        while (jsonReader.hasNext()) {//tiene el array más propiedades del dia?

                                            //Una vez dentro del array dia, tenemos muchas propiedades del día
                                            //que serán objetos diferentes

                                            String prop_dia = jsonReader.nextName();

                                            Log.d(LOG_TAG, "2-JSON NAME: " + prop_dia);

                                            switch (prop_dia) {

                                                case "probPrecipitacion":
                                                    jsonReader.beginArray();//abrimos el array de probPrecipitacion
                                                    jsonReader.beginObject();//abrimos el objeto del periodo
                                                    try {
                                                        int value_probPrep = 0;
                                                        String periodo_probPrep = "";
                                                        //comprobamos mientras haya propiedades en el objeto json dentro del array
                                                        while (jsonReader.hasNext()) {//necesitamos el while para asegurarnos que el objeto tiene datos que leer
                                                            String props = jsonReader.nextName();//cogemos el nombre de la propiedad siguiente
                                                            switch (props) {
                                                                case "value":
                                                                    value_probPrep = jsonReader.nextInt();//cogemos el valor de la propiedad con un tipo concreto
                                                                    bundle_respuestas.putInt("value_probPrep", value_probPrep);
                                                                    break;
                                                                case "periodo":
                                                                    periodo_probPrep = jsonReader.nextString();
                                                                    bundle_respuestas.putString("periodo_propPrep", periodo_probPrep);
                                                                    break;
                                                            }
                                                        }
                                                        //Cerramos el objeto una vez leido para pasar a los siguientes elementos del array
                                                        jsonReader.endObject();
                                                        //nos saltamos el resto de objetos
                                                        while (jsonReader.hasNext()) {
                                                            //No se hace begin object ni end object porque no estamos
                                                            //entrando al objeto para hacerle skip
                                                            jsonReader.skipValue();
                                                        }
                                                        //cerramos el array de la probPrecipitacion
                                                        jsonReader.endArray();
                                                        //En un futuro en vez de loggearlo, lo mostramos en la activity
                                                        Log.d(LOG_TAG, "RESULTADOS Precipitación-> Value:  " + value_probPrep + " ->Period:  " + periodo_probPrep);

                                                    } catch (Exception e) {
                                                        System.out.println("Exception");
                                                        e.printStackTrace();
                                                        //return null;
                                                    }
                                                    break;

                                                case "cotaNieveProv":
                                                    jsonReader.beginArray();//abrimos el array
                                                    jsonReader.beginObject();//abrimos el objeto
                                                    try {
                                                        int value_cotaNieve = 0;
                                                        String periodo_cotaNieve = "";
                                                        //comprobamos mientras haya propiedades en el objeto json dentro del array
                                                        while (jsonReader.hasNext()) {//necesitamos el while para asegurarnos que el objeto tiene datos que leer
                                                            String props = jsonReader.nextName();//cogemos el nombre de la propiedad siguiente
                                                            switch (props) {
                                                                case "value":

                                                                    try{
                                                                        value_cotaNieve = jsonReader.nextInt();
                                                                        bundle_respuestas.putInt("value_cotaNieve", value_cotaNieve);
                                                                    }catch (Exception e){
                                                                        e.printStackTrace();
                                                                        jsonReader.skipValue();
                                                                        break;
                                                                    }

                                                                    /*
                                                                    // con este if no sale la excepcion y sigue leyendo el codigo
                                                                    //hay que buscarle una solucion funcional
                                                                    if (jsonReader.nextString().equals("") || jsonReader.nextString().isEmpty()) {
                                                                        Log.d(LOG_TAG, "Cota Nieve no disponible");
                                                                    } else {
                                                                        value_cotaNieve = jsonReader.nextInt();
                                                                        bundle_respuestas.putInt("value_cotaNieve", value_cotaNieve);
                                                                    }
                                                                    */
                                                                    //cogemos el valor de la propiedad con un tipo concreto
                                                                    break;
                                                                case "periodo":
                                                                    try{
                                                                        periodo_cotaNieve = jsonReader.nextString();
                                                                        bundle_respuestas.putString("periodo_cotaNieve", periodo_cotaNieve);
                                                                    }catch (Exception e){
                                                                        e.printStackTrace();

                                                                        break;
                                                                    }

                                                                    break;
                                                            }
                                                        }
                                                        //Cerramos el objeto una vez leido para pasar a los siguientes elementos del array
                                                        jsonReader.endObject();
                                                        //nos saltamos el resto de objetos
                                                        while (jsonReader.hasNext()) {
                                                            //No se hace begin object ni end object porque no estamos
                                                            //entrando al objeto para hacerle skip
                                                            jsonReader.skipValue();
                                                        }
                                                        //cerramos el array de la probPrecipitacion
                                                        jsonReader.endArray();
                                                        //En un futuro en vez de loggearlo, lo mostramos en la activity
                                                        Log.d(LOG_TAG, "RESULTADOS Cota Nieve-> Value:  " + value_cotaNieve + " ->Period:  " + periodo_cotaNieve);

                                                    } catch (Exception e) {
                                                        System.out.println("Exception");
                                                        e.printStackTrace();
                                                        //return null;
                                                    }
                                                    break;
                                                case "estadoCielo":
                                                    jsonReader.beginArray();
                                                    jsonReader.beginObject();
                                                    try {
                                                        int value_estadoCielo = 0;
                                                        String periodo_estadoCielo = "";
                                                        String descrip_estadoCielo = "";
                                                        while (jsonReader.hasNext()) {
                                                            String props = jsonReader.nextName();
                                                            switch (props) {
                                                                case "value":

                                                                    try {
                                                                        value_estadoCielo = jsonReader.nextInt();
                                                                        bundle_respuestas.putInt("value_estadoCielo", value_estadoCielo);
                                                                    } catch (Exception e) {
                                                                        // SI no consigue leer propiedad value salta al siguiente
                                                                        Log.d(LOG_TAG, "Exception: Valor de estado cielo no disponible");
                                                                        value_estadoCielo = 0;
                                                                        jsonReader.skipValue();
                                                                        break;
                                                                    }

                                                                    // con este if no sale la excepcion y sigue leyendo el codigo
                                                                    //hay que buscarle una solucion funcional
                                                                    /*
                                                                    if (jsonReader.nextString().equals("") || jsonReader.nextString().isEmpty()) {
                                                                        Log.d(LOG_TAG, "Valor estado cielo no disponible");
                                                                    } else {
                                                                        value_estadoCielo = jsonReader.nextInt();
                                                                    }
                                                                    */
                                                                    break;
                                                                case "periodo":
                                                                    periodo_estadoCielo = jsonReader.nextString();
                                                                    bundle_respuestas.putString("periodo_estadoCielo", periodo_estadoCielo);
                                                                    break;
                                                                case "descripcion":
                                                                    try{
                                                                        descrip_estadoCielo = jsonReader.nextString();
                                                                        bundle_respuestas.putString("descrip_estadoCielo", descrip_estadoCielo);
                                                                    }catch (Exception e){
                                                                        Log.d(LOG_TAG, "Valor de la descripcion de estado cielo no disponible");
                                                                        //jsonReader.skipValue();
                                                                        break;

                                                                    }

                                                                    /*
                                                                    // con este if no sale la excepcion y sigue leyendo el codigo
                                                                    //hay que buscarle una solucion funcional
                                                                    if (jsonReader.nextString().equals("") || jsonReader.nextString().isEmpty()) {
                                                                        Log.d(LOG_TAG, "Descripcion estado cielo no disponible");
                                                                    } else {
                                                                        descrip_estadoCielo = jsonReader.nextString();
                                                                        bundle_respuestas.putString("descrip_estadoCielo", descrip_estadoCielo);
                                                                    }
                                                                    */
                                                                    break;
                                                            }
                                                        }
                                                        //cerramos el primer objeto (periodo 0-24h) del probPrecipitacion
                                                        jsonReader.endObject();

                                                        while (jsonReader.hasNext()) {
                                                            //No se hace begin object ni end object porque no estamos
                                                            //entrando al objeto para hacerle skip
                                                            jsonReader.skipValue();

                                                        }
                                                        jsonReader.endArray();
                                                        Log.d(LOG_TAG, "RESULTADOS Estado Cielo-> Value:  " + value_estadoCielo + " ->Period:  " + periodo_estadoCielo
                                                                + " ->Descripción:  " + descrip_estadoCielo);
                                                    } catch (Exception e) {
                                                        System.out.println("Exception: estado cielo");
                                                        e.printStackTrace();
                                                        //return null;
                                                    }
                                                    break;

                                                case "temperatura":
                                                    jsonReader.beginObject();
                                                    try {
                                                        int maxima_temp = 0;
                                                        int minima_temp = 0;
                                                        while (jsonReader.hasNext()) {
                                                            String props = jsonReader.nextName();
                                                            switch (props) {
                                                                case "maxima":
                                                                    maxima_temp = jsonReader.nextInt();
                                                                    bundle_respuestas.putInt("maxima_temp", maxima_temp);
                                                                    break;
                                                                case "minima":
                                                                    minima_temp = jsonReader.nextInt();
                                                                    bundle_respuestas.putInt("minima_temp", minima_temp);
                                                                    break;
                                                                default:
                                                                    jsonReader.skipValue();
                                                                    Log.d(LOG_TAG, "Obviamos lo demás y ya");
                                                                    break;

                                                            }
                                                        }
                                                        //Podemos obviar este while porque acabamos de leer con "dato"
                                                        jsonReader.endObject();
                                                        Log.d(LOG_TAG, "RESULTADOS Temperatura-> Max:  " + maxima_temp + " ->Min:  " + minima_temp);
                                                    } catch (Exception e) {
                                                        System.out.println("Exception");
                                                        e.printStackTrace();
                                                        //return null;
                                                    }
                                                    break;

                                                case "fecha":
                                                    String fecha = jsonReader.nextString();
                                                    bundle_respuestas.putString("fecha", fecha);
                                                    Log.d(LOG_TAG, "RESULTADOS Fecha-> Max:  " + fecha);

                                                    Calendar c = Calendar.getInstance();
                                                    int day = c.get(Calendar.DAY_OF_MONTH);

                                                    String dia_fecha = fecha.substring(fecha.length()-2, fecha.length());

                                                    int i_dia_fecha = Integer.parseInt(dia_fecha);


                                                    // Si la fecha de la prediccion es la fecha de
                                                    // hoy nos quedamos con los datos del bundle que se han guardado en esta iteracion
                                                    if(day == i_dia_fecha){
                                                        bundle_definitivo = bundle_respuestas;
                                                    }

                                                    break;
                                                default:
                                                    jsonReader.skipValue();
                                                    Log.d(LOG_TAG, "Obviamos lo demás");
                                                    break;
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                }
                                break;

                            case "id":
                                String id = jsonReader.nextString();
                                bundle_respuestas.putString("id", id);
                                Log.d(LOG_TAG, "RESULTADOS Fecha-> ID:  " + id);
                                break;

                            default:
                                jsonReader.skipValue();
                                Log.d(LOG_TAG, "Obviamos lo demás");
                                break;
                        }
                    }
                    jsonReader.endObject();
                }
                //jsonReader.endArray();


            } catch (
                    Exception e) {
                System.out.println("Exception: readWeatherDataJSON");
                e.printStackTrace();
                return null;
            }
            Log.d(LOG_TAG, "Finalizacdo el parseado del JSON");


            String s;
            s = "Prevision del tiempo para hoy " + bundle_definitivo.get("fecha") + ":\n";
            s += (String) bundle_definitivo.get("descrip_estadoCielo") + "\n";
            Log.d(LOG_TAG, "String return: " + s);
            s += "Probabilidad de precipitacion " + bundle_definitivo.get("value_probPrep") + "%\n";
            s += "Temperatura " + bundle_definitivo.get("minima_temp") + " - " + bundle_definitivo.get("maxima_temp") + " (Cº)";

            return s;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            tv_prevision.setText(previsionTiempo);
        }
    }

}
