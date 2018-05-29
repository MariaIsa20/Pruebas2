package com.isabel.pruebas;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.pruebas.Adapters.AdapterPrestamo;
import com.isabel.pruebas.Adapters.AdapterProductos;
import com.isabel.pruebas.model.Prestamo;
import com.isabel.pruebas.model.Productos;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrimerFragment extends Fragment {

    private ArrayList<Productos> productosList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterProductos;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public EditText eMesa;

    Button bNext;
    String objeto, cantidad;

    public PrimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primer, container, false);

       // bNext = view.findViewById(R.id.bnext);

        eMesa = view.findViewById(R.id.eMesa);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        productosList = new ArrayList<>();


        adapterProductos = new AdapterProductos(productosList,R.layout.cardview_detalle,getActivity());
        recyclerView.setAdapter(adapterProductos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

            databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productosList.clear();
                if (dataSnapshot.exists()){



                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Productos productos = snapshot.getValue(Productos.class);
                        productosList.add(productos);
                    }
                    adapterProductos.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               *//* StringBuilder stringBuilder = new StringBuilder();


                for(Prestamo prest : prestamosList){

                    if (stringBuilder.length()>0){
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(prest.getNombre());
                }


                Toast.makeText(PrimerFragment.this.getContext(),
                        stringBuilder.toString(),Toast.LENGTH_SHORT).show();*//*

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < productosList.size();i++){

                    if (productosList.get(i).isSelected()){
                        objeto = productosList.get(i).getNombre();

                        stringBuilder.append(objeto);
                    }

                }

                Toast.makeText(PrimerFragment.this.getContext(), "en el fragment"+
                        stringBuilder ,Toast.LENGTH_SHORT).show();

            }
        });*/


//        for (int i = 0; i < prestamosList.size();i++){
//
//            if (prestamosList.get(i).isSelected()){
//                objeto = prestamosList.get(i).getNombre();
//                Toast.makeText(PrimerFragment.this.getContext(), "en el fragment"+
//                        objeto.toString(),Toast.LENGTH_SHORT).show();
//            }
//        }

        return view;

    }

}
