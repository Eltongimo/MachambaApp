package com.example.machambaapp.model.update;

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
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewLocalidade;
import com.example.machambaapp.ui.admin.views.ActivityViewPostoAdmnistrativo;

public class UpdatePosto extends AppCompatActivity {

    Button update;
    EditText nome;

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
        setContentView(R.layout.activity_update_posto);

        nome = (EditText) findViewById(R.id.idAddPostoAdministrativo);
        update = (Button) findViewById(R.id.updatePosto);

        autoLocalidade= (AutoCompleteTextView) findViewById(R.id.localidade);

        String [] localidadesArray = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);

        adapterLocalidade = new ArrayAdapter<>(this, R.layout.list_item_localidade, localidadesArray);
        autoLocalidade.setAdapter(adapterLocalidade);

        nome.setText(getIntent().getStringExtra("posto"));
        autoLocalidade.setHint(getIntent().getStringExtra("localidade"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updatePosto(nome.getText().toString(), autoLocalidade.getText().toString(), getIntent().getStringExtra("key"));
                finish();
                startActivity(new Intent(UpdatePosto.this, ActivityViewPostoAdmnistrativo.class));
            }
        });


    }
}