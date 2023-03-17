package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityChooseAction extends AppCompatActivity {

    CardView startVisit;
    CardView selectClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

          startVisit=(CardView) findViewById(R.id.idCardVisita);
          selectClient = (CardView) findViewById(R.id.idCardClientSelect);

           startVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityChooseAction.this, ActivitySelect.class);
                startActivity(intent);
            }
        });

        selectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(ActivityChooseAction.this, ActivityListClient.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(this, " voltou", Toast.LENGTH_SHORT).show();
    }
}