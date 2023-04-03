package com.example.machambaapp.ui.admin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.datamodel.Resposta;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CriarFormularios extends AppCompatActivity {
    private LinearLayout mLayout;

    private Formulario formulario = new Formulario();

    private boolean nameInserted = false;

    private boolean questionInserted = false;

    private boolean answersInserted = false;

    private Pergunta pergunta = new Pergunta();
    private Resposta resposta = new Resposta();

    // Group of possible answers to be choosen
    RadioGroup radioGroup;

    private void showPossibleAnswers(){
        String[] options = {"Campo de Texto", "Checkbox", "Radio Button", "Imagem"};
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[i]);
            radioButton.setId(i); // Define um ID único para cada RadioButton
            radioGroup.addView(radioButton);
        }
    }

    private Button salvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_formularios);

        mLayout = findViewById(R.id.linear_layout);
        salvar =  findViewById(R.id.salvar);

        radioGroup = findViewById(R.id.radio_group_id);

        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        editText.setHint("Escreva o nome do formulario");
        mLayout.addView(editText);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nameInserted){
                    formulario.setNome(editText.getText().toString());
                    nameInserted = true;
                    editText.setText("");
                }

                editText.setHint("Por favor insira a pergunta");

                if (questionInserted){
                    questionInserted = false;
                    pergunta.setNomeDaPergunta(editText.getText().toString());
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                    showPossibleAnswers();
                }

                if (nameInserted && !questionInserted){

                    int id = radioGroup.getCheckedRadioButtonId();
                    String tipoResposta = "";

                    switch (id) {
                        case 0: tipoResposta = "Campo de Texto";
                        case 1: tipoResposta = "Checkbox";
                        case 2: tipoResposta = "Radio Button";
                        case 3: tipoResposta = "Imagem";
                    }

                    pergunta.setTipoPergunta(tipoResposta);
                }

                if (answersInserted){

                }

                if (nameInserted){
                    questionInserted = true;
                }
            }
        });
    }
}