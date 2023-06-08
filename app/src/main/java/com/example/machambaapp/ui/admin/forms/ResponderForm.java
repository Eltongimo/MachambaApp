package com.example.machambaapp.ui.admin.forms;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.android.gms.location.LocationListener;
import com.google.android.material.slider.Slider;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ResponderForm extends AppCompatActivity {

    TextView txtPergunta;
    Button btnResponder;
    LinearLayout container;
    ImageView imageAddCultura;
    static Uri urlImage;
    Dialog dialog;
    Uri urlImageCaptureFace;

    ImageView image;

    private String data = "";

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
    ProgressDialog loadingBar;
    CheckBox checkBox;
    RadioGroup radioGroup;
    DatePicker datePicker;
    ImageView imageView;

    private String resposta = "";
    boolean enteredConditional = false;

    private Pergunta pergunta;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (SplashScreen.showingConditional) {
            SplashScreen.indexCondicional--;
        } else {
            SplashScreen.indexForm--;
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_form);
        ProgressDialog loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Carregando o formulário...");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingBar.dismiss();
            }
        }, 500); // Tempo em milissegundos (1 segundo)


        try{
            txtPergunta = findViewById(R.id.nomePergunta);
            container = findViewById(R.id.container);
            btnResponder = findViewById(R.id.btnNext);
          //  ProgressBar progressBar = findViewById(R.id.progressBar);

            if (SplashScreen.runGroup){

                if (SplashScreen.groupIndex < SplashScreen.groupQuestions.get(SplashScreen.selectedCultures.
                        get(SplashScreen.selectedCulturesIndex)).size()){
                        pergunta = SplashScreen.groupQuestions.get(SplashScreen.selectedCultures.
                                get(SplashScreen.selectedCulturesIndex)).get(SplashScreen.groupIndex);

                }else{
                    SplashScreen.groupIndex = 0;
                    SplashScreen.selectedCulturesIndex++;
                    pergunta = SplashScreen.groupQuestions.get(SplashScreen.selectedCultures.
                            get(SplashScreen.selectedCulturesIndex)).get(SplashScreen.groupIndex);
                    loadingBar.dismiss();

                }

                if (pergunta == null){
                    SplashScreen.runGroup = false;
                    // Just to end the form
                    SplashScreen.indexForm = 15;
                }
                mostrarCampo(pergunta);
            }else{
                if (SplashScreen.indexForm < SplashScreen.formulario.getPerguntas().size()) {
                    pergunta = SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm);
                } else if (SplashScreen.finishGroup){
                    Toast.makeText(ResponderForm.this, "Fim do formulario", Toast.LENGTH_SHORT).show();
                    btnResponder.setText("Submeter Formulario");
                    btnResponder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ResponderForm.this, "Submetendo Formulario", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                if (!SplashScreen.showingConditional) {
                    if (pergunta != null){
                        mostrarCampo(pergunta);
                    }
                    else{
                        SplashScreen.runGroup = true;
                        startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                        return ;
                        // pergunta = SplashScreen.groupQuestions.get(SplashScreen.selectedCultures.get(SplashScreen.selectedCulturesIndex)).get(SplashScreen.groupIndex);
                    //    mostrarCampo(pergunta);
                    }
                }
                else
                {
                    pergunta = pergunta.getPerguntasCondicionais().get(SplashScreen.indexCondicional);
                    mostrarCampo(pergunta);
                }
            }

            txtPergunta.setText(pergunta.getNomeDaPergunta());

            btnResponder.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    collectAnswers(pergunta.getTipoPergunta());

                    if (SplashScreen.runGroup){
                        SplashScreen.groupIndex++;
                        startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                        return;
                    }

                    if (SplashScreen.showingConditional) {

                        if (pergunta.getNomeDaPergunta().contains("Quantos dias deixou mergulhar o pesticida")){

                            if (resposta.isEmpty()){
                                Toast.makeText(ResponderForm.this, "Por favor, preencha o campo de resposta", Toast.LENGTH_LONG).show();
                                return ;
                            }
                            int res = Integer.parseInt(resposta);
                            if (res < 7){
                                displayConditionalPopUp(pergunta.getNomeDaPergunta());
                                return ;
                            }
                        }

                        SplashScreen.indexCondicional++;

                        if (SplashScreen.indexCondicional >= SplashScreen.formulario.getPerguntas().get(SplashScreen.indexForm).perguntasCondicionais.size()) {
                            SplashScreen.showingConditional = false;
                            SplashScreen.indexCondicional = 0;
                            SplashScreen.indexForm += 1;
                        }
                        startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                    } else {
                        // So here the question do not have conditions

                        if (SplashScreen.indexForm <= 9){
                            if (pergunta.perguntasCondicionais != null) {

                                if (SplashScreen.indexCondicional >= pergunta.perguntasCondicionais.size()) {
                                    SplashScreen.indexCondicional = 0;
                                    SplashScreen.showingConditional = false;
                                    SplashScreen.indexForm += 1;
                                    return;
                                }
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("culturas do canteiro")){

                                for (String s : resposta.split(",")){
                                    if ( !s.contains(",") && !s.isEmpty())
                                        SplashScreen.selectedCultures.add(s.toLowerCase());
                                }
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("pesticida")){
                                if (resposta.toLowerCase().contains("não")){
                                    displayConditionalPopUp(pergunta.getNomeDaPergunta());
                                }else{
                                    //  SplashScreen.indexForm++;
                                    SplashScreen.showingConditional = true;
                                    startActivity(new Intent(ResponderForm.this,ResponderForm.class));
                                }
                                return;
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("bokashi")){

                                if (resposta.toLowerCase().contains("não")){
                                    displayConditionalPopUp(pergunta.getNomeDaPergunta());
                                    return ;
                                }else{
                                    SplashScreen.indexForm++;
                                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                                }
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("incidência de praga")) {
                                if (resposta.toLowerCase().contains("sim")) {
                                    SplashScreen.showingConditional = true;
                                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                                    return;
                                }
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("mergulhar o pesticida")){

                                Toast.makeText(ResponderForm.this,"dias de mergulho", Toast.LENGTH_LONG).show();
                            }

                            if (pergunta.getNomeDaPergunta().toLowerCase().contains("pesticida botanico")) {

                                // Show conditional question
                                if (resposta.toLowerCase().contains("sim")) {
                                    SplashScreen.showingConditional = true;
                                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                                    return;
                                }
                            }

                            // User did not respond form and pressed next buttom
                            if (resposta.equals("")) {
                                Toast.makeText(getApplicationContext(), "Por favor, preencha o campo de respostas ", Toast.LENGTH_LONG).show();
                            } else {

                                if (!enteredConditional && !SplashScreen.showingConditional) {
                                    // See if this question have a conditional flow
                                    if (pergunta.conditionalQuestion == null) {

                                        // Then Follow the Flow Normaly
                                        SplashScreen.indexForm++;

                                        startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String collectAnswers(String typeOfQuestion) {

        resposta = "";

        if (typeOfQuestion.contains("CheckBox")) {
            for (int i = 0; i < container.getChildCount(); i++) {

                if (container.getChildAt(i).getClass() == CheckBox.class){
                    CheckBox c = (CheckBox) container.getChildAt(i);
                    if (c.isChecked())
                        resposta += c.getText() + ",";

                }else if (container.getChildAt(i).getClass() == EditText.class){
                    EditText edt = (EditText) container.getChildAt(i);
                    resposta += " "+ edt.getText().toString();
                }

            }

        } else if (typeOfQuestion.contains("Slider")) {
            resposta = "  ";

        } else if (typeOfQuestion.contains("DatePicker")) {

            Calendar calendar = Calendar.getInstance();

            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    resposta = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    Toast.makeText(ResponderForm.this, resposta, Toast.LENGTH_LONG).show();
                }
            });
            resposta = "   ";
        } else if (typeOfQuestion.contains("Radio")) {

            RadioGroup r = (RadioGroup) container.getChildAt(0);

            for (int i = 0; i < r.getChildCount(); i++) {
                RadioButton rb = (RadioButton) r.getChildAt(i);

                if (rb.isChecked()) {
                    resposta += rb.getText() + ",";
                }
            }
        } else if (typeOfQuestion.contains("EditText")) {

            EditText txt = (EditText) container.getChildAt(0);
            resposta = txt.getText().toString();

        } else if (typeOfQuestion.contains("Image")) {
            resposta = "Image View";

        } else if (typeOfQuestion.contains("Number")) {
            NumberPicker numberPicker = (NumberPicker) container.getChildAt(0);
            resposta = numberPicker.getValue() + "";
        }
        return resposta;
    }

    // Aqui mostra popup condicional para algumas perguntas
    private void displayConditionalPopUp(String per){

        if (per.contains("mergulhar o pesticida")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ResponderForm.this);
            builder.setTitle("Informação Importante");
            builder.setMessage("Parabens "+SplashScreen.currentUser.getNome()+" por preparar o seu pesticida natural. Embora, o tempo de mergulho deveria aumentar." +
                    " Para aumentar a forca do produto, se aconselha mergulhar minimo 5 plantas differentes e deixar mergulhar minimo 7" +
                    " dias misturando todos os dias! VEJA O MANUAL DE PESTICIDAS NATURAIS DA IDE");
            builder.setPositiveButton("Compreendi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreen.indexCondicional++;
                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }else if (per.toLowerCase().contains("pesticida botanico")){
            Toast.makeText(ResponderForm.this, SplashScreen.indexForm+"", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(ResponderForm.this);
            builder.setTitle("Informação Importante");
            builder.setMessage("E' muito importante proteger a propia machamba com pesticida natural.\n" +
                    " O produtor sabe que tem muitas plantas disponiveis e vale a pena investir num pulverizador?\n" +
                    " VEJA O MANUAL DA IDE SOBRE PESTICIDAS NATURAIS \n\n"+
                    "E' muito importante proteger a propia machamba com pesticida natural. \nO produtor sabe que tem muitas plantas disponiveis e vale a pena investir num pulverizador? VEJA O MANUAL DA IDE SOBRE PESTICIDAS NATURAIS"
            );
            builder.setPositiveButton("Compreendi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreen.indexForm++;
                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if (per.toLowerCase().contains("bokashi")){

            AlertDialog.Builder builder = new AlertDialog.Builder(ResponderForm.this);
            builder.setTitle("Informação Importante");
            builder.setMessage("O bokashi e\' vida! com um custo limitado o produtor pode aumentar a produtividade e melhorar a qualidade" +
                    " do seu solo. Veja o manual de bokashi da iDE\n");

            builder.setPositiveButton("Compreendi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreen.indexForm++;
                    startActivity(new Intent(ResponderForm.this, ResponderForm.class));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    // Neste metodo o sw verifica se a na pergunta corrente tem video, caso sim ele mostra o video
    // E tambem se a pergunta tem algum tipo de popup condicional, como a pergunta sobre bokashi, pesticida
    private void mostrarBotaoVideo(String per){

        Button btnVideo = findViewById(R.id.btnvideo);

        ImageView img = new ImageView(ResponderForm.this);

        Button btnVerPopUp = findViewById(R.id.btnvideo);

        if (per.toLowerCase().contains("pesticida botanico")) {
            img.setImageResource(R.drawable.pesticida1);
            btnVideo.setVisibility(View.VISIBLE);
            btnVideo.setText("Dica");
        }else if (per.toLowerCase().contains("bokashi")) {
            img.setImageResource(R.drawable.bokashi_1);
            btnVideo.setVisibility(View.VISIBLE);
            btnVideo.setText("Dica");
        }else if (per.toLowerCase().contains("cobertura morta")){
            img.setImageResource(R.drawable.checkbox2_1);
            btnVideo.setVisibility(View.VISIBLE);
            btnVideo.setText("Dica");
        }

        if (per.toLowerCase().contains("pesticida")){
            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnVerPopUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setView(img);

                            builder.setTitle("Informação Importante");
                            builder.setPositiveButton("Seguinte", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ImageView img = new ImageView(v.getContext());

                                    img.setImageResource(R.drawable.pesticida1);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setView(img);

                                    builder.setTitle("Informação Importante");
                                    builder.setView(img);

                                    builder.setPositiveButton("Comprendi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                }
            });
        }

        if (per.toLowerCase().contains("bokashi")){
            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnVerPopUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setView(img);

                            builder.setTitle("Informação Importante");
                            builder.setPositiveButton("Seguinte", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ImageView img = new ImageView(v.getContext());

                                    if (per.toLowerCase().contains("camada de estrume")) {
                                        img.setImageResource(R.drawable.checkbox2_2);
                                    }else if (per.toLowerCase().contains("bokashi")) {
                                        img.setImageResource(R.drawable.bokashi__2);
                                    }

                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setView(img);

                                    builder.setTitle("Informação Importante");
                                    builder.setView(img);

                                    builder.setPositiveButton("Comprendi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                }
            });
        }

        if (per.toLowerCase().contains("cobertura morta")){

            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnVerPopUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setView(img);

                                builder.setTitle("Informação Importante");
                                builder.setPositiveButton("Seguinte", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ImageView img = new ImageView(v.getContext());

                                        if (per.toLowerCase().contains("camada de estrume")) {
                                            img.setImageResource(R.drawable.checkbox2_2);
                                        } else if (per.toLowerCase().contains("bokashi")) {
                                            img.setImageResource(R.drawable.bokashi__2);
                                        }

                                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                        builder.setView(img);

                                        builder.setTitle("Informação Importante");
                                        builder.setView(img);

                                        builder.setPositiveButton("Comprendi", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                        }
                    });
                }
            });
        }

        if (per.toLowerCase().contains("humidade")){

            btnVideo.setText("Mostrar Video");
            btnVideo.setVisibility(View.VISIBLE);

            btnVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    VideoView videoView = findViewById(R.id.videoview);

                    ViewGroup.LayoutParams params = videoView.getLayoutParams();

                    params.height = 2000; // ou outra altura desejada em pixels

                    videoView.setLayoutParams(params);

                    videoView.setVisibility(View.VISIBLE);

                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.humidade));
                    MediaController mediaController = new MediaController(ResponderForm.this);
                    videoView.setMediaController(mediaController);
                    mediaController.setAnchorView(videoView);
                    videoView.start();

                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(getApplicationContext(), "Parabens, voce ja sabe verificar a humidade do solo!", Toast.LENGTH_LONG).show();
                            ViewGroup.LayoutParams paramsx = videoView.getLayoutParams();

                            paramsx.height = 0; // ou outra altura desejada em pixels
                            videoView.setLayoutParams(paramsx);
                            videoView.setVisibility(View.INVISIBLE);
                            container.setVisibility(View.VISIBLE);
                            btnResponder.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }
    }

    // Este metodo mostra o tipo de input baseado na pergunta.

    private void mostrarCampo(Pergunta p) {

        String tipoResposta = p.getTipoPergunta();

        mostrarBotaoVideo(p.getNomeDaPergunta());

        switch (tipoResposta) {

            case "Slider":
                Slider slider = new Slider(ResponderForm.this);
                slider.setId(View.generateViewId()); // set a unique ID for the slider
                slider.setValueFrom(1); // set the minimum value of the slider
                slider.setValueTo(4); // set the maximum value of the slider
                slider.setStepSize(1); // set the step size of the slider
                slider.setValue(1);

                container.addView(slider);
                TextView txt1 = new TextView(getApplicationContext());
                txt1.setText("1 - Pequeno\n");

                TextView txt2 = new TextView(getApplicationContext());
                txt1.setText("2 - Normal\n");

                TextView txt3 = new TextView(getApplicationContext());
                txt1.setText("3 - Acima do Normal\n");

                TextView txt4 = new TextView(getApplicationContext());
                txt1.setText("4 - Grande\n");

                TextView legenda = new TextView(getApplicationContext());
                legenda.setText("Legenda");
                legenda.setTextSize(30);
                container.addView(legenda);
                container.addView(txt1);
                container.addView(txt2);
                container.addView(txt3);
                container.addView(txt4);

                slider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(Slider slider, float value, boolean fromUser) {
                        resposta = value + "";
                    }
                });
                resposta = " ";
                break;

            case "EditText":
                editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        130));
                editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                editText.setPadding(16, 0, 16, 0);
                editText.setHint("Digite aqui...");
                editText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                editText.setTypeface(ResourcesCompat.getFont(this, R.font.poppinsregular));
                editText.setBackground(getResources().getDrawable(R.drawable.button_sape));
                container.addView(editText);
                break;

            case "CheckBox":
                checkBox = new CheckBox(this);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                for (String c : p.getOpcoes()) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(c);
                    container.addView(checkBox);
                }

                if (p.getNomeDaPergunta().contains("Qual plantas usa")){
                    editText = new EditText(this);
                    editText.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            130));
                    editText.setPadding(16, 0, 16, 0);
                    editText.setHint("Caso outra indique ...");
                    editText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    editText.setTypeface(ResourcesCompat.getFont(this, R.font.poppinsregular));
                    editText.setBackground(getResources().getDrawable(R.drawable.button_sape));
                    container.addView(editText);
                }

                break;

            case "ImageView":
