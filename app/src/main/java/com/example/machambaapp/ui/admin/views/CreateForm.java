package com.example.machambaapp.ui.admin.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.R;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.datamodel.Resposta;

public class CreateForm extends AppCompatActivity {

    private LinearLayout mLayout;

    private Formulario formulario = new Formulario();

    private boolean nameInserted = false;

    private boolean questionInserted = false;

    private boolean answersInserted = false;

    private Pergunta pergunta = new Pergunta();
    private Resposta resposta = new Resposta();
    private Button salvar ;

    // Group of possible answers to be choosen
    RadioGroup radioGroup;
    private boolean canShowFormName = true;

    private void showPossibleAnswers(){
        String[] options = {"Campo de Texto", "Checkbox", "Radio Button", "Imagem"};
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[i]);
            radioButton.setId(i); // Define um ID único para cada RadioButton
            radioGroup.addView(radioButton);
        }
        tipoRespostaInserido = true;
        answersInserted = true;
    }

    private void show(){
        if (canShowFormName){

        }
    }
    private EditText editText;
    private Button continuar;
    private boolean tipoRespostaInserido = false;
    private boolean mostrarPerguntaHint = true;

    private void showQuestion(){

        if (mostrarPerguntaHint){
            editText.setHint("Escreva a pergunta ");
            editText.setText("");
            mLayout.addView(editText);
            mostrarPerguntaHint = false;
        }
    }

    private void showAnswers(){
        if (mostrarPerguntaHint){
            editText.setHint("Escreva a pergunta ");
            editText.setText("");
            mLayout.addView(editText);
            mostrarPerguntaHint = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        mLayout = findViewById(R.id.linear_layout);
        salvar =  findViewById(R.id.salvar);

        radioGroup = findViewById(R.id.radio_group_id);

        editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        editText.setHint("Escreva o nome do formulario");
        mLayout.addView(editText);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(); // Just to display the Hint: Inserir a pergunta
            }
        });
    }
}

