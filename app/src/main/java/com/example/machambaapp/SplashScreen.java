package com.example.machambaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.datamodel.Resposta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.addforms.AddCultura;
import com.example.machambaapp.ui.admin.forms.ResponderForm;
import com.example.machambaapp.ui.clientes.ActivitySelectClient;
import com.example.machambaapp.ui.home.HomeFragment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<String> distritos = DatabaseHelper.getLocation("distritos");
    public static ArrayList<String> provincias = new ArrayList<String>();
    public static boolean runGroup = false;
    public static int groupIndex = 0;
    public static int selectedCulturesIndex = 0;
    public static int indexForm = 0;
    public static int repeate1 = 9;
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

    public static int indexCulturas = 0 ;

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
        assignQuestionsToCultures();

        ArrayList<String> generos = new ArrayList<>();
        generos.add("Masculino");
        generos.add("Femenino");

        Pergunta p = new Pergunta();
        p.setNomeDaPergunta("Genero ?");
        p.setTipoPergunta("RadioGroup");
        p.setOpcoes(generos);

        culturas.add("Alface");
        culturas.add("Cebola");
        culturas.add("Couve");
        culturas.add("Tomate");

        selectedCultures.add("alface");
        selectedCultures.add("cebola");

        perguntas.put("1", p);
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
        opcoes.add("Nao tem humidade");
        opcoes.add("tem mas o bolo não fica bem firme");
        opcoes.add("O bolo fica bem firme, a humidade é boa!");
        opcoes.add("Esta a escorrer agua, esta molhado demais!");

        p = new Pergunta();

        p.setNomeDaPergunta("Medir a umidade do canteiro em varios pontos, perto da base das plantas. A humidade pode variar dependendo da area examinada");
        p.setTipoPergunta("RadioGroup1");
        p.setOpcoes(opcoes);
        Stack s = new Stack();

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
        p.setNomeDaPergunta("O produtor esta a aplicar pesticida botanico neste canteiro?");

        opcoes = new ArrayList<>();

        opcoes.add("Sim");
        opcoes.add("Não");

        p.setOpcoes(opcoes);

        p.setTipoPergunta("RadioGroup");
        ps.add(p);

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
        plantas.add("Outra");

        condicional.setOpcoes(plantas);

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();

        condicional.setNomeDaPergunta("Quantos dias deixou mergulhar o pesticida ? (Indicar o numero de dias)");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();

        condicional.setNomeDaPergunta("Quantos dias consecutivos pulveriza");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        condicional = new Pergunta();
        condicional.setNomeDaPergunta("Quantos dias de pausa entre pulverizaçoes ?");
        condicional.setTipoPergunta("EditText");

        p.perguntasCondicionais.add(condicional);

        ps.add(p);

        formulario.setPerguntas(ps);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashScreen.this, ActivityPageStart.class));
                startActivity(new Intent(SplashScreen.this, ResponderForm.class));

                finish();
            }
        }, 2000);
    }

    private void assignQuestionsToCultures(){

        ArrayList<Pergunta> ps = new ArrayList<>();
        ArrayList<String> opcoes = new ArrayList<>();
        ArrayList<String> op = new ArrayList<>();
        ArrayList<Pergunta> perguntasCondicionais = new ArrayList<>();
        Pergunta p = new Pergunta();

        /******************************Inicio Alface**********************************/

        p.setNomeDaPergunta("Indicar data do transplante para alface");
        p.setTipoPergunta("DatePicker");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar a fase de crescimento do Alface");
        opcoes = new ArrayList<>();

        opcoes.add("Pequeno");
        opcoes.add("Medio");
        opcoes.add("Grande");
        opcoes.add("Muito Grande");
        p.setOpcoes(opcoes);
        p.setTipoPergunta("RadioGroup");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre as plantas ");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre linhas para alface em centimetros");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Quais são as inhas de alface em centimetros ?");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("As plantas de alface tem incidencia de pragas e doenças?");
        p.setTipoPergunta("RadioGroup");

        opcoes.clear();
        opcoes.add("Sim");
        opcoes.add("Não");
        p.setOpcoes(op);
        p.setCondicaoTexto("Sim");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Tirar foto da praga ou doenca");
        p.setTipoPergunta("ImageView");
        perguntasCondicionais.add(p);
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Esta praga ou doenca e' muito difusa?");
        p.setTipoPergunta("RadioGroup");
        opcoes.clear();
        opcoes.add("Sim");
        opcoes.add("Não");
        p.setOpcoes(opcoes);
        perguntasCondicionais.add(p);
        p.setPerguntasCondicionais(perguntasCondicionais);
        ps.add(p);

        groupQuestions.put("alface", ps);
        /***************************************Alface**********************************************/
        /***************************************Cebola**********************************************/

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar data do transplante para cebola");
        p.setTipoPergunta("DatePicker");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar a fase de crescimento do Cebola");
        opcoes.clear();
        opcoes.add("Pequeno");
        opcoes.add("Medio");
        opcoes.add("Grande");
        opcoes.add("Muito Grande");
        p.setOpcoes(opcoes);
        p.setTipoPergunta("RadioGroup");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre as plantas ");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre linhas para cebola em centimetros");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Quais são as inhas de cebola em centimetros ?");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("As plantas de alface tem incidencia de pragas e doenças?");
        p.setTipoPergunta("RadioGroup");
        op.clear();
        op.add("Sim");
        op.add("Não");
        p.setOpcoes(op);
        p.setCondicaoTexto("Sim");

        p = new Pergunta();
        p.setNomeDaPergunta("Tirar foto da praga ou doenca");
        p.setTipoPergunta("ImageView");

        perguntasCondicionais.clear();
        perguntasCondicionais.add(p);
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Esta praga ou doenca e' muito difusa?");
        p.setTipoPergunta("RadioGroup");
        op.clear();
        op.add("Sim");
        op.add("Não");
        p.setOpcoes(op);
        perguntasCondicionais.add(p);
        p.setPerguntasCondicionais(perguntasCondicionais);
        ps.add(p);
        groupQuestions.put("cebola", ps);

        /****************************************************Fim Cebola***************************************************/

        /***************************************Couve**********************************************/
        ps = new ArrayList<>();
        p = new Pergunta();

        p.setNomeDaPergunta("Indicar data do transplante para couve");
        p.setTipoPergunta("DatePicker");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar a fase de crescimento da couve");
        opcoes.clear();
        opcoes.add("Pequeno");
        opcoes.add("Medio");
        opcoes.add("Grande");
        opcoes.add("Muito Grande");
        p.setOpcoes(opcoes);
        p.setTipoPergunta("RadioGroup");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre as plantas ");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre linhas para couve em centimetros");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Quais são as inhas de couve em centimetros ?");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("As plantas de couve tem incidencia de pragas e doenças?");
        p.setTipoPergunta("RadioGroup");
        op.clear();
        op.add("Sim");
        op.add("Não");
        p.setOpcoes(op);

        p.setCondicaoTexto("Sim");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Tirar foto da praga ou doenca");
        p.setTipoPergunta("ImageView");

        perguntasCondicionais.clear();
        perguntasCondicionais.add(p);
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Esta praga ou doenca e' muito difusa?");
        p.setTipoPergunta("RadioGroup");
        opcoes.clear();
        opcoes.add("Sim");
        opcoes.add("Não");
        p.setOpcoes(opcoes);
        perguntasCondicionais.add(p);
        p.setPerguntasCondicionais(perguntasCondicionais);
        ps.add(p);
        groupQuestions.put("couve", ps);
        /****************************************************Fim Couve***************************************************/

        /***************************************Tomate**********************************************/
        p = new Pergunta();
        ps = new ArrayList<>();

        p.setNomeDaPergunta("Indicar data do transplante para o tomate");
        p.setTipoPergunta("DatePicker");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar a fase de crescimento do tomate");
        opcoes.clear();
        opcoes.add("Pequeno");
        opcoes.add("Medio");
        opcoes.add("Grande");
        opcoes.add("Muito Grande");
        p.setOpcoes(opcoes);
        p.setTipoPergunta("RadioGroup");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre as plantas ");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre linhas para tomate em centimetros");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Quais são as inhas de tomate em centimetros ?");
        p.setTipoPergunta("EditText");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("As plantas de tomate tem incidencia de pragas e doenças?");
        p.setTipoPergunta("RadioGroup");
        op.clear();
        op.add("Sim");
        op.add("Não");
        p.setOpcoes(op);
        p.setCondicaoTexto("Sim");

        p = new Pergunta();
        p.setNomeDaPergunta("Tirar foto da praga ou doenca");
        p.setTipoPergunta("ImageView");

        perguntasCondicionais.clear();
        perguntasCondicionais.add(p);
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Esta praga ou doenca e' muito difusa?");
        p.setTipoPergunta("RadioGroup");
        opcoes.clear();
        opcoes.add("Sim");
        opcoes.add("Não");
        p.setOpcoes(opcoes);
        perguntasCondicionais.add(p);
        p.setPerguntasCondicionais(perguntasCondicionais);
        ps.add(p);

        groupQuestions.put("tomate", ps);
        /****************************************************Fim Tomate***************************************************/
    }
}