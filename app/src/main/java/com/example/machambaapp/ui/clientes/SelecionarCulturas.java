package com.example.machambaapp.ui.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.SelectCulturaAdapter;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SelecionarCulturas extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Cultura> culturas = new ArrayList<Cultura>();

    SearchView searchView;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_culturas);
        searchView = (SearchView) findViewById(R.id.searchCultura);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Culturas");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getCulturasFromFirebase();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getCulturasFromFirebase();
                }else{
                    ArrayList<Cultura> c = DatabaseHelper.getCulturas(newText);
                    culturas = c;
                    setAdapter();
                }
                return false;
            }
        });
    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idViewCulturaR);
        SelectCulturaAdapter selectAdapter = new SelectCulturaAdapter(this, culturas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(selectAdapter);
    }
    private void getCulturasFromFirebase(){
        databaseReference.child("culturas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                culturas.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String cultura = comunidadesSnap.child("nome").getValue(String.class);
                    String chave = comunidadesSnap.getKey().toString();
                    String imagem = comunidadesSnap.child("image").getValue(String.class);
                    culturas.add(new Cultura(cultura, chave,imagem));
                }
                Collections.sort(culturas, new Comparator<Cultura>() {
                    @Override
                    public int compare(Cultura c1, Cultura c2) {
                        return c1.getCultura().compareTo(c2.getCultura());
                    }
                });

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}