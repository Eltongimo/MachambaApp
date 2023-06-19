package com.example.machambaapp.ui.admin.views;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Resposta;
import com.example.machambaapp.model.helper.DatabaseHelper;

public class CreateFormResposta extends AppCompatActivity {

    private Button addResposta;
    private RadioGroup radioGroup;
    private TextView salvarForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form_resposta);

        addResposta = (Button) findViewById(R.id.addResposta);
        radioGroup = (RadioGroup) findViewById(R.id.idRadioGroup);
        salvarForm = (TextView) findViewById(R.id.salvarForm);

        salvarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Salvar Formulário!");
                builder.setMessage("Deseja mesmo salvar este formulário?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (radioGroup.getCheckedRadioButtonId() != -1 ){
                            inserirResposta();
                        }else{
                            Toast.makeText(getApplicationContext(), "Por favor Introduza o tipo de Resposta", Toast.LENGTH_LONG).show();
                            return ;
                        }

                        DatabaseHelper.addForm(SplashScreen.formulario);

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        addResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (inserirResposta()){
//                    startActivity(new Intent(CreateFormResposta.this, CreateFormPergunta.class));
                    Intent intent = new Intent(CreateFormResposta.this, CreateFormPergunta.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation(CreateFormResposta.this).toBundle();
                    startActivity(intent, b);
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor selecione um tipo de resposta ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        private boolean inserirResposta(){
            Resposta r = new Resposta();
            String tipoResposta = "";

            switch(radioGroup.getCheckedRadioButtonId()){
                case R.id.checkboxRadio:
                    tipoResposta = "Checkbox";
                    break;
                case R.id.idRadio:
                    tipoResposta = "Radio";
                    break;
                case R.id.imagemRadio:
                    tipoResposta = "Image";
                    break;
                case R.id.editTextRadio:
                    tipoResposta = "EditText";
                    break;
            }

            if (radioGroup.getCheckedRadioButtonId() == -1){
                return false;
            }

            SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm++).setTipoPergunta(tipoResposta);
            SplashScreen.formulario.getRespostas().add(r);
            return true;
        }

}