package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.model.Sha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;

public class AddEtnia extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    EditText etnia;
    Button addEtnia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etnia);


        etnia = (EditText) findViewById(R.id.nomeEtnia);
        addEtnia = (Button) findViewById(R.id.addEtnia);
        
        addEtnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("etnias").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sha=getSha(etnia.getText().toString());
                        databaseReference.child("etnias").child(sha).child("nome").setValue(etnia.getText().toString());
                        startActivity(new Intent(AddEtnia.this,ActivityViewEtnia.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddEtnia.this,ActivityViewEtnia.class));
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