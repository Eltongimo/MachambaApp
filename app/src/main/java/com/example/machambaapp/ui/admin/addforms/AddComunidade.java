package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;

import java.util.concurrent.CountDownLatch;

public class AddComunidade extends AppCompatActivity {

    Button addComunidade;
    EditText comunidade;
    ProgressDialog loadingBar;
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

        loadingBar = new ProgressDialog(this);

        comunidade = (EditText) findViewById(R.id.idAddComunidade);
        addComunidade = (Button) findViewById(R.id.addComunidade);

        autoComunidade= (AutoCompleteTextView) findViewById(R.id.comunidades);

        String [] postosAdministrativos = SplashScreen.postosAdministrativos.toArray(new String[SplashScreen.postosAdministrativos.size()]);

        adapterLocalidades = new ArrayAdapter<>(this, R.layout.list_item_comunidade, postosAdministrativos);
        autoComunidade.setAdapter(adapterLocalidades);

        addComunidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadingBar.setTitle("Adicionando Comunidade");
                    loadingBar.setMessage("Aguarde por favor!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    DatabaseHelper.addLocations(comunidade.getText().toString(),
                            "comunidades",autoComunidade.getText().toString(),"postoAdministrativo");
                    loadingBar.dismiss();
                    finish();
                }catch (Exception e) {
                    System.out.println(" hj,fhjfmgfdm");
                }
            }
        });
    }
}