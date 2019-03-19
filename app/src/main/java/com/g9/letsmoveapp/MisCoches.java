package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class  MisCoches extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_coches);
    }
    public void nuevoCoche (View view){
        Intent intent = new Intent(this, NuevoCoche.class);
        startActivity(intent);
    }
}
