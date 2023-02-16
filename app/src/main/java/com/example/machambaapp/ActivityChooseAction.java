package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityChooseAction extends AppCompatActivity {


     Button startVisit;
      Button buttonViewClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        startVisit=(Button) findViewById(R.id.IniciarVisita);
        buttonViewClients = (Button) findViewById(R.id.viewClientes);

        startVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityChooseAction.this, ActivitySelect.class);
                startActivity(intent);
            }
        });

        buttonViewClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(ActivityChooseAction.this, ActivityListClient.class);
                startActivity(intent);
            }
        });
    }
}