//                imageView = new ImageView(getApplicationContext());
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
//                layoutParams.setMargins(200, 10, 16, 100);
//                imageView.setLayoutParams(layoutParams);
//                imageView.setImageResource(R.drawable.baseline_photo_camera_24);
//                container.addView(imageView);

                imageView = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
                layoutParams.setMargins(200, 10, 16, 100);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.baseline_photo_camera_24);
                container.addView(imageView);

// Adicionar a legenda
                TextView legend = new TextView(getApplicationContext());
                legend.setText("Clique para tirar uma foto");
                LinearLayout.LayoutParams legendaParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                legendaParams.setMargins(200, 0, 16, 0);
                legend.setLayoutParams(legendaParams);
                container.addView(legend);


                ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Intent data = result.getData();
                                    urlImageGaleria = data.getData();
                                    imageView.setImageURI(urlImageGaleria);
                                } else {
                                    Toast.makeText(ResponderForm.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPhotoCaptureForDocument = false;
                        dialog = new Dialog(ResponderForm.this);
                        dialog.setContentView(R.layout.alert_view_dialog_choose_camera);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                        }
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                        camera = dialog.findViewById(R.id.idCardCamera);
                        cancel = dialog.findViewById(R.id.alertButton);
                        galeria = dialog.findViewById(R.id.idCardGaleria);

                        galeria.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isCamera = false;
                                Intent photoPicker = new Intent(Intent.ACTION_PICK);
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
                                isCamera = true;
                                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(openCamera, 1);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;
            case "RadioGroup1": {
                radioGroup = new RadioGroup(getApplicationContext());

                for (String op : p.getOpcoes()) {

                    RadioButton r = new RadioButton(getApplicationContext());
                    r.setText(op);
                    radioGroup.addView(r);
                }

                container.addView(radioGroup);
                radioGroup = new RadioGroup(getApplicationContext());

            }
            break;

            case "RadioGroup":
                radioGroup = new RadioGroup(getApplicationContext());

                for (String op : p.getOpcoes()) {

                    RadioButton r = new RadioButton(getApplicationContext());
                    r.setText(op);
                    radioGroup.addView(r);
                }

                container.addView(radioGroup);
                break;

            case "DatePicker":
                datePicker = new DatePicker(getApplicationContext());
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

                numberPicker.setGravity(Gravity.CENTER);

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

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        resposta += latitude + " "+ longitude;
                    }
                };

                Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_SHORT).show();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, (android.location.LocationListener) locationListener);
                break;
        }
    }
}

