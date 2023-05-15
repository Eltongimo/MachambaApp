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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<String> distritos = DatabaseHelper.getLocation("distritos");
    public static ArrayList<String> provincias = new ArrayList<String>();
    public static int indexForm = 0;
    public static boolean showingConditional = false;

    public static View v;

    public static boolean displayPopUp = false;
    public static Pergunta currentQuestion = new Pergunta();
    public static int indexCondicional = 0;

    public static Resposta respostas = new Resposta();

    public static ArrayList<Formulario> formularios =  DatabaseHelper.getForms();
    public static Formulario formulario = new Formulario();

    public static ArrayList<String> culturas = DatabaseHelper.getLocation("culturas");
    public static ArrayList<String> postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
    public static ArrayList<String> localiadades = DatabaseHelper.getLocation("localidades");
    public static ArrayList<String> comunidades = DatabaseHelper.getLocation("comunidades");
    public static ArrayList<String> etnia = DatabaseHelper.getEtnia("etnias");
    public static Cliente.UserPl currentUser = new Cliente.UserPl();

    public static void updateComunidade(){
        comunidades = DatabaseHelper.getLocation("comunidades");
    }
    public static void updateDistrito(){
        distritos = DatabaseHelper.getLocation("distritos");
    }

    public static void updatePosto(){
        postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
    }
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

        ArrayList<String> generos = new ArrayList<>();
        generos.add("Masculino");
        generos.add("Femenino");

        Pergunta p = new Pergunta();
        p.setNomeDaPergunta("Genero ?");
        p.setTipoPergunta("RadioGroup");
        p.setOpcoes(generos);

        currentQuestion = p;

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Registar coordenadas geograficas");
        p.setTipoPergunta("EditText");

        ps.add(p);

        p = new Pergunta();

        p.setNomeDaPergunta("\n Canteiro \n \n Vamos comecar a avaliacao dum canteiro. Na pergunta seguinte pode selecionar uma ou mais culturas se o canteiro tiver consorciacao.");
        p.setTipoPergunta("ImageView");

        ps.add(p);

        p = new Pergunta();

        p.setNomeDaPergunta("Indicar a largura do Canteiro (metros)");
        p.setTipoPergunta("NumberPicker");

        ps.add(p);

        p.setNomeDaPergunta("Indicar o cumprimento do Canteiro (metros)");
        p.setTipoPergunta("NumberPicker");

        ps.add(p);

        p = new Pergunta();

        p.setNomeDaPergunta("Selecionar as culturas do canteiro");
        p.setTipoPergunta("CheckBox");
        p.setOpcoes(SplashScreen.culturas);
        ps.add(p);

        p = new Pergunta();

        p.setNomeDaPergunta("O canteiro tem uma camada de estrume e uma de cobertura morta?\n");

        ArrayList<String> respostas = new ArrayList<>();
        respostas.add("Camada de Estrume");
        respostas.add("Camada de Cobertura Morta");
        respostas.add("Nenhuma");


        p.setTipoPergunta("CheckBox");
        p.setOpcoes(respostas);

        ps.add(p);

        ArrayList<String> opcoes = new ArrayList<>();
        opcoes.add("Nao tem humidade");
        opcoes.add("tem mas o bolo nao fica bem firme");
        opcoes.add("O bolo fica bem firme, a humidade é boa!");
        opcoes.add("Esta a escorrer agua, esta molhado demais!");

        p = new Pergunta();

        p.setNomeDaPergunta("Medir a umidade do canteiro em varios pontos, perto da base das plantas. A humidade pode variar dependendo da area examinada");
        p.setTipoPergunta("RadioGroup1");
        p.setOpcoes(opcoes);

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Foi aplicado Bokashi neste canteiro?" + "\n\n" +
                "O bokashi e' vida! com um custo limitado o produtor pode aumentar a produtividade e melhorar a qualidade do seu solo. Veja o manual de bokashi da iDE");

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

        opcoes.add("sim");
        opcoes.add("Não");

        p.setOpcoes(opcoes);

        p.setTipoPergunta("RadioGroup");

        // ******************************

        // condicional - se o agricultor esta a usar pestiida Botanico

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

        p = new Pergunta();

        p.setNomeDaPergunta("Indicar data do transplante para alface");
        p.setTipoPergunta("DatePicker");
        ps.add(p);


        p = new Pergunta();
        p.setNomeDaPergunta("Indicar a fase de crescimento do Alface");
        p.setTipoPergunta("Slider");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre as plantas ");
        p.setTipoPergunta("EditText");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Indicar o espaçamento entre linhas para alface em centimetros");
        p.setTipoPergunta("NumberPicker");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Quais são as inhas de alface em centimetros ?");
        p.setTipoPergunta("NumberPicker");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("As plantas de alface tem incidencia de pragas e doenças?");
        p.setTipoPergunta("RadioGroup");

        ArrayList<String> op = new ArrayList<>();
        op.add("Sim");
        op.add("Não");
        p.setOpcoes(op);

        p.setCondicaoTexto("Sim");

        Pergunta per = new Pergunta();
        per.setNomeDaPergunta("Tirar foto da praga ou doenca");
        per.setTipoPergunta("ImageView");

        ArrayList<Pergunta> perguntasCondicionais = new ArrayList<>();
        perguntasCondicionais.add(per);

        per = new Pergunta();
        per.setNomeDaPergunta("Esta praga ou doenca e' muito diffusa?");
        per.setTipoPergunta("RadioGroup");

        ArrayList<String> oo = new ArrayList<>();
        oo.add("Sim");
        oo.add("Não");

        per.setOpcoes(oo);

        perguntasCondicionais.add(per);

        p.setPerguntasCondicionais(perguntasCondicionais);
        ps.add(p);

        formulario.setPerguntas(ps);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityPageStart.class));
              //  startActivity(new Intent(SplashScreen.this, ResponderForm.class));
                finish();
            }
        }, 2000);
    }
}