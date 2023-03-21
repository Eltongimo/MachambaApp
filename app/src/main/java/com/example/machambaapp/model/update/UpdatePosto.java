package com.example.machambaapp.model.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;

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


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addLocations(nome.getText().toString(),
                        "postosAdministrativos",autoLocalidade.getText().toString(),"localidade");
                finish();
            }
        });


    }
}