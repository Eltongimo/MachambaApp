package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.example.machambaapp.ui.admin.views.ActivityViewDistrito;

public class UpdateDistrito extends AppCompatActivity {

    Button update;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_distrito);

        update = (Button) findViewById(R.id.updateDistrito);
        nome = (EditText) findViewById(R.id.nomeDistrito);

        Intent i = getIntent();
        String distrito = i.getStringExtra("distrito");
        String key = i.getStringExtra("key");

        nome.setText(distrito);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateDistrito(nome.getText().toString(), key);
                finish();
                startActivity(new Intent(UpdateDistrito.this, ActivityViewDistrito.class));
            }
        });

    }
}