package com.g9.letsmoveapp;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorDatos extends RecyclerView.Adapter<adaptadorDatos.ViewHolderViajes> {

    ArrayList<VisualViajes> listaViajes;

    public adaptadorDatos(ArrayList<VisualViajes> listaViajes) {
        this.listaViajes = listaViajes;
    }

    @NonNull
    @Override
    public ViewHolderViajes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esto es lo que me ha autorellenado para que se calle
        AlertDialog.Builder parent = null;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null,false);
        return new ViewHolderViajes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderViajes viewHolderDatos, int i) {

        //Aquí es donde vamos a ir llenando la lista
        viewHolderDatos.etiquetaNombre.setText(listaViajes.get(i).getNombre());
        viewHolderDatos.etiquetaInformacion.setText(listaViajes.get(i).getInfo());
        viewHolderDatos.etiquetaFoto.setImageResource(listaViajes.get(i).getFoto());

    }

    @Override
    public int getItemCount() {
        return listaViajes.size();
    }

    public class ViewHolderViajes extends RecyclerView.ViewHolder {

        TextView etiquetaNombre,etiquetaInformacion;
        ImageView etiquetaFoto;

        public ViewHolderViajes(@NonNull View itemView) {
            super(itemView);
            etiquetaNombre=(TextView) itemView.findViewById(R.id.idViajes);
            etiquetaInformacion=(TextView)itemView.findViewById(R.id.idDescripcion);
            etiquetaFoto=(ImageView)itemView.findViewById(R.id.idFoto);
        }
    }
}
