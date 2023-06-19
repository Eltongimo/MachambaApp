package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;

import com.example.machambaapp.model.adapter.OfflineDBModelAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.model.helper.OfflineDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FormulariosRespondidos extends AppCompatActivity {

    Button registerEtnia;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    SearchView searchView;
    ProgressDialog loadingBar;

    private static ArrayList<Etnia> etnias = new ArrayList<Etnia>();

    @Override
    protected void onResume(){
        super.onResume();
    }

    private ArrayList<OfflineDBModelForm> removeDuplicates(ArrayList<OfflineDBModelForm> data){

        ArrayList<OfflineDBModelForm> newArray = new ArrayList<>();


        for (OfflineDBModelForm d : data){
            boolean a = false;
            for (OfflineDBModelForm dd : newArray){
                if (dd.getFormId().equals(d.getFormId())){
                    a = true;
                    break;
                }
            }

            if (!a){
                newArray.add(d);
            }
        }

        return newArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios_respondidos);

        registerEtnia = (Button) findViewById(R.id.registerEtnia);
        searchView = (SearchView) findViewById(R.id.idSearch);

        OfflineDB db = new OfflineDB(FormulariosRespondidos.this);
        ArrayList<OfflineDBModelForm> forms = db.readForms();

        forms = removeDuplicates(forms);

        setAdapter(forms);
    }

    private void setAdapter(ArrayList<OfflineDBModelForm> forms){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewEtnia);
        OfflineDBModelAdapter formsAdapter = new OfflineDBModelAdapter(this, forms);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(formsAdapter);
    }

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }
}