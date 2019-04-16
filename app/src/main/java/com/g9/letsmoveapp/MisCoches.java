package com.g9.letsmoveapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
