package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.model.adapter.DistritoAdapter;
import com.example.machambaapp.model.adapter.EtniaAdapter;
import com.example.machambaapp.model.adapter.LocalidadeAdapter;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.datamodel.Localidade;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.machambaapp.model.datamodel.Distrito;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ActivityViewLocalidade extends AppCompatActivity {

    Button registerLocalidade;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Localidade> localidades = new ArrayList<Localidade>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_localidade);

        getLocalidadesFromFirebase();
        registerLocalidade = (Button) findViewById(R.id.registerLocalidade);
        registerLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewLocalidade.this,AddLocalidade.class));
            }
        });
    }

    private void getLocalidadesFromFirebase(){
        databaseReference.child("localidades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                localidades.clear();
                for (DataSnapshot localidadesSnap : snapshot.getChildren()) {
                    String nomeLocalidades = localidadesSnap.child("nome").getValue(String.class);
                    localidades.add(new Localidade(nomeLocalidades));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewLocalidade);
        LocalidadeAdapter localidadeAdapter = new LocalidadeAdapter(this, localidades);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(localidadeAdapter);
    }
}