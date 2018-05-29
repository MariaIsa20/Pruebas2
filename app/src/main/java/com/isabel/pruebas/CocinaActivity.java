package com.isabel.pruebas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.pruebas.Adapters.AdapterOrdenes;
import com.isabel.pruebas.model.Ordenes;

import java.util.ArrayList;

public class CocinaActivity extends AppCompatActivity {

    private ArrayList<Ordenes> ordenesList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterOrden;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);

        recyclerView = findViewById(R.id.RVcocina);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ordenesList = new ArrayList<>();
        adapterOrden = new AdapterOrdenes(ordenesList);
        recyclerView.setAdapter(adapterOrden);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Ordenes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordenesList.clear();

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        Ordenes ordenes = snapshot.getValue(Ordenes.class);
                        ordenesList.add(ordenes);
                    }
                    adapterOrden.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
