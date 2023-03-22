package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewDistrito;
import com.example.machambaapp.ui.admin.views.ActivityViewEtnia;

public class UpdateEtnia extends AppCompatActivity {

    Button update;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String et = getIntent().getStringExtra("etnia");
        String key = getIntent().getStringExtra("key");

        setContentView(R.layout.activity_update_etnia);

        update = (Button) findViewById(R.id.updateEtnia);
        name = (EditText) findViewById(R.id.nomeEtnia);

        name.setText(et);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateEtnia(name.getText().toString(), key);
                finish();
                startActivity(new Intent(UpdateEtnia.this, ActivityViewEtnia.class));
            }
        });
    }
}