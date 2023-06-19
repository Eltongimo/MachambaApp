package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.machambaapp.ui.admin.addforms.AddEtnia;
import com.example.machambaapp.ui.admin.views.ActivityViewEtnia;

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
//                Intent  intent=new Intent(ActivityPageStart.this, ActivityLogin.class);
//                startActivity(intent);

                Intent intent = new Intent(ActivityPageStart.this, ActivityLogin.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ActivityPageStart.this).toBundle();
                startActivity(intent, b);
            }
        });
    }
}