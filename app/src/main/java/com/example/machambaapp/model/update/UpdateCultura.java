package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;

public class UpdateCultura extends AppCompatActivity {

    Button update;
    EditText nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cultura);

        Intent i = getIntent();
        String cultura = i.getStringExtra("cultura");
        String key = i.getStringExtra("key");

        update = (Button) findViewById(R.id.updateCultura);
        nome = (EditText) findViewById(R.id.nomeCultura);

        nome.setText(cultura);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateCultura(nome.getText().toString(),key);
                finish();

                startActivity(new Intent(UpdateCultura.this, ActivityViewAddCultura.class));
            }
        });
    }
}