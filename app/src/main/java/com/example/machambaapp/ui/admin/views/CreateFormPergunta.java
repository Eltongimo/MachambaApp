package com.example.machambaapp.ui.admin.views;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.ui.admin.addforms.AddEtnia;

public class CreateFormPergunta extends AppCompatActivity {

    private EditText pergunta;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form_pergunta);
        pergunta = (EditText) findViewById(R.id.nomePergunta);

        button = (Button) findViewById(R.id.addPergunta);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Before move to next page
                // save the inserted data
                String pp = "";

                pp = pergunta.getText().toString();

                if (pp.equals("")){
                    Toast.makeText(getApplicationContext(),"Por favor introduza uma pergunta ", Toast.LENGTH_LONG).show();
                    return;
                }
                Pergunta p = new Pergunta();

                 p.setNomeDaPergunta(pp);

                SplashScreen.formulario.getPerguntas().add(p);

//                startActivity(new Intent(CreateFormPergunta.this, CreateFormResposta.class));
                Intent intent = new Intent(CreateFormPergunta.this, CreateFormResposta.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(CreateFormPergunta.this).toBundle();
                startActivity(intent, b);
            }
        });
    }
}