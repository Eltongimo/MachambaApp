package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

public class UpdateCultura extends AppCompatActivity {

    Button update;
    EditText nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cultura);

        update = (Button) findViewById(R.id.updateCultura);
        nome = (EditText) findViewById(R.id.nomeCultura);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}