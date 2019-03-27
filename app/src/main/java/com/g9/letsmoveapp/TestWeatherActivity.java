package com.g9.letsmoveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TestWeatherActivity extends AppCompatActivity {

    TextView tv_1, tv_2, tv_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weather);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);

        weather();
    }

    private void weather() {

        // el esqueleto de este codigo en: https://developer.android.com/training/volley/request

        String url = "https://opendata.aemet.es/opendata/sh/a17b3c1a"; // url de hacer una peticion con el codigo postal 28109

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "WEATHER", Toast.LENGTH_SHORT).show();
                            // TODO: set text a los text view
                            JSONArray jsonArray = response.getJSONArray("");
                            JSONObject object = jsonArray.getJSONObject(2);
                            String nombre = object.getString("nombre");



                            tv_1.setText(nombre);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }
}
