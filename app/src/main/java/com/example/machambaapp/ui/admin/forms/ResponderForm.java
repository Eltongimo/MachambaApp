package com.example.machambaapp.ui.admin.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView txtPergunta;
    Button btnResponder;
    LinearLayout container;

    EditText editText;
    CheckBox checkBox;
    RadioGroup radioGroup;
    DatePicker datePicker;
    NumberPicker numberPicker;
    RadioButton radioButton1, radioButton2, radioButton3,radioButton4;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_responder_form);

        container = findViewById(R.id.container);
        txtPergunta = findViewById(R.id.nomePergunta);
        btnResponder = findViewById(R.id.btnNext);

        if (SplashScreen.indexForm < SplashScreen.formulario.getPerguntas().size()) {

            ArrayList<Pergunta> perguntas = SplashScreen.formulario.getPerguntas();

            Pergunta p = perguntas.get(SplashScreen.indexForm);

            txtPergunta.setText(p.getNomeDaPergunta());

            mostrarCampo(p.getTipoPergunta());

            btnResponder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SplashScreen.indexForm++;
                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Formulario ja chegou ao fim, Parabens!", Toast.LENGTH_LONG).show();
            btnResponder.setText("Submeter Formulario");
            btnResponder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Submetendo o Formulario", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SplashScreen.indexForm--;
        finish();
    }

    private void mostrarCampo(String tipoResposta){


        switch (tipoResposta){
            case "EditText":
                editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                container.addView(editText);

                break;
            case "CheckBox": checkBox = new CheckBox(this);
                             checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                             int numberOfCheckBoxes = 5; // Quantidade desejada de CheckBoxes

                            for (int i = 0; i < numberOfCheckBoxes; i++) {
                                CheckBox checkBox = new CheckBox(this);
                                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                checkBox.setText("CheckBox " + (i+1));
                                container.addView(checkBox);
                            }
                break;
            case "ImageView":imageView = new ImageView(getApplicationContext());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
                                layoutParams.setMargins(300, 250, 16, 16);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setImageResource(R.drawable.baseline_photo_camera_24);
                                container.addView(imageView);
                break;
            case "RadioGroup":radioGroup = new RadioGroup(getApplicationContext());
                                radioButton1 = new RadioButton(getApplicationContext());
                                radioButton1.setText("Opção 1");

                                radioButton2 = new RadioButton(getApplicationContext());
                                radioButton2.setText("Opção 2");

                                radioButton3 = new RadioButton(getApplicationContext());
                                radioButton3.setText("Opção 3");

                                radioButton4 = new RadioButton(getApplicationContext());
                                radioButton4.setText("Opção 4");

                                radioGroup.addView(radioButton1);
                                radioGroup.addView(radioButton2);
                                radioGroup.addView(radioButton3);
                                radioGroup.addView(radioButton4);

                                container.addView(radioGroup);
                break;

            case "DatePicker":datePicker = new DatePicker(getApplicationContext());
                              container.addView(datePicker);
                break;
            case "NumberPicker": numberPicker = new NumberPicker(getApplicationContext());
                                 numberPicker.setMinValue(0);
                                 numberPicker.setMaxValue(1000);
                                 container.addView(numberPicker);

                break;

        }

    }

}
