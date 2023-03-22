package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<String> distritos = DatabaseHelper.getLocation("distritos");
    public static ArrayList<String> postosAdministrativos = DatabaseHelper.getLocation("postosAdministrativos");
    public static ArrayList<String> localiadades = DatabaseHelper.getLocation("localidades");
    public static ArrayList<String> comunidades = DatabaseHelper.getLocation("comunidades");

    public static ArrayList<String> etnia = DatabaseHelper.getEtnia("etnias");

    public static Cliente.UserPl currentUser = new Cliente.UserPl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, ActivityPageStart.class));
                finish();
            }
        }, 2000);
    }
}