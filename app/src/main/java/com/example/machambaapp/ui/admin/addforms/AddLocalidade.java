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
import com.example.machambaapp.ui.admin.views.ActivityViewLocalidade;
import com.example.machambaapp.model.helper.DatabaseHelper;

public class AddLocalidade extends AppCompatActivity {

    Button addLocalidade;
    EditText localidade;

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
        setContentView(R.layout.activity_add_localidade);

        addLocalidade = (Button) findViewById(R.id.addLocalidade);
        localidade = (EditText) findViewById(R.id.nomeLocalidade);
        autoDistritos= (AutoCompleteTextView) findViewById(R.id.distritos);

        String [] distritosArray = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);

        adapterDistritos = new ArrayAdapter<>(this, R.layout.list_item_distrito, distritosArray);
        autoDistritos.setAdapter(adapterDistritos);

        addLocalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.addCultura(localidade.getText().toString(), "localidades",autoDistritos.getText().toString(),"distrito");
                finish();
            //    startActivity(new Intent(AddLocalidade.this, ActivityViewLocalidade.class));
            }
        });
    }
}