package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.ui.admin.addforms.AddUserActivity;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityListClient extends AppCompatActivity {
     FloatingActionButton floatingActionButton;
     Button buttonUserAdd;
    RecyclerView recyclerView ;
    private ArrayList<Cliente> client = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list_client);
         setAdapter();


          buttonUserAdd=(Button) findViewById(R.id.registerClientPl);
          recyclerView = findViewById(R.id.idRecyclerviewClient);


          recyclerView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                  Intent intent=new Intent(ActivityListClient.this, ActivitySelectCultura.class);
//                  startActivity(intent);
              }
          });

          buttonUserAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent(ActivityListClient.this, AddUserActivity.class);
                  startActivity(intent);
              }
          });
    }

    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewClient);
        ClientAdapter clientAdapter = new ClientAdapter(this, client);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(clientAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}