package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.Sha;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;

public class AddCultura extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    Button button;
    EditText cultura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultura);


        button=(Button) findViewById(R.id.addCultura);
        cultura=(EditText) findViewById(R.id.idAddCultura);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper.addCultura(cultura.getText().toString(), "culturas");
                startActivity(new Intent(AddCultura.this, ActivityViewAddCultura.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddCultura.this,ActivityViewAddCultura.class));
        super.onBackPressed();
    }

    String getSha(String value){

        byte[] inpuData= value.toString().getBytes();
        byte[] outputData=new byte[0];

        try {
            outputData= new Sha().encryptSHA(inpuData,"SHA-384");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BigInteger shaData=new BigInteger(1,outputData);

        return shaData.toString(16);
    }
}