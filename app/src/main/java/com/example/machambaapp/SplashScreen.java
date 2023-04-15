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

        ps.add(new Pergunta("kjandjnaskdnakjsdnkajndakjanskdjnakdsnaksdjnakdnakjd", "EditText"));
        ps.add(new Pergunta("aksndkajdsnkajsndkjansdkajndkasdkj?", "CheckBox"));
        ps.add(new Pergunta("nakjdsnkajndsksajndkajndsknaskdjasnd?", "RadioGroup"));
        ps.add(new Pergunta("nakjdsnkajndsksajndkajndsknaskdjasnd?", "ImageView"));
        ps.add(new Pergunta("nakjdsnkajndsksajndkajndsknaskdjasnd?", "DatePicker"));
        ps.add(new Pergunta("nakjdsnkajndsksajndkajndsknaskdjasnd?", "NumberPicker"));


        formulario.setPerguntas(ps);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityPageStart.class));
                //startActivity(new Intent(SplashScreen.this, ResponderForm.class));
                finish();
            }
        }, 2000);
    }
}