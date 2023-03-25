package com.example.machambaapp.ui.admin.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.helper.DatabaseHelper;
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
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewDistrito extends AppCompatActivity {

    Button registarDistrito;
    SearchView searchView;
    ProgressDialog loadingBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Distrito> distritos = new ArrayList<Distrito>();

    @Override
    protected void onResume(){
        super.onResume();
        getDistritosFromFirebase();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_distrito);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Distritos");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getDistritosFromFirebase();
        registarDistrito=(Button) findViewById(R.id.registarDistrito);
        searchView = (SearchView) findViewById(R.id.searchDistritos);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getDistritosFromFirebase();
                }else{
                    ArrayList<Distrito> d = DatabaseHelper.getDistritos(newText);
                    distritos = d;
                    setAdapter();
                }
                return false;
            }
        });


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
                        String chave = DistritosSnap.getKey().toString();
                        distritos.add(new Distrito(nomeDistrito, chave));
                    }
                    Collections.sort(distritos, new Comparator<Distrito>() {
                        @Override
                        public int compare(Distrito u1, Distrito u2) {
                            return u1.getNome().compareTo(u2.getNome());
                        }
                    });

                    setAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        private void setAdapter(){
            RecyclerView recyclerView = findViewById(R.id.idRecyclerviewClient);
            DistritoAdapter DistritoAdapter = new DistritoAdapter(this, distritos);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            loadingBar.dismiss();
            recyclerView.setAdapter(DistritoAdapter);
        }
    }
