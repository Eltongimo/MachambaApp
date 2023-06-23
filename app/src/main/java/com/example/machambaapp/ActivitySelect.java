package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.machambaapp.ui.admin.addforms.AddCultura;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
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
//                Intent intent=new Intent(ActivitySelect.this, ActivitySelectVisit.class);
//                startActivity(intent);

                Intent intent = new Intent(ActivitySelect.this, ActivitySelectVisit.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivitySelect.this).toBundle();
                startActivity(intent, b);
            }
        });

        onClickSelectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ActivitySelect.this, ActivitySelectClient.class);
//                startActivity(intent);

                Intent intent = new Intent(ActivitySelect.this, ActivitySelectClient.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivitySelect.this).toBundle();
                startActivity(intent, b);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}