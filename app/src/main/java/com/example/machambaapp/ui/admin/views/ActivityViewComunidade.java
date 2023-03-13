package com.example.machambaapp.ui.admin.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.ui.admin.addforms.AddComunidade;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.ComunidadeAdapter;
import com.example.machambaapp.model.datamodel.Comunidade;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityViewComunidade extends AppCompatActivity {

    Button addComunidade;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Comunidade> comunidades = new ArrayList<Comunidade>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comunidade);
        getComunidadesFromFirebase();
        addComunidade = (Button) findViewById(R.id.addComunidade);
        addComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewComunidade.this, AddComunidade.class));
            }
        });
    }
    private void getComunidadesFromFirebase(){
        databaseReference.child("comunidades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comunidades.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String nomecomunidades = comunidadesSnap.child("nome").getValue(String.class);
                    comunidades.add(new Comunidade(nomecomunidades));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewComunidade);
        ComunidadeAdapter comunidadeAdapter = new ComunidadeAdapter(this, comunidades);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(comunidadeAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}