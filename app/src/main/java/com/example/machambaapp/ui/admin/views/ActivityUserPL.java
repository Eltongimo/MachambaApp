package com.example.machambaapp.ui.admin.views;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.ActivityUserRegister;
import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.adapter.UserPlAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.CollectionOperations;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

public class ActivityUserPL extends AppCompatActivity {
    Button buttonRegister;
    SearchView searchView;
    ProgressDialog loadingBar;
    private ArrayList<Cliente.UserPl> userPls = new ArrayList<>();

    private void getDataFromDatabase(){

        DatabaseHelper.databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userPls.clear();

                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {
                    String nome = userPLSnapshot.child("nome").getValue(String.class);
                    String apelido= userPLSnapshot.child("apelido").getValue(String.class);
                    String comunidade = userPLSnapshot.child("comunidade").getValue(String.class);
                    String phone = userPLSnapshot.child("phone").getValue(String.class);
                    String distrito = userPLSnapshot.child("distrito").getValue(String.class);
                    String localidade = userPLSnapshot.child("localidade").getValue(String.class);
                    String postoAdministrativo = userPLSnapshot.child("postoAdministrativo").getValue(String.class);
                    String genero = userPLSnapshot.child("genero").getValue(String.class);
                    String senha = userPLSnapshot.child("senha").getValue(String.class);
                    Uri uriImage = userPLSnapshot.child("imagens").getValue(Uri.class);

                    userPls.add(new Cliente.UserPl(nome, apelido, senha, genero, phone, uriImage, distrito, localidade, postoAdministrativo, comunidade, userPLSnapshot.getKey()));
                }
                Collections.sort(userPls, new Comparator<Cliente.UserPl>() {
                    @Override
                    public int compare(Cliente.UserPl c1, Cliente.UserPl c2) {
                        return c1.getNome().compareTo(c2.getNome());
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_pl);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Usuarios");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getDataFromDatabase();

        buttonRegister =(Button) findViewById(R.id.registerClientPl);
        searchView = (SearchView) findViewById(R.id.searchUserPL);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("") || newText == null){
                    userPls = DatabaseHelper.getUsers();
                }else{
                    ArrayList<Cliente.UserPl> users = DatabaseHelper.getUsers(newText);
                   userPls = users;
                }
                setAdapter();
                return false;
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            /*Should provide a List of PL and his clients*/
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserPL.this, ActivityUserRegister.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        getDataFromDatabase();
    }
    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewUserPl);
        UserPlAdapter userPlAdapter = new UserPlAdapter(this, userPls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(userPlAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
  }
}