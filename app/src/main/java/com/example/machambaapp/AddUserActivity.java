package com.example.machambaapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.adapter.ClientAdapter;

public class AddUserActivity extends AppCompatActivity {

    NumberPicker numberPickerOne;
    NumberPicker numberPickerTwo;
    AutoCompleteTextView etniaInput;
    ArrayAdapter<String> adapterEtnia;
    CheckBox checkBoxMale;
    CheckBox checkBoxFeme;
    static Uri url;
    Button buttonRegisterUser;
    ImageView imageUserUpload;
    ImageView imageDocumentUpload;
    TextView textFullName;



    String[] itemsDistrito = {"Americano","Mocan"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        imageUserUpload =(ImageView) findViewById(R.id.imageAdd);
        textFullName=(TextView) findViewById(R.id.idFullNameClient);
        imageDocumentUpload=(ImageView) findViewById(R.id.uploadImageDocument);
        buttonRegisterUser =(Button) findViewById(R.id.registerUser);
        numberPickerOne=(NumberPicker) findViewById(R.id.picherOne);
        numberPickerTwo=(NumberPicker) findViewById(R.id.picherTwo);

        checkBoxFeme=(CheckBox) findViewById(R.id.idCheckBoxfeme);
        checkBoxMale=(CheckBox) findViewById(R.id.idCheckBoxMale);

        numberPickerOne.setMaxValue(9);
        numberPickerOne.setMinValue(0);
        numberPickerTwo.setMaxValue(9);
        numberPickerTwo.setMinValue(0);
        etniaInput=(AutoCompleteTextView) findViewById(R.id.etnia_select);
        adapterEtnia = new ArrayAdapter<String>(this, R.layout.list_item_etnia, itemsDistrito);
        etniaInput.setAdapter(adapterEtnia);



        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               DB db=new DB();
               db.addArrayListClient(textFullName.getText().toString(),R.drawable.baseline_person_pin_24);






                Toast.makeText(AddUserActivity.this, "Usuario registado com Sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddUserActivity.this,ActivityListClient.class));
            }
        });

        checkBoxMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddUserActivity.this, "Masculino", Toast.LENGTH_SHORT).show();
                checkBoxMale.setChecked(true);
                checkBoxFeme.setChecked(false);
            }
        });

        checkBoxFeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddUserActivity.this, "femenino", Toast.LENGTH_SHORT).show();

                checkBoxMale.setChecked(false);
                checkBoxFeme.setChecked(true);
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                         if(result.getResultCode()== Activity.RESULT_OK){

                             Intent data=result.getData();
                             url=data.getData();
                             imageUserUpload.setImageURI(url);
                         }else {
                             Toast.makeText(AddUserActivity.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                         }
                    }
                }
        );

        ActivityResultLauncher<Intent> activityResultLauncherImageDocuments = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){

                            Intent data=result.getData();
                            url=data.getData();
                            imageDocumentUpload.setImageURI(url);
                        }else {
                            Toast.makeText(AddUserActivity.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imageUserUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncherImageUsers.launch(photoPicker);

            }
        });

        imageDocumentUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncherImageDocuments.launch(photoPicker);

            }
        });

    }


}