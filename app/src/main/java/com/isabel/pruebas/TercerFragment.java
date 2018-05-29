package com.isabel.pruebas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.pruebas.Adapters.AdapterEntrega;
import com.isabel.pruebas.Adapters.AdapterOrdenes;
import com.isabel.pruebas.model.Entrega;
import com.isabel.pruebas.model.Ordenes;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TercerFragment extends Fragment {

    private ArrayList<Entrega> entregasList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterEntrega;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public TercerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tercer, container, false);

        recyclerView = view.findViewById(R.id.RVentrega);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        entregasList = new ArrayList<>();

        adapterEntrega = new AdapterEntrega(entregasList,R.layout.cardview_entrega,getActivity());
        recyclerView.setAdapter(adapterEntrega);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final String bandera = "Listo";
        databaseReference.child("Ordenes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entregasList.clear();

                if (dataSnapshot.exists()){
                    Ordenes ordenes = dataSnapshot.getValue(Ordenes.class);
                    Entrega entrega = dataSnapshot.getValue(Entrega.class);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        if (ordenes.getEstado().equals(bandera)){
                            entregasList.add(entrega);
                        }

                    }
                    adapterEntrega.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
