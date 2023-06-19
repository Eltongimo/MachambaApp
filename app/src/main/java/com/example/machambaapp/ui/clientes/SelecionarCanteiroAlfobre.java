package com.example.machambaapp.ui.clientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.forms.ResponderForm;

import java.util.ArrayList;

public class SelecionarCanteiroAlfobre extends AppCompatActivity {

    private CardView canteiro;
    private CardView cardAlfobre;
    public static String formKey = DatabaseHelper.getSha();
    public static ArrayList<OfflineDBModelForm> offlineDB = new ArrayList<>();

    public static String clientName = "";

    @Override
    protected void onResume(){
        super.onResume();
        formKey = DatabaseHelper.getSha();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_canteiro_alfobre);

        canteiro = findViewById(R.id.cardCanteiro);

        canteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientName = getIntent().getStringExtra("fullName");
                startActivity(new Intent(SelecionarCanteiroAlfobre.this, ResponderForm.class));
            }
        });
    }
}