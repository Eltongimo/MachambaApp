package com.example.machambaapp.ui.admin.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResponderForm extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private CardView idRespForm;
    private ProgressDialog loadingBar;
    private void getForm(){

        databaseReference.child("formularios").child("perguntas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Formulario f = new Formulario();
                ArrayList<Pergunta> perguntas = new ArrayList<>();

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    Pergunta p = userSnapshot.child("perguntas").getValue(Pergunta.class);
                    perguntas.add(p);
                }

                f.setPerguntas(perguntas);
                SplashScreen.formularioDeResposta = f;
                loadingBar.dismiss();
                idRespForm.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_form);
        idRespForm = (CardView) findViewById(R.id.idRespForm);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando Formulário");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getForm();

        startActivity(new Intent(getApplicationContext(), ResponderForm.class));

    }
}