package com.isabel.pruebas;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText eCorreo, eContraseña;
    Button bResgistrar, bSesion;
    int LOGIN_CON_GOOGLE = 1;

    // Autenticacion con firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    // Autenticacion con google
    private GoogleApiClient googleApiClient;
    private SignInButton bIniciogoogle;
    // Autenticacion con Facebook
    private CallbackManager callbackManager; //objeto que permite capturar los datos de facebook
    private LoginButton bInicioFacebook; // instancia el boton de facebook


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // inicializar el sdk de facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create(); //inicializa el callback manager

        eCorreo = findViewById(R.id.eCorreo);
        eContraseña = findViewById(R.id.eContraseña);
        bResgistrar = findViewById(R.id.bRegistrar);
        bSesion = findViewById(R.id.bSesion);

        bIniciogoogle = findViewById(R.id.bIniciogoogle);

        bInicioFacebook = findViewById(R.id.bInicioFacebook);

        inicializar();

        // Boton de Google
        bIniciogoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,LOGIN_CON_GOOGLE);
            }
        }); //fin boton de google

        // Boton y permisos facebook
        bInicioFacebook.setReadPermissions("email","public_profile");

        bInicioFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login con facebook","Login exitoso");
                sigInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login con Facebook", "Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login con Facebook","Login Error");
                error.printStackTrace();
            }
        }); //fin boton facebook

        getHashes();
    }

    private void getHashes() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.isabel.pruebas",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.d("FirebaseUser","Usuario logueado" + firebaseUser.getDisplayName());
                    Log.d("FirebaseUser","Usuario logueado" + firebaseUser.getEmail());

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


    }

    // Metodos para sesion con Google: onActivityResult y sigInGoogle

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_CON_GOOGLE){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);


            //signInGoogle(googleSignInResult); //METODO
        }else { // esta parte es de Facebook
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void signInGoogle(GoogleSignInResult googleSignInResult) {

        if (googleSignInResult.isSuccess()){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               goMainActivity();
                           }else{
                               Toast.makeText(MainActivity.this,"Error inicio de sesion",Toast.LENGTH_SHORT).show();
                           }

                        }
                    });
        }/*else{
            Toast.makeText(MainActivity.this,"Autenticacion con google no exitosa",Toast.LENGTH_SHORT).show();
        }*/
    }


    // Hasta aqui con Google

            ///////////// INTENT /////////////////
    private void goMainActivity(){
       // Intent i = new Intent(MainActivity.this,PrincipalActivity.class);
       //Intent i = new Intent(MainActivity.this,PrestamosActivity.class);
        Intent i = new Intent(MainActivity.this,TabActivity.class);
        startActivity(i);
        finish();
    }

    // Metodos para acceder con Facebook
    private void sigInFacebook(AccessToken accessToken){

        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            goMainActivity();
                        }else {
                            Toast.makeText(MainActivity.this,"Autenticacion con Facebook no exitosa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Hasta aqui con Facebook

    public void crearcuenta(View view) {
        Registrar(eCorreo.getText().toString(),eContraseña.getText().toString());
    }

    private void Registrar(String correo, String contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(correo,contraseña).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Cuenta creada",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Error al crear cuenta",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void iniciasesion(View view) {
        Sesion(eCorreo.getText().toString(),eContraseña.getText().toString());
    }

    private void Sesion(String correo, String contraseña) {
        firebaseAuth.signInWithEmailAndPassword(correo,contraseña).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Inicio de sesion exitosa",Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(MainActivity.this,PrincipalActivity.class);
//                            startActivity(i);
//                            finish();
                            goMainActivity();

                        }else {
                            Toast.makeText(MainActivity.this,"Error en inicio de seison",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
