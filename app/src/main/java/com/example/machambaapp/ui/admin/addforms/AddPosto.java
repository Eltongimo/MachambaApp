package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Adicionar posto administrativo!");
                builder.setMessage("Deseja mesmo adicionar o posto administrativo?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.addLocations(postoAdministrativo.getText().toString(),
                                "postosAdministrativos",autoLocalidade.getText().toString(),"localidade");                        Toast.makeText(getApplicationContext(), "Localidade adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
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