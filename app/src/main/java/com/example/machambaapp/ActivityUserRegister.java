package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.model.DB;

public class ActivityUserRegister extends AppCompatActivity {
     Button addUser;

    String[] itemsDistrito = {"Mecuf"};
    String[] itemsPostoAdministrativo = {"Murrebue"};
    String[] itemsLocalidade = {"L-Muitua"};
    String[] itemsComunidade = {"Sicura B", "Muitua Sede", "Murripa","Singura A"};
    AutoCompleteTextView autoCompleteDistrito;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterItems;

    EditText editTextNome;
    EditText editTextApelido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        addUser=(Button) findViewById(R.id.registerUser);
        editTextNome=(EditText) findViewById(R.id.idNome);
        editTextApelido=(EditText) findViewById(R.id.idApelido);

        addUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                if(editTextApelido.getText().equals("") || editTextNome.getText().equals("")){
                    editTextNome.setError(" campo vazio");
                    editTextApelido.setError(" campo vazio");
                }else{
                    DB db =new DB();
                    db.addArrayListUserPl(editTextNome.getText().toString(),editTextApelido.getText().toString());
                    Intent intent=new Intent(ActivityUserRegister.this, ActivityUserPL.class);
                    startActivity(intent);
                }




             }
         });

        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.auto_select);
        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.idPostoAdministrativo);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.idLocalidade);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.idComunidade);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterItems);

       // autoCompleteTextView.setOnClickListener(s);





    }


}