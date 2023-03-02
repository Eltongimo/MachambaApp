package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.UserAdmin;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ActivityLogin extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    EditText editTextPhone;
    EditText editTextPassword;
    TextView textViewAlert;

   private Button buttonLogar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword=(EditText) findViewById(R.id.idEditPassword);
        editTextPhone =(EditText) findViewById(R.id.idEditUserName);
        textViewAlert=(TextView) findViewById(R.id.idAlert);



           buttonLogar=(Button) findViewById(R.id.idButtonlogar);
           buttonLogar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(editTextPhone.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                       editTextPassword.setError("campo vazio");
                       editTextPhone.setError("campo vazio");
                       textViewAlert.setText("Entrada invalido");
                   }else {
                       if (verificationPasswordAndUserNameAdmin()) {
                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                           startActivity(intent);
                       } else {
                           databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {


                                   if(snapshot.hasChild(editTextPhone.getText().toString())){

                                       String getPasswordFromFirebase=snapshot.child(editTextPhone.getText().toString()).child("senha").getValue(String.class);
                                       if(getPasswordFromFirebase.equalsIgnoreCase(editTextPassword.getText().toString())){
                                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                           startActivity(intent);
                                       }else {
                                           textViewAlert.setText("Usuario ou senha invalido");
                                       }

                                   }

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }

                           });

                       }
                   }


               }

           });
           }


    boolean verificationPasswordAndUserNameAdmin(){

        UserAdmin userAdmin=new UserAdmin();
        Privilegios privilegios=new Privilegios();
            if(userAdmin.getUserName().equalsIgnoreCase(editTextPhone.getText().toString())
                    && userAdmin.getPassWord().equalsIgnoreCase(editTextPassword.getText().toString()) ){
                textViewAlert.setText("");
                privilegios.setAllAcessView(true);
                return true;
            }
     return false;
    }

    boolean verificationPasswordAndUserNamePl(){
        final boolean isTrue = false;


        return false;
    }
}