package com.example.machambaapp;

import static android.os.Build.VERSION.SDK_INT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.DialogView;

import java.text.DateFormat;
import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    NumberPicker numberPickerDia;
    NumberPicker numberPickerMes;
    NumberPicker numberPickerAno;
    AutoCompleteTextView etniaInput;
    ArrayAdapter<String> adapterEtnia;
    CheckBox checkBoxMale;
    CheckBox checkBoxFeme;
    static Uri url;
    Button buttonRegisterUser;
    Button ok;
    ImageView imageUserUpload;
    ImageView imageDocumentUpload;
    TextView textFullName;
    EditText txtIdade;

    Dialog dialog;

    String[] listEtnia = {" Macua","Makonde","Mwani","Swahili","Sena","Shona","Ndau","Chuwabo","Nyungwe","Tsonga","Changana","Bitonga","Yaos","Outros"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Calendar c=Calendar.getInstance();
        getIdView();

        numberPickerDia.setMinValue(1);
        numberPickerDia.setMaxValue(31);

        numberPickerMes.setMinValue(1);
        numberPickerMes.setMaxValue(c.MONTH);

        numberPickerAno.setMinValue(1900);
        numberPickerAno.setMaxValue(c.YEAR);

        /// dialog
        dialog =new Dialog(AddUserActivity.this);
        dialog.setContentView(R.layout.alert_view_dialog);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
        }
         dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         dialog.setCancelable(false);
         dialog.getWindow().getAttributes().windowAnimations=R.style.animation;

         ok=dialog.findViewById(R.id.confirm_ok);
         ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DB db=new DB();
                 db.addArrayListClient(textFullName.getText().toString(),R.drawable.baseline_person_pin_24);

                 Toast.makeText(AddUserActivity.this, "Usuario registado com Sucesso", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(AddUserActivity.this,ActivityListClient.class));

                 dialog.dismiss();
             }
         });



         // fim dialog

        txtIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment  dialogFragment =new DialogView();
                dialogFragment.show(getSupportFragmentManager(),"DataPicker");

            }
        });


        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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

    private void getIdView() {

        imageUserUpload =(ImageView) findViewById(R.id.imageAdd);
        textFullName=(TextView) findViewById(R.id.idFullNameClient);
        imageDocumentUpload=(ImageView) findViewById(R.id.uploadImageDocument);
        buttonRegisterUser =(Button) findViewById(R.id.registerUser);

        //txtIdade=(EditText) findViewById(R.id.idIdade);
        checkBoxFeme=(CheckBox) findViewById(R.id.idCheckBoxfeme);
        checkBoxMale=(CheckBox) findViewById(R.id.idCheckBoxMale);

        etniaInput=(AutoCompleteTextView) findViewById(R.id.etnia_select);
        adapterEtnia = new ArrayAdapter<String>(this, R.layout.list_item_etnia, listEtnia);
        etniaInput.setAdapter(adapterEtnia);

        numberPickerAno=(NumberPicker) findViewById(R.id.ano);
        numberPickerDia=(NumberPicker) findViewById(R.id.dia);
        numberPickerMes=(NumberPicker) findViewById(R.id.mes);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        String currentDate= DateFormat.getDateInstance().format(c.getTime());
        txtIdade.setText(currentDate);
    }
}