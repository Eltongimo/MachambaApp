package com.example.machambaapp.ui.clientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.forms.ResponderForm;

import java.util.ArrayList;
import java.util.Calendar;

public class SelecionarCanteiroAlfobre extends AppCompatActivity {

    private CardView canteiro;
    private CardView cardAlfobre;
    public static String formKey = DatabaseHelper.getSha();
    public static ArrayList<OfflineDBModelForm> offlineDB = new ArrayList<>();

    public static String clientName = "";

    @Override
    protected void onResume(){
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        formKey = getIntent().getStringExtra("fullName") + hour + ":" + minute + ":" + second;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_canteiro_alfobre);

        canteiro = findViewById(R.id.cardCanteiro);

        canteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientName = getIntent().getStringExtra("fullName") + " ";
                startActivity(new Intent(SelecionarCanteiroAlfobre.this, ResponderForm.class));
            }
        });
    }
}