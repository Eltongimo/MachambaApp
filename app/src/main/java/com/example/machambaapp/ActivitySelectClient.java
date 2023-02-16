package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.model.DataClassClient;
import com.example.machambaapp.model.adapter.ClientAdapter;

public class ActivitySelectClient extends AppCompatActivity {

    EditText editName;
    ImageView imageFaceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);


  }



}