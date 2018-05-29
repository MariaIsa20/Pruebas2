package com.isabel.pruebas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class PrincipalActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView tMostrar;
    Button bCerrar;
    ImageView Ifoto;

    // Autenticacion con firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    // Con Google
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tMostrar = findViewById(R.id.tMostrar);
        bCerrar = findViewById(R.id.bCerrar);
        Ifoto = findViewById(R.id.Ifoto);

        inicializar();
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    tMostrar.setText("Correo" + firebaseUser.getEmail());
                    Picasso.get().load(firebaseUser.getPhotoUrl()).into(Ifoto);

                }else{
                    Log.d("FirebaseUser", "El usuario ha cerrado sesion");
                }

            }
        };

        // Inicializar con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        // hasta aqui va con google

    }

    @Override
    protected void onStart() {//firebase
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener); //firebase
        googleApiClient.disconnect(); //google
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(this); //google
        googleApiClient.disconnect(); //google
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect(); //google
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);//google
        googleApiClient.disconnect();//google
    }

    public void cerrarsesion(View view) {
        firebaseAuth.signOut(); // signout con correo y contraseña, firebase
        // validacion para saber si esta con google o facebook
        if (Auth.GoogleSignInApi != null){
            // signout con google
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()){
                        goLoginActivity();
                    }else{
                        Toast.makeText(PrincipalActivity.this,"Error cerrado sesion con google",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // hasta aqui signout con google
        }
        // signout con facebook
        if (LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }


    }

    private void goLoginActivity(){
        Intent i = new Intent(PrincipalActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
