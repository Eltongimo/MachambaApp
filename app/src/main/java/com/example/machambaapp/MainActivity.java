package com.example.machambaapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.Privilegios;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
   Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        privilegios(navigationView);



//        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
                 mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_admin, R.id.nav_clientes, R.id.nav_produtor_lider, R.id.nav_distrito,R.id.nav_posto_administrativo)
                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void privilegios(NavigationView navigationView) {
        Privilegios privilegios=new Privilegios();
        MenuItem menuItem;

        if(!privilegios.isAllAcessView()){
            menu=navigationView.getMenu();
            menuItem =menu.findItem(R.id.nav_distrito);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_produtor_lider);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_comunidade);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_cultura);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_etnia);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_localidade);
            menuItem.setVisible(false);
            menuItem =menu.findItem(R.id.nav_posto_administrativo);
            menuItem.setVisible(false);
        }

    }

    @Override
    public void onBackPressed() {
      super.onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}