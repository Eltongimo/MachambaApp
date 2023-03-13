package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

import com.example.machambaapp.ui.admin.views.ActivityViewDistrito;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;

public class AddDistrito extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    Button btnRegistar;
    EditText nomeDistrito;

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_distrito);

        nomeDistrito = (EditText) findViewById(R.id.nomeDistrito);
        btnRegistar = (Button) findViewById(R.id.addDistrito);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper.addCultura(nomeDistrito.getText().toString(), "distritos");
                finish();
                //    startActivity(new Intent(AddDistrito.this, ActivityViewDistrito.class));


            }
        });
    }
}

