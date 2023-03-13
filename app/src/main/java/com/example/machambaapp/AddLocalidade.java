package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddLocalidade extends AppCompatActivity {

    Button addLocalidade;
    EditText localidade;

    ArrayAdapter<String> adapterDistritos;
    AutoCompleteTextView autoDistritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_localidade);

        addLocalidade = (Button) findViewById(R.id.addLocalidade);
        localidade = (EditText) findViewById(R.id.nomeLocalidade);
        autoDistritos= (AutoCompleteTextView) findViewById(R.id.autoCompleteDistritos);

        String []distritosArray = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);

        adapterDistritos = new ArrayAdapter<String>(this, R.layout.activity_add_localidade, distritosArray);

        addLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(localidade.getText().toString(), "localidades");
                startActivity(new Intent(AddLocalidade.this, ActivityViewLocalidade.class));
            }
        });
    }
}