package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddComunidade extends AppCompatActivity {

    Button addComunidade;
    EditText comunidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comunidade);

        comunidade = (EditText) findViewById(R.id.idAddComunidade);
        addComunidade = (Button) findViewById(R.id.addComunidade);

        addComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(comunidade.getText().toString(), "comunidades");
                startActivity(new Intent(AddComunidade.this, ActivityViewComunidade.class));
            }
        });


    }
}