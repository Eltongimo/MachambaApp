package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddPosto extends AppCompatActivity {

    Button addPosto;
    EditText postoAdministrativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posto);

        postoAdministrativo = (EditText) findViewById(R.id.idAddPostoAdministrativo);
        addPosto = (Button) findViewById(R.id.addPosto);

        addPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(postoAdministrativo.getText().toString(), "postosAdministrativos");
                startActivity(new Intent(AddPosto.this, ActivityViewPostoAdmnistrativo.class));
            }
        });
    }
}