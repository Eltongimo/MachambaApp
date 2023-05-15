package com.example.machambaapp.ui.clientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.machambaapp.ActivityPageStart;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.ui.admin.forms.ResponderForm;

public class SelecionarCanteiroAlfobre extends AppCompatActivity {

    private CardView canteiro;
    private CardView cardAlfobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_canteiro_alfobre);
        canteiro = findViewById(R.id.cardCanteiro);

        canteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelecionarCanteiroAlfobre.this, ResponderForm.class));
            }
        });
    }
}