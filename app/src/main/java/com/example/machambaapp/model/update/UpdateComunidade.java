package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewComunidade;
import com.example.machambaapp.ui.admin.views.ActivityViewEtnia;

public class UpdateComunidade extends AppCompatActivity {

    Button update;
    EditText nome;

    ArrayAdapter<String> adapterDistritos;
    AutoCompleteTextView postoAdmnistrativo;

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comunidade);

        update = (Button) findViewById(R.id.updateLocalidade);
        nome = (EditText) findViewById(R.id.nomeLocalidade);
        postoAdmnistrativo = (AutoCompleteTextView) findViewById(R.id.nomePosto);

        String [] distritosArray = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);

        nome.setText(getIntent().getStringExtra("comunidade"));
        postoAdmnistrativo.setHint(getIntent().getStringExtra("posto"));

        adapterDistritos = new ArrayAdapter<>(this, R.layout.list_item_distrito, distritosArray);
        postoAdmnistrativo.setAdapter(adapterDistritos);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateComunidade(nome.getText().toString(), postoAdmnistrativo.getText().toString(), getIntent().getStringExtra("key"));
                finish();
                startActivity(new Intent(UpdateComunidade.this, ActivityViewComunidade.class));
            }
        });
    }
}