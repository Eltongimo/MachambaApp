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
import com.example.machambaapp.ui.admin.views.ActivityViewPostoAdmnistrativo;
import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddPosto extends AppCompatActivity {

    Button addPosto;
    EditText postoAdministrativo;

    ArrayAdapter<String> adapterLocalidade;
    AutoCompleteTextView autoLocalidade;

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posto);

        postoAdministrativo = (EditText) findViewById(R.id.idAddPostoAdministrativo);
        addPosto = (Button) findViewById(R.id.addPosto);

        autoLocalidade= (AutoCompleteTextView) findViewById(R.id.localidade);

        String [] localidadesArray = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);

        adapterLocalidade = new ArrayAdapter<>(this, R.layout.list_item_localidade, localidadesArray);
        autoLocalidade.setAdapter(adapterLocalidade);



        addPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(postoAdministrativo.getText().toString(),
                        "postosAdministrativos",autoLocalidade.getText().toString(),"localidade");
                finish();
            }
        });
    }
}