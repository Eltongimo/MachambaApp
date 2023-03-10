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
import com.example.machambaapp.model.adapter.PostoAdapter;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.datamodel.Posto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.machambaapp.model.datamodel.Distrito;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ActivityViewPostoAdmnistrativo extends AppCompatActivity {

    Button registerPosto;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Posto> postosAdministrativos = new ArrayList<Posto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posto_admnistrativo);

        getPostosAdministrativosFromFirebase();
        registerPosto = (Button) findViewById(R.id.registerPosto);
        registerPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewPostoAdmnistrativo.this, AddPosto.class));
            }
        });
    }
    private void getPostosAdministrativosFromFirebase(){
        databaseReference.child("postosAdministrativos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postosAdministrativos.clear();
                for (DataSnapshot postosAdministrativosSnap : snapshot.getChildren()) {
                    String nomePostosAdministrativos = postosAdministrativosSnap.child("nome").getValue(String.class);
                    postosAdministrativos.add(new Posto(nomePostosAdministrativos));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewPosto);
        PostoAdapter postoAdapter = new PostoAdapter(this, postosAdministrativos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postoAdapter);
    }
}