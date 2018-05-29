package com.isabel.pruebas.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isabel.pruebas.R;
import com.isabel.pruebas.model.Prestamo;

import java.util.ArrayList;

/**
 * Created by Isabel on 25/04/2018.
 */

public class AdapterPrestamo extends RecyclerView.Adapter<AdapterPrestamo.PrestamoViewHolder> {

    FloatingActionButton fab;
    private ArrayList<Prestamo> prestamosList;
    private int resource;
    private Activity activity;
    public String objeto,cantidad;




    public AdapterPrestamo(ArrayList<Prestamo> prestamosList,int resource, Activity activity){
        this.prestamosList = prestamosList;
        this.resource = resource;
        this.activity = activity;
    }

    public AdapterPrestamo(String objeto, String cantidad) {
        this.objeto = objeto;
        this.cantidad = cantidad;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public PrestamoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity,"Abre actividad con detalle",Toast.LENGTH_SHORT).show();
            }
        });

        return new PrestamoViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final PrestamoViewHolder holder, final int position) {
        final Prestamo prestamo = prestamosList.get(position);
        holder.bindPrestamo(prestamo,activity);

       // holder.Check.setText("check" + position);
        holder.Check.setChecked(prestamosList.get(position).isSelected());
        holder.tNombre.setText(prestamosList.get(position).getNombre());
        //cantidad = holder.eCantidad.getText().toString();
        holder.Check.setTag(position);
        holder.Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Integer pos = (Integer) holder.Check.getTag();
                //cantidad = String.valueOf(prestamosList.get(pos).getCantidad());
                Toast.makeText(activity,prestamosList.get(pos).getNombre()+"clicked"
                        +holder.eCantidad.getText() ,Toast.LENGTH_SHORT).show();

                if (prestamosList.get(pos).isSelected()){
                    prestamosList.get(pos).setSelected(false);
                }else{
                    prestamosList.get(pos).setSelected(true);
                }

            }

        });

        holder.bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return prestamosList.size();

    }

    public class PrestamoViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre, tObjeto, tTelefono, tFecha;
        private ImageView ifoto;
        private EditText eCantidad;
        private CheckBox Check;
        private Button bOK;


        public PrestamoViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre);
           // tObjeto = itemView.findViewById(R.id.tObjeto);
            //ifoto = itemView.findViewById(R.id.ifoto);
            bOK = itemView.findViewById(R.id.bOK);
            eCantidad = itemView.findViewById(R.id.eCantidad);
           // Check = itemView.findViewById(R.id.Check);
           //fab = itemView.findViewById(R.id.FloatCheck);

        }

        public void bindPrestamo(Prestamo prestamo, Activity activity) {
            tNombre.setText(prestamo.getNombre());
            tObjeto.setText(prestamo.getObjeto());
            //eCantidad.getText().toString();
           // Picasso.get().load(prestamo.getFoto()).into(ifoto);


        }

    }



}

