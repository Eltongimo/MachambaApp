package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.ui.admin.views.ActivityViewComunidade;
import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddComunidade extends AppCompatActivity {

    Button addComunidade;
    EditText comunidade;
    ArrayAdapter<String> adapterLocalidades;
    AutoCompleteTextView autoComunidade;


    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comunidade);

        comunidade = (EditText) findViewById(R.id.idAddComunidade);
        addComunidade = (Button) findViewById(R.id.addComunidade);

        autoComunidade= (AutoCompleteTextView) findViewById(R.id.comunidades);

        String [] distritos = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);

        adapterLocalidades = new ArrayAdapter<>(this, R.layout.list_item_comunidade, distritos);
        autoComunidade.setAdapter(adapterLocalidades);

        addComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(comunidade.getText().toString(), "comunidades");
                finish();
                //      startActivity(new Intent(AddComunidade.this, ActivityViewComunidade.class));
            }
        });


    }
}