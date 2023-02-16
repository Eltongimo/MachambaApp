package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPageStart extends AppCompatActivity {

    private Button buttonStart;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_start);

        buttonStart =(Button) findViewById(R.id.button_start);




        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(ActivityPageStart.this, ActivityLogin.class);
                startActivity(intent);
            }
        });


    }
}