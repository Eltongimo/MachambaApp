package com.example.machambaapp.ui.admin.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.ui.admin.addforms.AddDistrito;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.DistritoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.machambaapp.model.datamodel.Distrito;

import java.util.ArrayList;

public class ActivityViewDistrito extends AppCompatActivity {

    Button registarDistrito;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Distrito> distritos = new ArrayList<Distrito>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_distrito);

        getDistritosFromFirebase();
        registarDistrito=(Button) findViewById(R.id.registarDistrito);

        registarDistrito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ActivityViewDistrito.this, AddDistrito.class));
                }
            });
        }

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    private void getDistritosFromFirebase(){
            databaseReference.child("distritos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    distritos.clear();
                    for (DataSnapshot DistritosSnap : snapshot.getChildren()) {
                        String nomeDistrito = DistritosSnap.child("nome").getValue(String.class);
                        distritos.add(new Distrito(nomeDistrito));
                    }
                    setAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        private void setAdapter(){

            getDistritosFromFirebase();
            RecyclerView recyclerView = findViewById(R.id.idRecyclerviewClient);
            DistritoAdapter DistritoAdapter = new DistritoAdapter(this, distritos);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(DistritoAdapter);
        }
    }
