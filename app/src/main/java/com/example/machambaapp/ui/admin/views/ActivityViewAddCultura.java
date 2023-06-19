package com.example.machambaapp.ui.admin.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.machambaapp.ActivityUserRegister;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.addforms.AddCultura;
import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.CulturaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewAddCultura extends AppCompatActivity {

    Button addCultura;

    SearchView searchView;
    ProgressDialog loadingBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Cultura> culturas = new ArrayList<Cultura>();
    private void getCulturasFromFirebase(){
        databaseReference.child("culturas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                culturas.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String cultura = comunidadesSnap.child("cultura").getValue(String.class);
                    String chave = comunidadesSnap.getKey().toString();
                    String imagem = comunidadesSnap.child("imagem").getValue(String.class);

                    culturas.add(new Cultura(cultura, chave, imagem));
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
    protected void onResume(){
        super.onResume();
        getCulturasFromFirebase();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_add_cultura);
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Culturas");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getCulturasFromFirebase();

        addCultura=(Button) findViewById(R.id.addCultura);
        searchView = (SearchView) findViewById(R.id.searchCultura);

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


        addCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ActivityViewAddCultura.this, AddCultura.class));

                Intent intent = new Intent(ActivityViewAddCultura.this, AddCultura.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityViewAddCultura.this).toBundle();
                startActivity(intent, b);
            }
        });
    }

    private void setAdapter(){

        RecyclerView recyclerView = findViewById(R.id.idViewCulturaR);
        CulturaAdapter culturaAdapter = new CulturaAdapter(this, culturas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(culturaAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}