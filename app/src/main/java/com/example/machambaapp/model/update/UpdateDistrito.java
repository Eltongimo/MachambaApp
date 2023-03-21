package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

public class UpdateDistrito extends AppCompatActivity {

    Button update;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_distrito);

        update = (Button) findViewById(R.id.updateDistrito);
        nome = (EditText) findViewById(R.id.nomeDistrito);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}