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
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Adicionar Localidade!");
                builder.setMessage("Deseja mesmo adicionar a localidade?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper.addLocations(localidade.getText().toString(), "localidades",autoDistritos.getText().toString(),"distrito");
                        Toast.makeText(getApplicationContext(), "Localidade adicionada com sucesso!", Toast.LENGTH_SHORT).show();
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