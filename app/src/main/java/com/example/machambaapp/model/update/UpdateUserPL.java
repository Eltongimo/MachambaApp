package com.example.machambaapp.model.update;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.machambaapp.ui.admin.views.ActivityUserPL;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;

public class UpdateUserPL extends AppCompatActivity {

    Button update;
    String[] itemsDistrito = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);
    String[] itemsPostoAdministrativo = SplashScreen.postosAdministrativos.toArray(new String[SplashScreen.postosAdministrativos.size()]);
    String [] itemsLocalidade = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);;
    String [] itemsComunidade = SplashScreen.comunidades.toArray(new String[SplashScreen.comunidades.size()]);;
    AutoCompleteTextView autoCompleteDistrito;
    Dialog dialog;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterDistrito;
    ArrayAdapter<String> adapterLocalidade;
    ArrayAdapter<String> adapterComunidade;
    ArrayAdapter<String> adapterPosto;
    EditText editTextNome;
    EditText editSenha;
    EditText editPhone;
    static Uri urlImage;
    Button cancel;
    CardView galeria;
    CardView camera;
    ImageView imageViewUser;
    EditText editTextApelido;


    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    private void setAttributesFomPutExtra(){
        autoCompleteDistrito.setHint(getIntent().getStringExtra("distrito"));
        autoCompletePostoAdministrativo.setHint(getIntent().getStringExtra("posto"));
        autoCompleteLocalidade.setHint(getIntent().getStringExtra("localidade"));
        autoCompleteComunidade.setHint(getIntent().getStringExtra("comunidade"));
        editTextNome.setText(getIntent().getStringExtra("nome"));
        editSenha.setText(getIntent().getStringExtra("senha"));
        editPhone.setText(getIntent().getStringExtra("phone"));
        editTextApelido.setText(getIntent().getStringExtra("apelido"));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_userpl);

        update = (Button) findViewById(R.id.updateUserPL);
        editTextNome = (EditText) findViewById(R.id.nomePl) ;
        editSenha = (EditText) findViewById(R.id.passwordPl);
        editPhone = (EditText) findViewById(R.id.phonePl);
        editTextApelido = (EditText) findViewById(R.id.apelidoPl);

        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.distritoPL);
        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.postoadmnistrativoPL);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.localidadePl);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.comunidadePL);


        adapterDistrito = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterDistrito);

        adapterPosto = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterPosto);

        adapterLocalidade = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterLocalidade);

        adapterComunidade = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterComunidade);

        setAttributesFomPutExtra();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper.updateUserPl( new Cliente.UserPl(editTextNome.getText().toString(),
                                                        editTextApelido.getText().toString(),
                                                        editSenha.getText().toString(),"",
                                                        editPhone.getText().toString(),
                                                        null,
                                                        autoCompleteDistrito.getText().toString(),
                                                        autoCompleteLocalidade.getText().toString(),
                                                        autoCompletePostoAdministrativo.getText().toString(),
                                                        autoCompleteComunidade.getText().toString(),
                                                        getIntent().getStringExtra("key")), getIntent().getStringExtra("key"));
                finish();
                startActivity(new Intent(UpdateUserPL.this, ActivityUserPL.class));
            }
        });

    }
}