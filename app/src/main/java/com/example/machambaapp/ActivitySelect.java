package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.machambaapp.ui.clientes.ActivitySelectClient;

public class ActivitySelect extends AppCompatActivity {
    CardView onClickVisitaLivre;
    CardView onClickSelectClient;

    CardView onClickFormRespondido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        onClickSelectClient =(CardView) findViewById(R.id.idCardClient);
        onClickVisitaLivre =(CardView) findViewById(R.id.idCardVisita);


        onClickVisitaLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySelect.this, ActivitySelectVisit.class);
                startActivity(intent);
            }
        });

        onClickSelectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySelect.this, ActivitySelectClient.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}