package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Adicionar Comunidade!");
                builder.setMessage("Deseja mesmo adicionar a comunidade?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.addLocations(comunidade.getText().toString(),
                                "comunidades",autoComunidade.getText().toString(),"postoAdministrativo");
                        finish();
                        Toast.makeText(getApplicationContext(), "Adicionado com sucesso!!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}