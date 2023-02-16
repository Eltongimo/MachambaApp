package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivitySelect extends AppCompatActivity {


    Button buttonVisitaLivre;
    Button buttonSelectClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        buttonSelectClient=(Button) findViewById(R.id.idBottonSelectClient);
        buttonVisitaLivre=(Button) findViewById(R.id.idButtonVisitaLivre);


        buttonVisitaLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySelect.this, ActivitySelectVisit.class);
                startActivity(intent);
            }
        });

        buttonSelectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySelect.this, ActivitySelectClient.class);
                startActivity(intent);
            }
        });


    }
}