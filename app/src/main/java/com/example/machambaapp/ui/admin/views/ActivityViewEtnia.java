package com.example.machambaapp.ui.admin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.addforms.AddEtnia;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.EtniaAdapter;
import com.example.machambaapp.model.datamodel.Etnia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewEtnia extends AppCompatActivity {
    Button registerEtnia;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    SearchView searchView;
    ProgressDialog loadingBar;
    private static ArrayList<Etnia> etnias = new ArrayList<Etnia>();

    @Override
    protected void onResume(){
        super.onResume();
        getEtniasFromFirebase();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Etnias");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getEtniasFromFirebase();
        setContentView(R.layout.activity_view_etnia);
        registerEtnia = (Button) findViewById(R.id.registerEtnia);
        searchView = (SearchView) findViewById(R.id.idSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getEtniasFromFirebase();
                }else{
                    ArrayList<Etnia> e = DatabaseHelper.getEtnias(newText);
                    etnias = e;
                    setAdapter();
                }
                return false;
            }
        });

        registerEtnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewEtnia.this, AddEtnia.class));

            }
        });
    }
    private void getEtniasFromFirebase(){
        databaseReference.child("etnias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                etnias.clear();
                for (DataSnapshot etniasSnap : snapshot.getChildren()) {
                    String nomeEtnias = etniasSnap.child("nome").getValue(String.class);
                    String chave = etniasSnap.getKey().toString();
                    etnias.add(new Etnia(nomeEtnias, chave));
                }
                Collections.sort(etnias, new Comparator<Etnia>() {
                    @Override
                    public int compare(Etnia u1, Etnia u2) {
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
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewEtnia);
        EtniaAdapter etniaAdapter = new EtniaAdapter(this, etnias);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(etniaAdapter);
    }

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }
}