package com.example.machambaapp.ui.admin.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.R;
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
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);

       // new OfflineDB(this).getUsers();

        for (OfflineDBModelForm d : data){
            parentLayout.addView(generateTextView(d.getPergunta()));
            parentLayout.addView(generateTextView(d.getResposta()));
        }
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