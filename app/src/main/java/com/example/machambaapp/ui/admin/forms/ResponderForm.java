package com.example.machambaapp.ui.admin.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
    private FusedLocationProviderClient mFusedLocationClient;


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
                        130));
                editText.setPadding(16, 0, 16, 0);
                editText.setHint("Digite aqui...");
                editText.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                editText.setTypeface(ResourcesCompat.getFont(this, R.font.poppinsregular));
                editText.setBackground(getResources().getDrawable(R.drawable.button_sape));
                container.addView(editText);

                break;
            case "CheckBox1":{ checkBox = new CheckBox(this);
                             checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                             int numberOfCheckBoxes = 5; // Quantidade desejada de CheckBoxes

                            ArrayList<String> culturas = new ArrayList<>();
                            culturas.add("Tomate");
                            culturas.add("Couve");
                            culturas.add("Cebola");

                            for (String c : culturas) {
                                CheckBox checkBox = new CheckBox(this);
                                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                checkBox.setText(c);
                                container.addView(checkBox);
                            }
                    }
                break;

                case "CheckBox2": checkBox = new CheckBox(this);
                 checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                 int numberOfCheckBoxes = 5; // Quantidade desejada de CheckBoxes

                ArrayList<String> culturas = new ArrayList<>();
                culturas.add("Camada de Estrume");
                culturas.add("Camada de Cobertura Morta");
                culturas.add("Nenhuma");

                for (String c : culturas) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(c);
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
            case "RadioGroup":  {radioGroup = new RadioGroup(getApplicationContext());
                                radioButton1 = new RadioButton(getApplicationContext());
                                radioButton1.setText("Masculino");

                                radioButton2 = new RadioButton(getApplicationContext());
                                radioButton2.setText("Femenino");

                                radioGroup.addView(radioButton1);
                                radioGroup.addView(radioButton2);

                                container.addView(radioGroup);}
                break;
            case "RadioGroup1":  {radioGroup = new RadioGroup(getApplicationContext());
                                radioButton1 = new RadioButton(getApplicationContext());
                                radioButton1.setText("Nao tem humidade");

                                radioButton2 = new RadioButton(getApplicationContext());
                                radioButton2.setText("tem mas o bolo nao fica bem firme");

                                radioButton3 = new RadioButton(getApplicationContext());
                                radioButton3.setText("O bolo fica bem firme, a humidade é boa!");


                                radioButton4 = new RadioButton(getApplicationContext());
                                radioButton4.setText("Esta a escorrer agua, esta molhado demais!");

                                radioGroup.addView(radioButton1);
                                radioGroup.addView(radioButton2);

                                container.addView(radioGroup);}
                break;
            case "RadioGroup2": radioGroup = new RadioGroup(getApplicationContext());
                                radioButton1 = new RadioButton(getApplicationContext());
                                radioButton1.setText("Sim");

                                radioButton2 = new RadioButton(getApplicationContext());
                                radioButton2.setText("Não");

                                radioGroup.addView(radioButton1);
                                radioGroup.addView(radioButton2);

                                container.addView(radioGroup);
                break;

            case "DatePicker":datePicker = new DatePicker(getApplicationContext());
                              container.addView(datePicker);
                break;
            case "NumberPicker": LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
                params.setMargins(400, 20, 0, 20); // opcional: define margens
                numberPicker = new NumberPicker(getApplicationContext());
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(1000);
                numberPicker.setLayoutParams(params); // define os parâmetros
                container.addView(numberPicker);
                break;

        }

    }

}
