package com.example.machambaapp;

import static com.example.machambaapp.R.id.idfloat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.adapter.ClientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityListClient extends AppCompatActivity {
     FloatingActionButton floatingActionButton;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

          floatingActionButton=(FloatingActionButton) findViewById(R.id.idfloatCliente);
          recyclerView = findViewById(R.id.idRecyclerviewClient);


          recyclerView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                  Intent intent=new Intent(ActivityListClient.this, ActivitySelectCultura.class);
//                  startActivity(intent);
              }
          });

          floatingActionButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent(ActivityListClient.this, AddUserActivity.class);
                  startActivity(intent);
              }
          });


        setAdapter();
    }

    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewClient);
        ClientAdapter clientAdapter = new ClientAdapter(this, DB.getListClient());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(clientAdapter);
    }
}