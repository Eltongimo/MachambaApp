package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.adapter.UserPlAdapter;

public class ActivityUserPL extends AppCompatActivity {
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_pl);
        setAdapter();


        buttonRegister =(Button) findViewById(R.id.registerClientPl);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserPL.this, ActivityUserRegister.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewUserPl);
        UserPlAdapter userPlAdapter = new UserPlAdapter(this, DB.getListUsePl());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userPlAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent( ActivityUserPL.this,MainActivity.class));

        super.onBackPressed();
  }
}