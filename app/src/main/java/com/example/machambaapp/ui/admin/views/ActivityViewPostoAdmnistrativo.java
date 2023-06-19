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
import com.example.machambaapp.ui.admin.addforms.AddPosto;
import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.PostoAdapter;
import com.example.machambaapp.model.datamodel.Posto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityViewPostoAdmnistrativo extends AppCompatActivity {

    Button registerPosto;
    SearchView searchView;
    ProgressDialog loadingBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private static ArrayList<Posto> postosAdministrativos = new ArrayList<Posto>();

    @Override
    protected void onResume(){
        super.onResume();
        getPostosAdministrativosFromFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posto_admnistrativo);
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Postos Administrativos");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getPostosAdministrativosFromFirebase();
        registerPosto = (Button) findViewById(R.id.registerPosto);
        searchView = (SearchView) findViewById(R.id.idSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    getPostosAdministrativosFromFirebase();
                }else{
                    ArrayList<Posto> p = DatabaseHelper.getPostosAdministrativos(newText);
                    postosAdministrativos = p;
                    setAdapter();
                }
                return false;
            }
        });
        registerPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ActivityViewPostoAdmnistrativo.this, AddPosto.class));

                Intent intent = new Intent(ActivityViewPostoAdmnistrativo.this, AddPosto.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityViewPostoAdmnistrativo.this).toBundle();
                startActivity(intent, b);
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
                    String nomeLocalidade = postosAdministrativosSnap.child("localidade").getValue(String.class);
                    String chave = postosAdministrativosSnap.getKey().toString();

                    postosAdministrativos.add(new Posto(nomePostosAdministrativos, nomeLocalidade, chave));
                }
                Collections.sort(postosAdministrativos, new Comparator<Posto>() {
                    @Override
                    public int compare(Posto u1, Posto u2) {
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
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewPosto);
        PostoAdapter postoAdapter = new PostoAdapter(this, postosAdministrativos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(postoAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}