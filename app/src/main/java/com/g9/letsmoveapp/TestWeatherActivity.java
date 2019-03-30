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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
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
        protected Object doInBackground(Object[] objects) {


            String CP = "28109"; // Codigo postal de Pelayos de la Presa
            String stringURL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" +
                    CP + "/?api_key=" + AEMET_KEY;

            tv_1.setText(stringURL);
            Log.d(LOG_TAG, stringURL);


            URL url = null;
            BufferedInputStream is = null;
            JsonReader jsonReader;

            try {
                url = new URL(stringURL);
            } catch (Exception ex) {
                System.out.println("Malformed URL");
            }

            try {
                if (url != null) {
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    int response = urlConnection.getResponseCode();
                    Log.d(LOG_TAG, "The response is: " + response);


                    // Peta en esta linea ->
                    is = new BufferedInputStream(urlConnection.getInputStream());

                    Log.d(LOG_TAG, is.toString());
                }
            } catch (IOException ioe) {
                System.out.println("IOException");
            }


            if (is != null) {
                try {
                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                    jsonReader = new JsonReader(isReader);

                    BufferedReader buff = new BufferedReader(isReader);
                    String response = buff.readLine();


                    Log.d(LOG_TAG,response);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
