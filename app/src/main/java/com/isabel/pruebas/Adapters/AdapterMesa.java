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
import com.isabel.pruebas.model.Mesa;
import com.isabel.pruebas.model.Pedido;
import com.isabel.pruebas.model.Productos;

import java.util.ArrayList;

/**
 * Created by Isabel on 24/05/2018.
 */
//Adapter para mostrar el detalle de cada mesa
public class AdapterMesa extends RecyclerView.Adapter<AdapterMesa.MesaViewHolder>{
    private ArrayList<Pedido> pedidolist;
    private int resource;
    private Activity activity;

    private DatabaseReference databaseReference; //objeto para referencia a la base de datos

    public AdapterMesa(ArrayList<Pedido> pedidolist /*, int resource, Activity activity, DatabaseReference databaseReference*/) {
        this.pedidolist = pedidolist;
        //this.resource = resource;
        //this.activity = activity;

    }

    @Override
    public MesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detallepedido,null,false);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //on click en el card view
             }
         });

         databaseReference = FirebaseDatabase.getInstance().getReference();

        return new MesaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MesaViewHolder holder, int position) {
        Pedido pedidocompleto = pedidolist.get(position);
        holder.bindpedido(pedidocompleto,activity);
    }

    @Override
    public int getItemCount() {
        return pedidolist.size();
    }

    public class MesaViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre, tValor, tCantidad, tMesa;
        private Button bEliminar;


        public MesaViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre2);
            tValor = itemView.findViewById(R.id.tValor2);
            tCantidad = itemView.findViewById(R.id.tCantidad2);
            bEliminar = itemView.findViewById(R.id.bDelete);
           // tMesa = findViewById(R.id.tMostrar);
        }

        public void bindpedido(final Pedido pedidocompleto, Activity activity) {

            tNombre.setText(pedidocompleto.getNombre());
            tValor.setText(String.valueOf(pedidocompleto.getValor()));
            tCantidad.setText(String.valueOf(pedidocompleto.getCantidad()));
           // final String Tmesa = tMesa.getText().toString();

            bEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d("bdelete","datasnapshot"+ pedidocompleto.getId_mesa()+pedidocompleto.getId_pedido());
                            databaseReference.child("Pedido").child(pedidocompleto.getId_pedido()).removeValue();
//                            for (DataSnapshot mesasnapshot:dataSnapshot.getChildren()){
//
//                                Pedido pedido = mesasnapshot.getValue(Pedido.class);
//
//                                Log.d("bdelete","datasnapshot"+ pedidocompleto.getId_mesa()+pedidocompleto.getId_pedido());
////                                if (pedido.getId_mesa().equals(Tmesa)){
////                                    //String pos = getAdapterPosition();
////                                    Log.d("bdelete","datasnapshot"+ pedidocompleto);
////                                }
//
//
//                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

            /*databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){

                        Pedido pedido = dataSnapshot.getValue(Pedido.class);

                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            Pedido pedido1 = dataSnapshot1.getValue(Pedido.class);
                            if (pedido.getId_mesa().equals(pedido1.getId_mesa())){
                                tNombre.setText(pedidocompleto.getNombre());
                                tValor.setText(String.valueOf(pedidocompleto.getValor()));
                                tCantidad.setText(pedidocompleto.getCantidad());

                            }

                        }

                        tNombre.setText(pedidocompleto.getNombre());
                        tValor.setText(String.valueOf(pedidocompleto.getValor()));
                        tCantidad.setText(pedidocompleto.getCantidad());
                    }

                    tNombre.setText(pedidocompleto.getNombre());
                    tValor.setText(String.valueOf(pedidocompleto.getValor()));
                    tCantidad.setText(String.valueOf(pedidocompleto.getCantidad()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/

            //tNombre.setText(pedidocompleto.getNombre());
            //tValor.setText(String.valueOf(pedidocompleto.getValor()));
            //tCantidad.setText(pedidocompleto.getCantidad());

        }
    }
}
