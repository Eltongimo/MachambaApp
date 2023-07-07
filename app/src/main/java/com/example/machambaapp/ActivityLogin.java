package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.Manifest;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.UserAdmin;
import com.example.machambaapp.model.adapter.UserPlAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.helper.OfflineDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    EditText editTextPhone;
    EditText editTextPassword;
    TextView textViewAlert;
    ProgressDialog loadingBar;

    private Button buttonLogar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword=(EditText) findViewById(R.id.idEditPassword);
        editTextPhone =(EditText) findViewById(R.id.idEditUserName);
        textViewAlert=(TextView) findViewById(R.id.idAlert);
        loadingBar = new ProgressDialog(this);



        buttonLogar=(Button) findViewById(R.id.idButtonlogar);
           buttonLogar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   ArrayList<Cliente.UserPl> u = new OfflineDB(getApplicationContext()).getUsers();


                   if (editTextPhone.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                       editTextPassword.setError("Campo Vazio");
                       editTextPhone.setError("Campo Vazio");
                       textViewAlert.setText("Entrada inválida!");
                   } else {
                       if (verificationPasswordAndUserNameAdmin()) {
                           editTextPassword.setText("");
                           editTextPhone.setText("");
                           textViewAlert.setText("");

                           loadingBar.setTitle("Entrando como Administrador...");
                           loadingBar.setMessage("Aguarde por favor!");
                           loadingBar.setCanceledOnTouchOutside(false);
                           loadingBar.show();

                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                   Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityLogin.this).toBundle();
                                   startActivity(intent, b);
                                   loadingBar.dismiss();
                               }
                           }, 2000); // 2000 milissegundos = 2 segundos

                       } else {
                           boolean isUserFound = false;
                           for (Cliente.UserPl uu : u) {
                               if (uu.getNome() != null) {
                                   if (uu.getPhone().equals(editTextPhone.getText().toString()) && uu.getSenha().equalsIgnoreCase(editTextPassword.getText().toString())) {
                                       isUserFound = true;
                                       Privilegios privilegios = new Privilegios();
                                       privilegios.setAllAcessView(false);
                                       SplashScreen.currentUser = uu;
                                       loadingBar.setTitle("Entrando como PL...");
                                       loadingBar.setMessage("Aguarde por favor!");
                                       loadingBar.setCanceledOnTouchOutside(false);
                                       loadingBar.show();

                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {

                                               Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                               Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityLogin.this).toBundle();
                                               startActivity(intent, b);
                                               loadingBar.dismiss();
                                           }
                                       }, 2000); // 2000 milissegundos = 2 segundos
                                       break;
                                   }
                               }
                           }

                           if (!isUserFound) {
                               textViewAlert.setText("Password ou número inválido, tente novamente!");
                           }
                       }
                   }


               }


           });
    }

    boolean verificationPasswordAndUserNameAdmin(){

        UserAdmin userAdmin=new UserAdmin();
        Privilegios privilegios=new Privilegios();
            if(userAdmin.getUserName().equalsIgnoreCase(editTextPhone.getText().toString())
                    && userAdmin.getPassWord().equalsIgnoreCase(editTextPassword.getText().toString()) ) {
                textViewAlert.setText("");
                privilegios.setAllAcessView(true);
                return true;
            }
        return false;
    }
}