package com.g9.letsmoveapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorDatosAnt extends RecyclerView.Adapter<adaptadorDatosAnt.ViewHolderViajes> {

    ArrayList<VisualViajes> listaViajesAnt;


    public adaptadorDatosAnt(ViajesAnteriores viajesAnteriores, ArrayList<VisualViajes> listaViajesAnt) {
        this.listaViajesAnt = listaViajesAnt;
    }


    @NonNull
    @Override
    public ViewHolderViajes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       // RecyclerView recyclerViajes = findViewById(R.id.recyclerAct);
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null,false);
        return new ViewHolderViajes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderViajes viewHolderDatos, int i) {

        //Aquí es donde vamos a ir llenando la lista
        viewHolderDatos.etiquetaNombre.setText(listaViajesAnt.get(i).getNombre());
        viewHolderDatos.etiquetaInformacion.setText(listaViajesAnt.get(i).getInfo());
        viewHolderDatos.etiquetaFoto.setImageResource(listaViajesAnt.get(i).getFoto());

    }

    @Override
    public int getItemCount() {
        return listaViajesAnt.size();
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
