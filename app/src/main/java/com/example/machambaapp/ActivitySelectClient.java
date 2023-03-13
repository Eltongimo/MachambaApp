package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.helper.DatabaseHelper;

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
        ClientAdapter clientAdapter = new ClientAdapter(this, DatabaseHelper.getUsersPL());
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