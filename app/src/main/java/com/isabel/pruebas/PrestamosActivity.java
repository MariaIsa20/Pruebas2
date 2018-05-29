package com.isabel.pruebas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.isabel.pruebas.Adapters.AdapterPrestamo;
import com.isabel.pruebas.model.Prestamo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class PrestamosActivity extends AppCompatActivity {

    private DatabaseReference databaseReference; //objeto para referencia a la base de datos

    EditText eNombre, eTelefono, eFecha, eObjeto, valor;
    Button bGuardar;
    ImageView isimagen;
    private Bitmap bitmap;
    private String urlFoto = "NO ha cargado";

    // List view
    private ListView listView;
    private ArrayList arrayList = new ArrayList<>();
    private ArrayAdapter<Prestamo> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //persistencia
        databaseReference = FirebaseDatabase.getInstance().getReference();//se obtiene la referencia

        eNombre = findViewById(R.id.eNombre);
        eTelefono = findViewById(R.id.eTelefono);
        eFecha = findViewById(R.id.eFecha);
        eObjeto = findViewById(R.id.eObjeto);
        bGuardar = findViewById(R.id.bGuardar);
        isimagen = findViewById(R.id.Isimagen);
        valor = findViewById(R.id.eCantidad);

        //****************** list view
       /* listView = findViewById(R.id.Listview);
        arrayAdapter = new ArrayAdapter<Prestamo>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        databaseReference.child("Prestamos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()){
                    Log.d("Listener","estoy en if");
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Prestamo prestamo = snapshot.getValue(Prestamo.class);
                        arrayList.add(prestamo.getNombre());
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

      //******************************** hasta aqui va listview
    }

   /* public void onClickedFoto(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        i.setType("image*//*"); //para que sea solo imagenes
        startActivityForResult(i,1234);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && requestCode == RESULT_OK){
            if (data == null){
                Toast.makeText(this,"Error cambiando imagen",Toast.LENGTH_SHORT).show();
            }else{
                Uri imagen = data.getData();
                //InputStream is = null;
               /* try {
                    InputStream is = getContentResolver().openInputStream(imagen);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmap = BitmapFactory.decodeStream(bis);
                    isimagen.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/

            }
        }
    }

// Con esto, se guardaron los datos en la base
    public void guardar(View view) {

     /*   FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference  storageReference= firebaseStorage.getReference();*/

/*        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // comprimir
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();*/

/*        storageReference.child("Fotos").child(databaseReference.push().getKey()).
                putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                urlFoto = taskSnapshot.getDownloadUrl().toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error",e.getMessage().toString());
            }
        });*/

        final String username = eNombre.getText().toString().toLowerCase();
        final boolean[] flag = {false};
        databaseReference.child("Prestamos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idgenerado:dataSnapshot.getChildren()){
                    String usernameTaken = idgenerado.child("nombre").getValue(String.class);
                    Log.d("obtenido","nombre leido:"+ usernameTaken);
                    Log.d("obtenido", "estado:"+ String.valueOf(flag[0]));
                    if (username.equals(usernameTaken.toLowerCase())){ // si es igual
                        flag[0] = true;
                        Log.d("obtenido","entro al if porque el nombre es igual:"+ String.valueOf(flag[0]));
                    }

                }
                if (!flag[0]){ // si es false
                    Log.d("obtenido","estoy en el if, nombre diferente");
                    Prestamo prestamos = new Prestamo(databaseReference.push().getKey(),
                            eNombre.getText().toString(),
                            Integer.valueOf(eTelefono.getText().toString()),
                            eFecha.getText().toString(),
                            eObjeto.getText().toString());

                    databaseReference.child("Prestamos").child(prestamos.getId()).setValue(prestamos).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                else{
                    Toast.makeText(PrestamosActivity.this,"Existe el nombre",Toast.LENGTH_SHORT).show();
                }




            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
