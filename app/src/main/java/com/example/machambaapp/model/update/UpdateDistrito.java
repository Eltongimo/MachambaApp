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
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.example.machambaapp.ui.admin.views.ActivityViewDistrito;

public class UpdateDistrito extends AppCompatActivity {

    Button update;
    EditText nome;
    AutoCompleteTextView autoProvincias;
    ArrayAdapter<String> adapterProvincias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_distrito);

        update = (Button) findViewById(R.id.updateDistrito);
        nome = (EditText) findViewById(R.id.nomeDistrito);
        autoProvincias = (AutoCompleteTextView) findViewById(R.id.idProvincias);

        String [] provinciasArray = SplashScreen.provincias.toArray(new String[SplashScreen.provincias.size()]);

        adapterProvincias = new ArrayAdapter<>(this, R.layout.list_item_distrito, provinciasArray);
        autoProvincias.setAdapter(adapterProvincias);

//        Intent i = getIntent();
//        String distrito = i.getStringExtra("distrito");
//        String key = i.getStringExtra("key");
        nome.setText(getIntent().getStringExtra("distrito"));
        autoProvincias.setHint(getIntent().getStringExtra("provincia"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateDistrito(nome.getText().toString(), autoProvincias.getText().toString(), getIntent().getStringExtra("key"));
                finish();
                startActivity(new Intent(UpdateDistrito.this, ActivityViewDistrito.class));
            }
        });

    }
}