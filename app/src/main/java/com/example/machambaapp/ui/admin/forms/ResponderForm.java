package com.example.machambaapp.ui.admin.forms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Pergunta;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResponderForm extends AppCompatActivity {

    TextView txtPergunta;
    Button btnResponder;
    LinearLayout container;
    ImageView imageAddCultura;
    static Uri urlImage;
    Dialog dialog;
    Uri urlImageCaptureFace;

    ImageView image;

    private boolean displayedPopup = false;
    Button cancel;
    CardView galeria;
    CardView camera;
    static boolean isCamera;
    private boolean isDisplayPopUp = false;
    ImageView imageUserUpload;
    ImageView imageDocumentUpload;
    static boolean isPhotoCaptureForDocument;
    Uri urlImageGaleria;
    Uri urlImageCamera;
    EditText editText;
    CheckBox checkBox;
    RadioGroup radioGroup;
    DatePicker datePicker;
    RadioButton radioButton1, radioButton2, radioButton3,radioButton4;
    ImageView imageView;

    boolean enteredConditional = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SplashScreen.indexForm--;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_form);
        try{

            txtPergunta = findViewById(R.id.nomePergunta);
            txtPergunta.setText(SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getNomeDaPergunta());
            container = findViewById(R.id.container);
            txtPergunta = findViewById(R.id.nomePergunta);
            btnResponder = findViewById(R.id.btnNext);

            if (!SplashScreen.showingConditional){
                mostrarCampo(SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm));
            }else{
                mostrarCampo(SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getPerguntasCondicionais().get(SplashScreen.indexCondicional));
            }

            Pergunta pergunta = SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm);

            btnResponder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<Pergunta> perguntasCondicionais = SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).perguntasCondicionais;

                    if (SplashScreen.indexForm >= SplashScreen.formulario.getPerguntas().size()){
                        Toast.makeText(getApplicationContext(), "Formulario ja esta no fim ", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    SplashScreen.v = v;

                    String answer = collectAnswers(SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getTipoPergunta());

                    String a = answer;

                    if (perguntasCondicionais != null)
                    {
                        if (SplashScreen.indexCondicional >= SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).perguntasCondicionais.size()) {
                            SplashScreen.indexCondicional = 0;
                            SplashScreen.showingConditional = false;
                            SplashScreen.indexForm++;
                            return;
                        }
                    }

                    if (pergunta.getNomeDaPergunta().toLowerCase().contains("pesticida botanico")){

                        // Show conditional question
                        if (answer.toLowerCase().contains("sim")){
                            SplashScreen.showingConditional = true;
                            startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                            return;
                        }
                    }


                    if (pergunta.getNomeDaPergunta().equals("O canteiro tem uma camada de estrume e uma de cobertura morta?\n")
                            || pergunta.getNomeDaPergunta().contains("Foi aplicado Bokashi neste canteiro?")
                    ){

                        if (!answer.contains("Camada de Estrume") || !answer.contains("Camada de Cobertura Morta")){
                            enteredConditional = true;
                            displayPopUp(v, pergunta.getNomeDaPergunta());
                        }else{
                            enteredConditional = false;
                        }
                    }

                    if (pergunta.getNomeDaPergunta().equals("Medir a umidade do canteiro em varios pontos, perto da base das plantas. A humidade pode variar dependendo da area examinada")){

                    }
                    // User did not respond form and pressed next buttom
                    if (answer.equals("")) {
                        Toast.makeText(getApplicationContext(), "Por favor, preencha o campo de respostas ", Toast.LENGTH_LONG).show();
                    } else {

                        if (!enteredConditional && !SplashScreen.showingConditional) {
                            // See if this question have a conditional flow
                            if (SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).conditionalQuestion == null) {

                                // Then Follow the Flow Normaly
                                SplashScreen.indexForm++;

                                startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                            } else {

                                // Here is the conditional flow must come
                            }

                            // Move to next question
                        }
                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG ).show();
            SplashScreen.indexForm = 0;
        }
    }

    private void displayPopUp(View v, String pergunta){

        ImageView img = new ImageView(v.getContext());

        if (pergunta.toLowerCase().contains("camada de estrume")){
            img.setImageResource(R.drawable.checkbox2_1);

        }else if (pergunta.toLowerCase().contains("bokashi")){
            img.setImageResource(R.drawable.bokashi_1);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(img);

        builder.setTitle("Informação Importante");
        builder.setPositiveButton("Seguinte", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageView img = new ImageView(v.getContext());

                if (pergunta.toLowerCase().contains("camada de estrume")){
                    img.setImageResource(R.drawable.checkbox2_2);
                }else if (pergunta.toLowerCase().contains("bokashi")){
                    img.setImageResource(R.drawable.bokashi__2);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(img);

                builder.setTitle("Informação Importante");
                builder.setView(img);

                builder.setPositiveButton("Comprendi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                        SplashScreen.indexForm++;
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        builder.setNegativeButton("Rever Escolha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDisplayPopUp = false;
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
        private String collectAnswers(String typeOfQuestion){

            String resposta = "";

            if (typeOfQuestion.contains("CheckBox")){
                for (int i = 0; i < container.getChildCount(); i++){
                  CheckBox c =(CheckBox) container.getChildAt(i);
                  resposta = c.getText() + ",";
                }

            }else if (typeOfQuestion.contains("Radio")){

                RadioGroup r = (RadioGroup) container.getChildAt(0);

                for (int i = 0 ; i < r.getChildCount() ;i++){
                    RadioButton rb = (RadioButton) r.getChildAt(i);

                    if ( rb.isChecked()){
                        resposta += rb.getText() + ",";
                    }
                }

            }else if (typeOfQuestion.contains("EditText")){

                EditText txt = (EditText) container.getChildAt(0);
                resposta = txt.getText().toString();

            }else if (typeOfQuestion.contains("Image")){
                resposta = "Image View";/*
                imageView = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
                layoutParams.setMargins(200, 10, 16, 100);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.baseline_photo_camera_24);
                container.addView(imageView);
                ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode()== Activity.RESULT_OK){
                                    Intent data=result.getData();
                                    urlImageGaleria =data.getData();
                                    imageView.setImageURI(urlImageGaleria);
                                }else {
                                    Toast.makeText(ResponderForm.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPhotoCaptureForDocument=false;
                        dialog =new Dialog(ResponderForm.this);
                        dialog.setContentView(R.layout.alert_view_dialog_choose_camera);

                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                        }
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                        camera=dialog.findViewById(R.id.idCardCamera);
                        cancel=dialog.findViewById(R.id.alertButton);
                        galeria=dialog.findViewById(R.id.idCardGaleria);

                        galeria.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isCamera=false;
                                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                                photoPicker.setType("image/*");
                                activityResultLauncherImageUsers.launch(photoPicker);
                                dialog.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isCamera=true;
                                Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(openCamera,1);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                */
            }else if (typeOfQuestion.contains("Number")){
                NumberPicker numberPicker = (NumberPicker) container.getChildAt(0);
                resposta = numberPicker.getValue() + "";
            }
            return  resposta;
       }
    private void mostrarCampo(Pergunta p){

        String tipoResposta = p.getTipoPergunta();

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

            case "CheckBox": checkBox = new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                for (String c : SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getOpcoes()) {
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
                layoutParams.setMargins(200, 10, 16, 100);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.baseline_photo_camera_24);
                container.addView(imageView);
                ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode()== Activity.RESULT_OK){
                                    Intent data=result.getData();
                                    urlImageGaleria =data.getData();
                                    imageView.setImageURI(urlImageGaleria);
                                }else {
                                    Toast.makeText(ResponderForm.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPhotoCaptureForDocument=false;
                        dialog =new Dialog(ResponderForm.this);
                        dialog.setContentView(R.layout.alert_view_dialog_choose_camera);

                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                        }
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                        camera=dialog.findViewById(R.id.idCardCamera);
                        cancel=dialog.findViewById(R.id.alertButton);
                        galeria=dialog.findViewById(R.id.idCardGaleria);

                        galeria.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isCamera=false;
                                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                                photoPicker.setType("image/*");
                                activityResultLauncherImageUsers.launch(photoPicker);
                                dialog.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isCamera=true;
                                Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(openCamera,1);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;
            case "RadioGroup1":  {
                radioGroup = new RadioGroup(getApplicationContext());
                container.setVisibility(View.INVISIBLE);
                btnResponder.setVisibility(View.INVISIBLE);

                VideoView videoView = findViewById(R.id.videoview);

                videoView.setVisibility(View.VISIBLE);

                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName() + "/" + R.raw.humidade));
                MediaController mediaController = new MediaController(this);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();

                for (String op :  SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getOpcoes() ){

                    RadioButton r = new RadioButton(getApplicationContext());
                    r.setText(op);
                    radioGroup.addView(r);
                }

                container.addView(radioGroup);

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getApplicationContext(), "Parabens, voce ja sabe verificar a humidade do solo!", Toast.LENGTH_LONG).show();
                        videoView.setVisibility(View.INVISIBLE);
                        container.setVisibility(View.VISIBLE);
                        btnResponder.setVisibility(View.VISIBLE);
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                radioGroup = new RadioGroup(getApplicationContext());

            }
            break;

            case "RadioGroup": radioGroup = new RadioGroup(getApplicationContext());

                for (String op :  SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).getOpcoes() ){

                    RadioButton r = new RadioButton(getApplicationContext());
                    r.setText(op);
                    radioGroup.addView(r);
                }

                container.addView(radioGroup);
                break;

            case "DatePicker":datePicker = new DatePicker(getApplicationContext());
                container.addView(datePicker);
                break;
            case "NumberPicker":
                LinearLayout linearLayout = findViewById(R.id.container); // seu layout onde você quer adicionar o NumberPicker
                layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Define o formato de exibição para o NumberPicker.

                NumberPicker numberPicker = new NumberPicker(this);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(1000);
                numberPicker.setFormatter(new NumberPicker.Formatter() {
                    @Override
                    public String format(int value) {
                        return decimalFormat.format(value / 100.0); // Formata o valor exibido no NumberPicker para exibir duas casas decimais.
                    }
                });
                numberPicker.setLayoutParams(layoutParams);

                linearLayout.addView(numberPicker); // Adiciona o NumberPicker ao seu layout.
                break;
            case "Location":
                break;
        }
    }
}
