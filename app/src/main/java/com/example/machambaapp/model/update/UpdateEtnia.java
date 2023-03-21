package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

public class UpdateEtnia extends AppCompatActivity {

    Button update;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_etnia);

        update = (Button) findViewById(R.id.updateEtnia);
        name = (EditText) findViewById(R.id.nomeEtnia);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}