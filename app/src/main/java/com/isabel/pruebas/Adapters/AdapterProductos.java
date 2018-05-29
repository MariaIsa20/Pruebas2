package com.isabel.pruebas.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.pruebas.PrestamosActivity;
import com.isabel.pruebas.R;
import com.isabel.pruebas.model.Ordenes;
import com.isabel.pruebas.model.Pedido;
import com.isabel.pruebas.model.Prestamo;
import com.isabel.pruebas.model.Productos;
import com.isabel.pruebas.model.Productos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isabel on 6/05/2018.
 */

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ProductosViewHolder> implements View.OnClickListener {

    private ArrayList<Productos> productosList;
    private int resource;
    private Activity activity;

    private String Cantidad, Producto;

    private DatabaseReference databaseReference; //objeto para referencia a la base de datos
    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario


    public AdapterProductos(ArrayList<Productos> productosList, int resource, Activity activity) {
        this.productosList = productosList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(activity,"Abre actividad con detalle",Toast.LENGTH_SHORT).show();
            }
        });

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //persistencia
        databaseReference = FirebaseDatabase.getInstance().getReference();//se obtiene la referencia
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase
        return new ProductosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductosViewHolder holder, final int position) {
        Productos productos = productosList.get(position);
        holder.bindproductos(productos,activity);


       /* holder.bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // De aqui lo mando a Firebase en pedidos
                //final String username = eNombre.getText().toString().toLowerCase();
                final boolean[] flag = {false};
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
                databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//
                            String user = firebaseUser.getEmail() ;
                            Toast.makeText(activity,databaseReference.getDatabase().toString() ,Toast.LENGTH_SHORT).show();

                            Pedido pedido = new Pedido(databaseReference.push().getKey(),  //id_orden
                                    databaseReference.push().getKey(), //id_pedido
                                    user,
                                    Integer.valueOf(holder.tValor.getText().toString()),
                                    Integer.valueOf(holder.eCantidad.getText().toString()),
                                    holder.eMesa.getText().toString(),
                                    holder.tProducto.getText().toString()
                                    );

                            databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(activity,productosList.get(position).getNombre()+ productosList.get(position)
                        .getValor() + holder.eCantidad.getText() ,Toast.LENGTH_SHORT).show();

            }
        });

        holder.bEliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });*/




    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    @Override
    public void onClick(View view) {

    }


    public class ProductosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tProducto, tValor;
        private EditText eCantidad;
        public EditText eMesa;
        //private CheckBox Check;
        public Button bOK, bEliminar;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            tProducto = itemView.findViewById(R.id.tNombre);
            tValor = itemView.findViewById(R.id.tValor);
            eCantidad = itemView.findViewById(R.id.eCantidad);
            bOK = itemView.findViewById(R.id.bOK);
            eMesa = activity.findViewById(R.id.eMesa);
          //  bEliminar = itemView.findViewById(R.id.bEliminar);

            //Check = itemView.findViewById(R.id.Check);
        }

        public void bindproductos(Productos productos, Activity activity) {
            tProducto.setText(productos.getNombre());
            tValor.setText(String.valueOf(productos.getValor()));
            eCantidad.getText().toString();
            eMesa.getText().toString();
          //  bEliminar.setOnClickListener(this);
            bOK.setOnClickListener(this);

            final String[] elemento = new String[1];

            bOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // De aqui lo mando a Firebase en pedidos
                    //final String username = eNombre.getText().toString().toLowerCase();
                    final boolean[] flag = {false};
                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
                    databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//
                            String user = firebaseUser.getEmail() ;
                            //Toast.makeText(activity,databaseReference.getDatabase().toString() ,Toast.LENGTH_SHORT).show();
                            //databaseReference = FirebaseDatabase.getInstance().getReference();
                            final Pedido pedido = new Pedido(
                                    databaseReference.push().getKey(), //id_pedido
                                    user,
                                    Integer.valueOf(tValor.getText().toString()),
                                    Integer.valueOf(eCantidad.getText().toString()),
                                    eMesa.getText().toString(),
                                    tProducto.getText().toString()
                            );
                            String state = "Pendiente";
                            Ordenes ordenes = new Ordenes(
                                    databaseReference.push().getKey(),
                                    eMesa.getText().toString(),
                                    tProducto.getText().toString(),
                                    Integer.valueOf(eCantidad.getText().toString()),
                                    state
                            );



                            /*databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                        Pedido pedido1 = dataSnapshot1.getValue(Pedido.class);

                                        if (pedido1.getId_mesa().equals(eMesa.getText().toString())
                                                && pedido1.getId_usuario().equals(tValor.getText().toString())
                                                && pedido1.getCantidad()==Integer.valueOf(eCantidad.getText().toString())
                                                ){

                                            elemento[0] =pedido.getId_pedido();

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                            databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });

                            databaseReference.child("Ordenes").child(ordenes.getId_orden()).setValue(ordenes).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //Toast.makeText(activity,productosList.get(position).getNombre()+ productosList.get(position)
                      //      .getValor() + holder.eCantidad.getText() ,Toast.LENGTH_SHORT).show();

                }
            });



            /*bEliminar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.d("estoyboton"," eliminar");
                    final boolean[] flag = {true};

                    databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String umesa = eMesa.getText().toString();
                            String nombre = tProducto.getText().toString();

                            String clave3 = "";

                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                String clave = dataSnapshot1.child("id_mesa").getValue(String.class);
                                String clave2 = dataSnapshot1.child("nombre").getValue(String.class);



                                // Log.d("estoy boton","clave" + clave);
                                Log.d("estoyboton","clave" + dataSnapshot1.child("id_mesa").getValue()+umesa);
                                Log.d("estoyboton","clave" + clave2+nombre);

                                //databaseReference.child("Pedido").child(clave3).removeValue();
                                if (umesa.equals(clave)
                                        && nombre.equals(clave2)
                                        && elemento.equals(clave3)){
                                    flag[0] = false;
                                    Log.d("estoyboton","umesa=clave"+ umesa + elemento);
                                    Log.d("estoyboton",databaseReference.child("Pedido").child(clave3).toString());
                                    databaseReference.child("Pedido").child(clave3).setValue(null);


                                }
                                clave3 = null;
                            }
                            clave3 = "";

                            if (!flag[0]){
                                Log.d("estoyboton","en el if de eliminar");
                                // databaseReference.child("Pedido").child(clave3).removeValue();


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });*/


        }

        @Override
        public void onClick(View view) {

        }

       /* @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.bOK:
                    // De aqui lo mando a Firebase en pedidos
                    //final String username = eNombre.getText().toString().toLowerCase();

                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
                    databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
//
                            String user = firebaseUser.getEmail() ;
                            Toast.makeText(activity,databaseReference.getDatabase().toString() ,Toast.LENGTH_SHORT).show();

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            Pedido pedido = new Pedido(databaseReference.push().getKey(),  //id_orden
                                    databaseReference.push().getKey(), //id_pedido
                                    user,
                                    Integer.valueOf(tValor.getText().toString()),
                                    Integer.valueOf(eCantidad.getText().toString()),
                                    eMesa.getText().toString(),
                                    tProducto.getText().toString()
                            );

                            databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                 break;

                case R.id.bEliminar:
                    Log.d("estoyboton"," eliminar");
                    final boolean[] flag = {true};
                    databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String umesa = eMesa.getText().toString();
                            String nombre = tProducto.getText().toString();

                            String clave3 = "";

                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                String clave = dataSnapshot1.child("id_mesa").getValue(String.class);
                                String clave2 = dataSnapshot1.child("nombre").getValue(String.class);

                                clave3 = dataSnapshot1.getKey();

                               // Log.d("estoy boton","clave" + clave);
                                Log.d("estoyboton","clave" + dataSnapshot1.child("id_mesa").getValue()+umesa);
                                Log.d("estoyboton","clave" + clave2+nombre);

                                //databaseReference.child("Pedido").child(clave3).removeValue();
                                if (umesa.equals(clave)
                                        && nombre.equals(clave2)){
                                    flag[0] = false;
                                    Log.d("estoyboton","umesa=clave"+ umesa);
                                    Log.d("estoyboton",databaseReference.child("Pedido").child(clave3).toString());
                                    databaseReference.child("Pedido").child(clave3).setValue(null);


                                }
                                clave3 = null;
                            }
                            clave3 = "";

                            if (!flag[0]){
                                 Log.d("estoyboton","en el if de eliminar");
                               // databaseReference.child("Pedido").child(clave3).removeValue();


                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    break;
            }
        }*/
    }
}
