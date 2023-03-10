package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddLocalidade extends AppCompatActivity {

    Button addLocalidade;
    EditText localidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_localidade);

        addLocalidade = (Button) findViewById(R.id.addLocalidade);
        localidade = (EditText) findViewById(R.id.nomeLocalidade);

        addLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(localidade.getText().toString(), "localidades");
                startActivity(new Intent(AddLocalidade.this, ActivityViewLocalidade.class));
            }
        });
    }
}