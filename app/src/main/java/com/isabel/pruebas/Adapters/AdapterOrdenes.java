package com.isabel.pruebas.Adapters;

import android.support.v7.widget.RecyclerView;
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
import com.isabel.pruebas.model.Ordenes;

import java.util.ArrayList;

/**
 * Created by Isabel on 28/05/2018.
 */
//Para cargar en la cocina y modificar la variable Estado
public class AdapterOrdenes extends RecyclerView.Adapter<AdapterOrdenes.OrdenViewHolder> {
    private ArrayList<Ordenes> ordenesList;

    private DatabaseReference databaseReference;

    public AdapterOrdenes(ArrayList<Ordenes> ordenesList) {
        this.ordenesList = ordenesList;
    }

    @Override
    public AdapterOrdenes.OrdenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cocina,null,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        return new OrdenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterOrdenes.OrdenViewHolder holder, int position) {
            Ordenes orden = ordenesList.get(position);
            holder.bindorden(orden);
    }

    @Override
    public int getItemCount() {
        return ordenesList.size();
    }

    public class OrdenViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre, tCantidad, tMesa;
        private Button bListo;

        public OrdenViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre3);
            tCantidad = itemView.findViewById(R.id.tCantidad3);
            tMesa = itemView.findViewById(R.id.tMesa3);
            bListo = itemView.findViewById(R.id.bListo);
        }

        public void bindorden(final Ordenes orden) {
            tNombre.setText(orden.getNombre());
            tCantidad.setText(String.valueOf(orden.getCantidad()));
            tMesa.setText(orden.getId_mesa());

            bListo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("Ordenes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            databaseReference.child("Ordenes").child(orden.getId_orden()).child("estado").setValue("Listo");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

        }
    }
}
