package com.example.machambaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.forms.ResponderForm;
import com.example.machambaapp.ui.clientes.ActivitySelectClient;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<String> distritos = DatabaseHelper.getLocation("distritos");
    public static ArrayList<String> provincias = new ArrayList<String>();
    public static int indexForm = 0;

    public static ArrayList<Formulario> formularios =  DatabaseHelper.getForms();
    public static Formulario formulario = new Formulario();
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
        p.setTipoPergunta("CheckBox1");
        ps.add(p);

        p = new Pergunta();

        p.setNomeDaPergunta("\nVamos a analizar um canteiro de metros por metros que tem plantas de\n" +
                "\n"+ " O canteiro tem uma camada de estrume e uma de cobertura morta?"
        );

        p.setNomeDaPergunta("O canteiro tem uma camada de estrume e uma de cobertura morta?\n");
        p.setTipoPergunta("CheckBox2");
        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Medir a umidade do canteiro em varios pontos, perto da base das plantas. A humidade pode variar dependendo da area examinada");
        p.setTipoPergunta("RadioGroup1");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Medir a umidade do canteiro em varios pontos, perto da base das plantas. A humidade pode variar dependendo da area examinada");
        p.setTipoPergunta("RadioGroup1");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("Foi aplicado Bokashi neste canteiro?");
        p.setTipoPergunta("RadioGroup2");

        ps.add(p);

        p = new Pergunta();
        p.setNomeDaPergunta("O produtor esta a aplicar pesticida botanico neste canteiro?");
        p.setTipoPergunta("RadioGroup2");

        ps.add(p);


        formulario.setPerguntas(ps);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityPageStart.class));
                //startActivity(new Intent(SplashScreen.this, ActivitySelectClient.class));
                finish();
            }
        }, 2000);
    }
}