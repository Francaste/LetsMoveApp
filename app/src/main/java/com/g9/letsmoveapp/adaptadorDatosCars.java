package com.g9.letsmoveapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class adaptadorDatosCars extends RecyclerView.Adapter<adaptadorDatosCars.ViewHolderCars> {

    ArrayList listaCars;
    Uri photouri;
//He añadido como parámetro el Uri photouri
    public adaptadorDatosCars(MisCoches misCoches, ArrayList listaCars, Uri photouri) {
        this.listaCars = listaCars;
        this.photouri = photouri; //Añadido

    }

    @NonNull
    @Override
    public ViewHolderCars onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //RecyclerView recyclerViajes = findViewById(R.id.recyclerAct);
        //He puesto false y estaba true aquí
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_car, null, false);
        return new ViewHolderCars(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCars viewHolderDatos, int i) {
        CarsDataModel carsDataModel;

        //Aquí es donde vamos a ir llenando la lista

        carsDataModel = (CarsDataModel) listaCars.get(i);

        viewHolderDatos.etiquetaNombre.setText(carsDataModel.getModel());
        viewHolderDatos.etiquetaInformacion.setText("Plazas: "+carsDataModel.getPlazas()
                + " | Color: " + carsDataModel.getColor()
                + " | Tamaño: " + carsDataModel.getSize());
        viewHolderDatos.etiquetaFoto.setImageURI(photouri);
        viewHolderDatos.etiquetaFoto.setImageResource(R.mipmap.ic_bat);

    }

    @Override
    public int getItemCount() {
        return listaCars.size();
    }


    public class ViewHolderCars extends RecyclerView.ViewHolder {

        TextView etiquetaNombre, etiquetaInformacion;
        ImageView etiquetaFoto;

        public ViewHolderCars(@NonNull View itemView) {
            super(itemView);
            etiquetaNombre = (TextView) itemView.findViewById(R.id.idModelo);
            etiquetaInformacion = (TextView) itemView.findViewById(R.id.idDescripcionCar);
            etiquetaFoto = (ImageView) itemView.findViewById(R.id.idFotoCar);
        }
    }
}
