package com.example.machambaapp.ui.admin.forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.R;
import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.adapter.FormAdapter;
import com.example.machambaapp.model.adapter.OfflineDBModelAdapter;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.example.machambaapp.model.helper.OfflineDB;

import java.util.ArrayList;

public class ShowForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_form);
        String formId = getIntent().getStringExtra("formId");

        ArrayList<OfflineDBModelForm>  data = new OfflineDB(this).getDataFromFormId(formId);

       setAdapter(data);

    }

    private void setAdapter(ArrayList<OfflineDBModelForm> d){
        RecyclerView recyclerView = findViewById(R.id.idRecyclerviewClient);
        FormAdapter formAdapter = new FormAdapter(this, d);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(formAdapter);
    }

    private TextView generateTextView(String text){
        TextView textView = new TextView(ShowForm.this);
        textView.setText(text);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 16, 0, 16); // Set top and bottom margins
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}