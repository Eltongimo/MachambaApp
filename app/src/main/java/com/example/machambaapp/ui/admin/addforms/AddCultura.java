package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityUserPL;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                DatabaseHelper.addLocations(cultura.getText().toString(), "culturas","","");
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}