package com.example.machambaapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.helper.OfflineDB;
import com.example.machambaapp.ui.admin.addforms.AddEtnia;
import com.example.machambaapp.ui.admin.forms.ResponderForm;
import com.example.machambaapp.ui.admin.views.ActivityViewEtnia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<String> distritos = DatabaseHelper.getLocation("distritos");
    public static ArrayList<Cliente.UserPl> users = DatabaseHelper.getUsers();
    public static ArrayList<String> provincias = new ArrayList<String>();

    public static ArrayList<Cliente> clientes = DatabaseHelper.getClientes();
    public static boolean runGroup = false;
    public static int groupIndex = 0;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static int selectedCulturesIndex = 0;
    public static int indexForm = 0;
    public static boolean showingConditional = false;
    public static Pergunta currentQuestion = new Pergunta();
    public static int indexCondicional = 0;

    public static ArrayList<Formulario> formularios =  DatabaseHelper.getForms();
    public static Formulario formulario = new Formulario();

    public static ArrayList<String> culturas = DatabaseHelper.getLocation("culturas");
    public static ArrayList<String> postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
    public static ArrayList<String> localiadades = DatabaseHelper.getLocation("localidades");
    public static ArrayList<String> comunidades = DatabaseHelper.getLocation("comunidades");
    public static ArrayList<String> etnia = DatabaseHelper.getEtnia("etnias");
    public static Cliente.UserPl currentUser = new Cliente.UserPl();
    public static boolean finishGroup = false;
    public static HashMap<String, ArrayList<Pergunta>>  groupQuestions = new HashMap<>();
    public static HashMap<String, Pergunta> perguntas = new HashMap<>();
    public static void updateComunidade(){
        comunidades = DatabaseHelper.getLocation("comunidades");
    }
    public static void updateDistrito(){
        distritos = DatabaseHelper.getLocation("distritos");
    }

    public static void updatePosto(){
        postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
    }
    public static ArrayList<String> selectedCultures = new ArrayList<>();
    public static void UpdateDataFromOnlineDatabase(){
        try{
            etnia = DatabaseHelper.getEtnia("etnias");
            comunidades = DatabaseHelper.getLocation("comunidades");
            localiadades = DatabaseHelper.getLocation("localidades");
            postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
            distritos = DatabaseHelper.getLocation("distritos");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        provincias.add("Maputo");
        provincias.add("Gaza");
        provincias.add("Inhambane");
        provincias.add("Sofala");
        provincias.add("Manica");
        provincias.add("Tete");
        provincias.add("Zambézia");
        provincias.add("Nampula");
        provincias.add("Cabo Delgado");
        provincias.add("Niassa");

        formulario =  new Formulario();
        ArrayList<Pergunta> ps = new ArrayList<>();

        /*Adding questions to the dictionary*/

        ArrayList<String> generos = new ArrayList<>();
        generos.add("Masculino");
        generos.add("Femenino");

        Pergunta p = new Pergunta();
        p.setNomeDaPergunta("Genero ?");
        p.setTipoPergunta("RadioGroup");
        p.setOpcoes(generos);


        culturas = new ArrayList<>();

        culturas.add("Alface");
        culturas.add("Cebola");
        culturas.add("Couve");
        culturas.add("Tomate");

        ps.add(p);
/*
        p = new Pergunta();
        p.setNomeDaPergunta("Registar coordenadas geograficas");
        p.setTipoPergunta("EditText");

        ps.add(p);
*/
        p = new Pergunta();

        p.setNomeDaPergunta("\n Canteiro \n \n Vamos comecar a avaliação dum canteiro. Na pergunta " +
                "seguinte pode selecionar uma ou mais culturas se o canteiro tiver consorciação.");
        p.setTipoPergunta("ImageView");

        ps.add(p);
        perguntas.put("2", p);
        p = new Pergunta();

        p.setNomeDaPergunta("Indicar a largura do Canteiro (metros)");
        p.setTipoPergunta("NumberPicker");

        ps.add(p);
        perguntas.put("3", p);

        p.setNomeDaPergunta("Indicar o cumprimento do Canteiro (metros)");
        p.setTipoPergunta("EditText");

        ps.add(p);

        perguntas.put("4", p);
        p = new Pergunta();

        p.setNomeDaPergunta("Selecionar as culturas do canteiro");
        p.setTipoPergunta("CheckBox");
        p.setOpcoes(culturas);
        ps.add(p);

        perguntas.put("5", p);

        p = new Pergunta();

        p.setNomeDaPergunta("O canteiro tem uma camada de estrume e uma de cobertura morta?\n");

        ArrayList<String> respostas = new ArrayList<>();
        respostas.add("Camada de Estrume");
        respostas.add("Camada de Cobertura Morta");
        respostas.add("Nenhuma");


        p.setTipoPergunta("CheckBox");
        p.setOpcoes(respostas);

        ps.add(p);

        perguntas.put("6", p);
        ArrayList<String> opcoes = new ArrayList<>();
        opcoes.add("Não tem humidade");
        opcoes.add("tem mas o bolo não fica bem firme");
        opcoes.add("O bolo fica bem firme, a humidade é boa!");
        opcoes.add("Esta a escorrer água, esta molhado demais!");

        p = new Pergunta();

        p.setNomeDaPergunta("Medir a umidade do canteiro em vários pontos, perto da base das plantas. A humidade pode variar dependendo da área examinada");
        p.setTipoPergunta("RadioGroup1");
        p.setOpcoes(opcoes);
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Foi aplicado Bokashi neste canteiro?" );

        opcoes = new ArrayList<>();
        opcoes.add("Sim");
        opcoes.add("Não");

        p.setTipoPergunta("RadioGroup");
        p.setOpcoes(opcoes);
        ps.add(p);

        // Sobre pesticida Botanico

        p = new Pergunta();
        p.setNomeDaPergunta("O produtor esta a aplicar pesticida botânico neste canteiro?");

        opcoes = new ArrayList<>();

        opcoes.add("Sim");
        opcoes.add("Não");

        p.setOpcoes(opcoes);

        p.setTipoPergunta("RadioGroup");
      //  ps.add(p);

        // ******************************

        // condicional - se o agricultor esta a usar pesticida Botanico

        p.setPerguntasCondicionais(new ArrayList<Pergunta>());

        Pergunta condicional = new Pergunta();
        condicional.setNomeDaPergunta("Qual plantas usa ?");
        condicional.setTipoPergunta("CheckBox");

        ArrayList<String> plantas = new ArrayList<>();

        plantas.add("Cacana");
        plantas.add("Malua");
        plantas.add("Margosa");
        plantas.add("Nkura");
        plantas.add("Nseta");
        plantas.add("Shafatali");
        plantas.add("Theka");
        plantas.add("Tabaco");
        plantas.add("Nthavaka");
        plantas.add("Newawi");

        condicional.setOpcoes(plantas);

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();

        condicional.setNomeDaPergunta("Quantos dias deixou mergulhar o pesticida ? (Indicar o número de dias)");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();

        condicional.setNomeDaPergunta("Quantos dias consecutivos pulveriza");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();
        condicional.setNomeDaPergunta("Quantos dias de pausa entre pulverizações ?");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        ps.add(p);

        assignQuestionsToCultures();

        formulario.setPerguntas(ps);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, ActivityPageStart.class);
//                Intent intent = new Intent(SplashScreen.this, ResponderForm.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle();
                startActivity(intent, b);
                finish();
            }
        }, 2000);

    }

    private void assignQuestionsToCultures(){

        ArrayList<String> opcoes = new ArrayList<>();
        ArrayList<String> op = new ArrayList<>();
        ArrayList<Pergunta> perguntasCondicionais = new ArrayList<>();
        Pergunta p = new Pergunta();

        ArrayList<Pergunta> ps;

        for (String c : culturas){


            ps = new ArrayList<>();

            p = new Pergunta();
            p.setNomeDaPergunta("Indicar data do transplante para "+ c);
            p.setTipoPergunta("DatePicker");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Indicar a fase de crescimento do "+ c);
            opcoes = new ArrayList<>();

            opcoes.add("Pequeno");
            opcoes.add("Médio");
            opcoes.add("Grande");
            opcoes.add("Muito Grande");
            p.setOpcoes(opcoes);
            p.setTipoPergunta("RadioGroup");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Indicar o espaçamento entre as plantas de "+ c);
            p.setTipoPergunta("EditText");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Indicar o espaçamento entre linhas para "+c+" em centímetros");
            p.setTipoPergunta("EditText");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Quantas linhas de "+ c +" tem o canteiro?");
            p.setTipoPergunta("EditText");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("As plantas de "+ c +" tem incidência de pragas e doenças?");
            p.setTipoPergunta("RadioGroup");
            opcoes = new ArrayList<>();
            opcoes.add("Sim");
            opcoes.add("Não");
            p.setOpcoes(opcoes);
//            p.setCondicaoTexto("Não");
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Tirar foto da praga ou doença");
            p.setTipoPergunta("ImageView");
            perguntasCondicionais.add(p);
            ps.add(p);

            p = new Pergunta();
            p.setNomeDaPergunta("Esta praga ou doença é muito difusa?");
            p.setTipoPergunta("RadioGroup");

            opcoes = new ArrayList<>();

            opcoes.add("Sim");
            opcoes.add("Não");
            p.setOpcoes(opcoes);
            perguntasCondicionais.add(p);
            p.setPerguntasCondicionais(perguntasCondicionais);
            ps.add(p);


            groupQuestions.put(c, ps);
            ps = new ArrayList<>();
            ps.clear();
        }
    }

}