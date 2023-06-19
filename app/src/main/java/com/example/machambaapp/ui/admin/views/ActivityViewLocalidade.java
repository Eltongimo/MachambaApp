package com.example.machambaapp.ui.admin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
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
import com.example.machambaapp.ui.admin.addforms.AddLocalidade;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.LocalidadeAdapter;
import com.example.machambaapp.model.datamodel.Localidade;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewLocalidade extends AppCompatActivity {

    Button registerLocalidade;
    SearchView searchView;
    ProgressDialog loadingBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Localidade> localidades = new ArrayList<Localidade>();


    @Override
    protected void onResume(){
        super.onResume();
        getLocalidadesFromFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_localidade);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Localidades");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getLocalidadesFromFirebase();
        registerLocalidade = (Button) findViewById(R.id.registerLocalidade);
        searchView = (SearchView) findViewById(R.id.idSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getLocalidadesFromFirebase();
                }else{
                    ArrayList<Localidade> d = DatabaseHelper.getLocalidades(newText);
                    localidades = d;
                    setAdapter();
                }
                return false;
            }
        });



        registerLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ActivityViewLocalidade.this, AddLocalidade.class));

                Intent intent = new Intent(ActivityViewLocalidade.this, AddLocalidade.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityViewLocalidade.this).toBundle();
                startActivity(intent, b);
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
                    String nomeDistrito = localidadesSnap.child("distrito").getValue(String.class);
                    String chave = localidadesSnap.getKey().toString();
                    localidades.add(new Localidade(nomeLocalidades, nomeDistrito, chave));
                }
                Collections.sort(localidades, new Comparator<Localidade>() {
                    @Override
                    public int compare(Localidade u1, Localidade u2) {
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
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewLocalidade);
        LocalidadeAdapter localidadeAdapter = new LocalidadeAdapter(this, localidades);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(localidadeAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}