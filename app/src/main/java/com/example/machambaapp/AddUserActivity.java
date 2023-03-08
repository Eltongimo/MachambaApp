package com.example.machambaapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.machambaapp.model.Sha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");


    NumberPicker numberPickerDia;
    NumberPicker numberPickerMes;
    NumberPicker numberPickerAno;
    AutoCompleteTextView etniaInput;
    ArrayAdapter<String> adapterEtnia;
    CheckBox checkBoxMale;
    static boolean isCamera;
    static boolean isPhotoCaptureForDocument;
    CheckBox checkBoxFeme;
    static Uri urlImageGaleria;
    static Uri urlImageCamera;
    static Uri urlImageCaptureFace;

    Button buttonRegisterUser;
    Button ok;
    Button cancel;
    CardView galeria;
    CardView camera;
    ImageView imageUserUpload;
    ImageView imageDocumentUpload;
    TextView textNome;
    TextView textApelido;
    EditText txtIdade;

    static String genero="";

    EditText editTextPhone;
    CheckBox checkBoxMacho;
    CheckBox checkBoxFemenino;
    Dialog dialog;

    String[] listEtnia = {" Macua","Makonde","Mwani","Swahili","Sena","Shona","Ndau","Chuwabo","Nyungwe","Tsonga","Changana","Bitonga","Yaos","Outros"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Calendar c=Calendar.getInstance();



        editTextPhone=(EditText) findViewById(R.id.idPhoneUser);

        getIdView();

        numberPickerAno.setMinValue(1900);
        numberPickerAno.setMaxValue(2040);

        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                            db.addArrayListClient(
                                    textNome.getText().toString(),
                                    textApelido.getText().toString(),
                                    editTextPhone.getText().toString(),
                                    "","",urlImageCaptureFace,urlImageCaptureFace);


                        databaseReference.child("clientes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    String sha=getSha(editTextPhone.getText().toString());
                                    databaseReference.child("clientes").child(sha).child("telemovel").setValue(editTextPhone.getText().toString());
                                    databaseReference.child("clientes").child(sha).child("nome").setValue(textNome.getText().toString());
                                    databaseReference.child("clientes").child(sha).child("apelido").setValue(textApelido.getText().toString());
                                    databaseReference.child("clientes").child(sha).child("genero").setValue(genero);
                                    databaseReference.child("clientes").child(sha).child("imagemDocumento").setValue(""+imageDocumentUpload);
                                    databaseReference.child("clientes").child(sha).child("imagemUsuario").setValue(""+imageUserUpload);

                                    startActivity(new Intent(AddUserActivity.this,ActivityListClient.class));
                                }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });





                        Toast.makeText(AddUserActivity.this, "Usuario registado com Sucesso", Toast.LENGTH_SHORT).show();

                        Toast.makeText(AddUserActivity.this, "Usuário registado com Sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddUserActivity.this,ActivityListClient.class));

                        dialog.dismiss();
                    }




                });

                dialog.show();
            }
        });

        checkBoxMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddUserActivity.this, "Masculino", Toast.LENGTH_SHORT).show();
                checkBoxMale.setChecked(true);
                checkBoxFeme.setChecked(false);
                    genero="Masculino";
            }
        });

        checkBoxFeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AddUserActivity.this, "femenino", Toast.LENGTH_SHORT).show();
                genero="Femenino";

                Toast.makeText(AddUserActivity.this, "Feminino", Toast.LENGTH_SHORT).show();


                checkBoxMale.setChecked(false);
                checkBoxFeme.setChecked(true);
            }
        });



        //Mario =content://media/external/images/media/33
       // Barata =android.graphics.Bitmap@e085dda

        ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                         if(result.getResultCode()== Activity.RESULT_OK){

                             Intent data=result.getData();
                             urlImageGaleria =data.getData();
                             imageUserUpload.setImageURI(urlImageGaleria);
                             System.out.println(" Mario ="+ urlImageGaleria);
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
                            urlImageGaleria =data.getData();
                            imageDocumentUpload.setImageURI(urlImageGaleria);
                            System.out.println(" Mario ="+ urlImageGaleria);
                        }else {
                            Toast.makeText(AddUserActivity.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );



        imageUserUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 isPhotoCaptureForDocument=false;
                dialog =new Dialog(AddUserActivity.this);
                dialog.setContentView(R.layout.alert_view_dialog_choose_camera);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                camera=dialog.findViewById(R.id.idCardCamera);
                cancel=dialog.findViewById(R.id.alertButton);
                galeria=dialog.findViewById(R.id.idCardGaleria);

                galeria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCamera=false;
                        Intent photoPicker=new Intent(Intent.ACTION_PICK);
                        photoPicker.setType("image/*");
                        activityResultLauncherImageUsers.launch(photoPicker);
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCamera=true;
                        Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(openCamera,1);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        imageDocumentUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPhotoCaptureForDocument=true;
                dialog =new Dialog(AddUserActivity.this);
                dialog.setContentView(R.layout.alert_view_dialog_choose_camera);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                galeria=dialog.findViewById(R.id.idCardGaleria);
                galeria=dialog.findViewById(R.id.idCardGaleria);

                cancel=dialog.findViewById(R.id.alertButton);
                camera=dialog.findViewById(R.id.idCardCamera);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCamera=true;
                        Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(openCamera,1);
                        dialog.dismiss();
                    }
                });



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                galeria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isCamera=false;
                   Intent photoPicker=new Intent(Intent.ACTION_PICK);
                    photoPicker.setType("image/*");
                    activityResultLauncherImageDocuments.launch(photoPicker);
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode==RESULT_OK) {
            if (isCamera) {
                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path= MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
                urlImageCamera = Uri.parse(path);

                if (!isPhotoCaptureForDocument) {
                    urlImageCaptureFace=urlImageCamera;
                    imageUserUpload.setImageURI(urlImageCamera);
                } else {

                    imageDocumentUpload.setImageURI(urlImageCamera);
                }

            }
        }

    }

    private void getIdView() {

        imageUserUpload =(ImageView) findViewById(R.id.imageAdd);
        textNome =(TextView) findViewById(R.id.idFullNameClient);
        textApelido=(TextView) findViewById(R.id.idApelidoCli) ;
        imageDocumentUpload=(ImageView) findViewById(R.id.uploadImageDocument);
        buttonRegisterUser =(Button) findViewById(R.id.registerUser);

        //txtIdade=(EditText) findViewById(R.id.idIdade);
        checkBoxFeme=(CheckBox) findViewById(R.id.idCheckBoxfeme);
        checkBoxMale=(CheckBox) findViewById(R.id.idCheckBoxMale);

        etniaInput=(AutoCompleteTextView) findViewById(R.id.etnia_select);
        adapterEtnia = new ArrayAdapter<String>(this, R.layout.list_item_etnia, listEtnia);
        etniaInput.setAdapter(adapterEtnia);

        numberPickerAno=(NumberPicker) findViewById(R.id.ano);

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