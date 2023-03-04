package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.adapter.CulturaAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityViewAddCultura extends AppCompatActivity {

   Button addCultura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_add_cultura);

          addCultura=(Button) findViewById(R.id.addCultura);

        setAdapter();
          addCultura.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(ActivityViewAddCultura.this,AddCultura.class));
              }
          });



    }

    private void setAdapter(){
        DB db=new DB();
        RecyclerView recyclerView = findViewById(R.id.idViewCulturaR);
        CulturaAdapter culturaAdapter = new CulturaAdapter(this, db.getListCultura());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(culturaAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityViewAddCultura.this,MainActivity.class));

        super.onBackPressed();
    }
}