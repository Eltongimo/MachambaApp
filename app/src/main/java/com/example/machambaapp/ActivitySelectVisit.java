package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivitySelectVisit extends AppCompatActivity {

    Button  buttonSaveNomeVisita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_visit);

        buttonSaveNomeVisita=(Button) findViewById(R.id.idSelectVisita);

        buttonSaveNomeVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySelectVisit.this, ActivitySelectCultura.class);
                startActivity(intent);
            } 
        });


    }
}