package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class ActivityUserRegister extends AppCompatActivity {
     Button addUser;

    String[] itemsDistrito = {"Monapo", "Moma", "Montepues"};
    String[] itemsPostoAdministrativo = {"p-Monapo", "p-Moma", "p-Montepues"};
    String[] itemsLocalidade = {"L-Monapo", "L-Moma", "L-Montepues"};
    String[] itemsComunidade = {"C-Monapo", "C-Moma", "C-Montepues"};
    AutoCompleteTextView autoCompleteDistrito;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

         addUser=(Button) findViewById(R.id.registerUser);


         addUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent intent=new Intent(ActivityUserRegister.this, ActivityChooseAction.class);
                 startActivity(intent);
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