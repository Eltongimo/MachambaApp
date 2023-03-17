package com.example.machambaapp.ui.admin.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.addforms.AddCultura;
import com.example.machambaapp.Cultura;
import com.example.machambaapp.MainActivity;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.CulturaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityViewAddCultura extends AppCompatActivity {

   Button addCultura;
   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Cultura> culturas = new ArrayList<Cultura>();

    private void getComunidadesFromFirebase(){
        databaseReference.child("culturas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                culturas.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String cultura = comunidadesSnap.child("nome").getValue(String.class);
                    culturas.add(new Cultura(cultura));
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_add_cultura);
        getComunidadesFromFirebase();

        addCultura=(Button) findViewById(R.id.addCultura);

          addCultura.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(ActivityViewAddCultura.this, AddCultura.class));
              }
          });
    }

    private void setAdapter(){

        RecyclerView recyclerView = findViewById(R.id.idViewCulturaR);
        CulturaAdapter culturaAdapter = new CulturaAdapter(this, culturas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(culturaAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}