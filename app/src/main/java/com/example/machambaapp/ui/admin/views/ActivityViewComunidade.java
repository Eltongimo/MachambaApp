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

import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.helper.DatabaseHelper;
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
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewComunidade extends AppCompatActivity {

    Button addComunidade;
    SearchView searchView;
    ProgressDialog loadingBar;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Comunidade> comunidades = new ArrayList<Comunidade>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comunidade);
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Comunidades");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getComunidadesFromFirebase();
        addComunidade = (Button) findViewById(R.id.addComunidade);
        searchView = (SearchView) findViewById(R.id.idSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getComunidadesFromFirebase();
                }else{
                    ArrayList<Comunidade> c = DatabaseHelper.getComunidades(newText);
                    comunidades = c;
                    setAdapter();
                }
                return false;
            }
        });

        addComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewComunidade.this, AddComunidade.class));
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        getComunidadesFromFirebase();
    }
    private void getComunidadesFromFirebase(){
        databaseReference.child("comunidades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comunidades.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String nomecomunidades = comunidadesSnap.child("nome").getValue(String.class);
                    String nomePosto = comunidadesSnap.child("postoAdministrativo").getValue(String.class);
                    String chave = comunidadesSnap.getKey().toString();
                    comunidades.add(new Comunidade(nomecomunidades, nomePosto, chave));
                }
                Collections.sort(comunidades, new Comparator<Comunidade>() {
                    @Override
                    public int compare(Comunidade u1, Comunidade u2) {
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
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewComunidade);
        ComunidadeAdapter comunidadeAdapter = new ComunidadeAdapter(this, comunidades);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(comunidadeAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}