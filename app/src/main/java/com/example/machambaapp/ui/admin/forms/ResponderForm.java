package com.example.machambaapp.ui.admin.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResponderForm extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    private CardView idRespForm;
    private ProgressDialog loadingBar;
    private EditText edtResposta;
    private CheckBox checkBox;
    private RadioGroup radioGroup;
    private ImageView imgResposta;
    ArrayList<Pergunta> perguntas = new ArrayList<>();
    TextView txtPergunta;
    Button btnResponder;

    private int perguntaAtual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_form);

        btnResponder = findViewById(R.id.btnNext);
        txtPergunta = findViewById(R.id.txtPergunta);
        edtResposta = findViewById(R.id.edtResposta);
        checkBox = findViewById(R.id.checkBox);
        radioGroup = findViewById(R.id.radioGroup);
        imgResposta = findViewById(R.id.imgResposta);

        ArrayList<Pergunta> perguntas = SplashScreen.formulario.getPerguntas();
        Pergunta p = perguntas.get(SplashScreen.indexForm);

        // Exibir a pergunta na tela
        txtPergunta.setText(p.getNomeDaPergunta());

        // Obter a resposta do usuário com base no tipo de pergunta

        System.out.println(p.getNomeDaPergunta());

        switch (p.getTipoPergunta()) {
            case "EditText":
                edtResposta.setVisibility(View.VISIBLE);


//            case "Radio":
//                    RadioGroup rgRespostas = findViewById(R.id.rgRespostas);
//                    int opcaoSelecionadaId = rgRespostas.getCheckedRadioButtonId();
//                    if (opcaoSelecionadaId != -1) {
//                        RadioButton rbOpcaoSelecionada = findViewById(opcaoSelecionadaId);
//                        rgRespostas.clearCheck();
//                    }
//                    break;
               /* case "Checkbox":
                    LinearLayout llRespostas = findViewById(R.id.llRespostas);
                    for (int i = 0; i < llRespostas.getChildCount(); i++) {
                        View view = llRespostas.getChildAt(i);
                        if (view instanceof CheckBox) {
                            CheckBox cbResposta = (CheckBox) view;
                            if (cbResposta.isChecked()) {
                                resposta += cbResposta.getText().toString() + ", ";
                                cbResposta.setChecked(false);
                            }
                        }*/
                   // }

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}}
