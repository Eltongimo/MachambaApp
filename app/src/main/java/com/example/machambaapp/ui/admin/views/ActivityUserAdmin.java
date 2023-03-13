package com.example.machambaapp.ui.admin.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.machambaapp.ActivityUserPL;
import com.example.machambaapp.R;

public class ActivityUserAdmin extends AppCompatActivity {

    CardView cardViewUserPl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        cardViewUserPl=(CardView) findViewById(R.id.idCardUserPl);
        cardViewUserPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserAdmin.this, ActivityUserPL.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}