package com.g9.letsmoveapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorDatos extends RecyclerView.Adapter<adaptadorDatos.ViewHolderViajes> {

    ArrayList listaViajes;
    Uri photouri;

    public adaptadorDatos(ViajesActuales viajesActuales, ArrayList listaViajes, Uri photouri) {
        this.listaViajes = listaViajes;
        this.photouri = photouri;
    }

    @NonNull
    @Override
    public ViewHolderViajes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //RecyclerView recyclerViajes = findViewById(R.id.recyclerAct);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderViajes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderViajes viewHolderDatos, int i) {
        RidesDataModel ridesDataModel;

        //Aquí es donde vamos a ir llenando la lista

        ridesDataModel = (RidesDataModel) listaViajes.get(i);

        viewHolderDatos.etiquetaNombre.setText(ridesDataModel.getR_name());
        viewHolderDatos.etiquetaInformacion.setText(ridesDataModel.getOrigen() + "-" + ridesDataModel.getDestino()
                + " | " + ridesDataModel.getFecha_salida() + " | " + ridesDataModel.getHora_salida() + "-" + ridesDataModel.getHora_llegada() +
                " | " + ridesDataModel.getHora_limite() + "'");
        viewHolderDatos.etiquetaFoto.setImageURI(photouri);
        viewHolderDatos.etiquetaFoto.setImageResource(R.mipmap.ic_bat);

    }

    @Override
    public int getItemCount() {
        return listaViajes.size();
    }


    public class ViewHolderViajes extends RecyclerView.ViewHolder {

        TextView etiquetaNombre, etiquetaInformacion;
        ImageView etiquetaFoto;

        public ViewHolderViajes(@NonNull View itemView) {
            super(itemView);
            etiquetaNombre = (TextView) itemView.findViewById(R.id.idViajes);
            etiquetaInformacion = (TextView) itemView.findViewById(R.id.idDescripcion);
            etiquetaFoto = (ImageView) itemView.findViewById(R.id.idFoto);
        }
    }
}
