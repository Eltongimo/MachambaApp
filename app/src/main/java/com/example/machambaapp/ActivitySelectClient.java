package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.DataClassClient;
import com.example.machambaapp.model.adapter.ClientAdapter;

public class ActivitySelectClient extends AppCompatActivity {
    RecyclerView recyclerView ;
    EditText editName;
    ImageView imageFaceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);
        recyclerView = findViewById(R.id.idRecyclerviewClients2);



        setAdapter();

  }

    private void setAdapter(){
        ClientAdapter clientAdapter = new ClientAdapter(this, DB.getListClient());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(clientAdapter);
    }

}