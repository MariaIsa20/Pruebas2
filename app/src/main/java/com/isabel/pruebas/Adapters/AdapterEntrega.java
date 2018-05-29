package com.isabel.pruebas.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.pruebas.R;
import com.isabel.pruebas.model.Entrega;

import java.util.ArrayList;

/**
 * Created by Isabel on 28/05/2018.
 */

public class AdapterEntrega extends RecyclerView.Adapter<AdapterEntrega.EntregaViewHolder>{

    private ArrayList<Entrega> entregaList;
    private int resource;
    private Activity activity;

    private DatabaseReference databaseReference;

    public AdapterEntrega(ArrayList<Entrega> entregaList, int resource, Activity activity) {
        this.entregaList = entregaList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AdapterEntrega.EntregaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new EntregaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterEntrega.EntregaViewHolder holder, int position) {
        Entrega entrega = entregaList.get(position);
        holder.bindentrega(entrega,activity);
    }

    @Override
    public int getItemCount() {
        return entregaList.size();
    }

    public class EntregaViewHolder extends RecyclerView.ViewHolder {
        public TextView tMesa, tProducto, tCantidad;
        public Button bFin;

        public EntregaViewHolder(View itemView) {
            super(itemView);
            tMesa = itemView.findViewById(R.id.tMesa4);
            tProducto = itemView.findViewById(R.id.tNombre4);
            tCantidad = itemView.findViewById(R.id.tCantidad4);
            bFin = itemView.findViewById(R.id.bEntregado);
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        public void bindentrega(final Entrega entrega, Activity activity) {
            tMesa.setText(entrega.getId_mesa());
            tProducto.setText(entrega.getNombre());
            tCantidad.setText(entrega.getCantidad());

            bFin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("entrega",databaseReference.child("Ordenes").child(String.valueOf(getItemId())).toString());
                }
            });
        }
    }
}
