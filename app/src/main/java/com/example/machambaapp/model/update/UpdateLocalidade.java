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
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.example.machambaapp.ui.admin.views.ActivityViewLocalidade;

public class UpdateLocalidade extends AppCompatActivity {
    Button update;
    EditText nome;
    ArrayAdapter<String> adapterDistritos;
    AutoCompleteTextView autoDistritos;

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_localidade);

        update = (Button) findViewById(R.id.updateLocalidade);
        nome = (EditText) findViewById(R.id.nomeLocalidade);
        autoDistritos= (AutoCompleteTextView) findViewById(R.id.distritos);

        String [] distritosArray = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);



        adapterDistritos = new ArrayAdapter<>(this, R.layout.list_item_distrito, distritosArray);
        autoDistritos.setAdapter(adapterDistritos);

        nome.setText(getIntent().getStringExtra("localidade"));
        autoDistritos.setHint(getIntent().getStringExtra("distrito"));


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.updateLocalidade(nome.getText().toString(), autoDistritos.getText().toString(), getIntent().getStringExtra("key"));
                finish();
                startActivity(new Intent(UpdateLocalidade.this, ActivityViewLocalidade.class));
            }
        });

    }
}