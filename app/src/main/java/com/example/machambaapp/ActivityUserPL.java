package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.machambaapp.model.UserPl;
import com.example.machambaapp.model.adapter.UserPlAdapter;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityUserPL extends AppCompatActivity {
    Button buttonRegister;
    private ArrayList<UserPl> userPls = new ArrayList<UserPl>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_pl);

        getUsersFromFirebase();

        buttonRegister =(Button) findViewById(R.id.registerClientPl);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserPL.this, ActivityUserRegister.class);
                startActivity(intent);
            }
        });
    }

    public void getUsersFromFirebase(){

        DatabaseHelper.databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {
                    String comunidade=userPLSnapshot.child("comunidade").getValue(String.class);;
                    String nome = userPLSnapshot.child("nome").getValue(String.class);
                    String phone=  userPLSnapshot.child("telefone").getValue(String.class);
                    String apelido= userPLSnapshot.child("apelido").getValue(String.class);
                    String distrito= userPLSnapshot.child("senha").getValue(String.class);
                    String localidade= userPLSnapshot.child("localidade").getValue(String.class);;
                    String postoAdministrativo=userPLSnapshot.child("postoAdministrativo").getValue(String.class);;
                    String genero=userPLSnapshot.child("genero").getValue(String.class);;
                    String senha=userPLSnapshot.child("senha").getValue(String.class);;
                   userPls.add(new UserPl( nome, apelido, senha,genero,phone,  null,  distrito, localidade, postoAdministrativo,  comunidade));
                }
                setAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewUserPl);
        UserPlAdapter userPlAdapter = new UserPlAdapter(this, userPls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userPlAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
  }
}