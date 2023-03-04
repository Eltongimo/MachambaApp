package com.example.machambaapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.ui.clientes.ClientesFragment;
import com.example.machambaapp.ui.home.HomeFragment;
import com.example.machambaapp.ui.produtorLider.ProdutorLiderFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;


   Menu menu;
   private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView=(findViewById(R.id.nav_view));
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navegation_drawer_open,R.string.navegation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState== null) {
            if(privilegios(navigationView)){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_admin);
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClientesFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_clientes);
            }
        }







    }


    private boolean privilegios(NavigationView navigationView) {
        Privilegios privilegios=new Privilegios();
        MenuItem menuItem;

        if(!privilegios.isAllAcessView()){
            menu=navigationView.getMenu();
            menuItem =menu.findItem(R.id.nav_admin);
            menuItem.setVisible(false);
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
            return false;
        }
      return true;
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_clientes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ClientesFragment()).commit();
                break;
            case R.id.nav_produtor_lider:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProdutorLiderFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}