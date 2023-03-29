package com.example.machambaapp.ui.admin.addforms;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.ActivitySelectClient;
import com.example.machambaapp.ActivityUserRegister;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.update.UpdateCultura;
import com.example.machambaapp.ui.admin.views.ActivityUserPL;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.example.machambaapp.ui.produtorLider.ProdutorLiderFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AddUserActivity extends AppCompatActivity {

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
    EditText txtAno;


    static String genero="";

    EditText editTextPhone, editTextNome, editTextApelido;
    CheckBox generoMasc;
    CheckBox generoFem;
    Dialog dialog;
    static Uri urlImage;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    ArrayAdapter<String> adapterItems;
    AutoCompleteTextView autoEtnias;
    AutoCompleteTextView autoCompleteDistrito;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;

    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getIdView();
        Calendar c=Calendar.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        editTextPhone=(EditText) findViewById(R.id.idPhoneUser);
        editTextNome = (EditText) findViewById(R.id.idFullNameClient);
        editTextApelido = (EditText) findViewById(R.id.idApelidoCli);
        buttonRegisterUser = (Button) findViewById(R.id.registerUser);
        numberPickerAno = (NumberPicker) findViewById(R.id.ano);
        generoFem =  (CheckBox) findViewById(R.id.idCheckBoxfeme);
        generoMasc = (CheckBox) findViewById(R.id.idCheckBoxMale);
        autoEtnias = (AutoCompleteTextView) findViewById(R.id.etnia_select);
        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.auto_distrito);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.auto_localidades);
        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.auto_postos);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.auto_comunidades);

        String [] etnias = SplashScreen.etnia.toArray(new String[SplashScreen.etnia.size()]);
        String[] itemsDistrito = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);
        String[] itemsPostoAdministrativo = SplashScreen.postosAdministrativos.toArray(new String[SplashScreen.postosAdministrativos.size()]);
        String [] itemsLocalidade = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);
        String [] itemsComunidade = SplashScreen.comunidades.toArray(new String[SplashScreen.comunidades.size()]);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterItems);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterItems);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterItems);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterItems);

        adapterItems = new ArrayAdapter<>(this, R.layout.list_item_etnia, etnias);
        autoEtnias.setAdapter(adapterItems);

        generoMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generoFem.setChecked(false);
            }
        });

        generoFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generoMasc.setChecked(false);
            }
        });

        numberPickerAno.setMinValue(1900);
        numberPickerAno.setMaxValue(2040);

        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gen = generoFem.isChecked() ? "Feminino" : "Masculino";
                String nome = editTextNome.getText().toString();
                String apelido = editTextApelido.getText().toString();
                String phone = editTextPhone.getText().toString();
                String et = autoEtnias.getText().toString();
                String distrito = autoCompleteDistrito.getText().toString();
                String comunidade = autoCompleteComunidade.getText().toString();
                String localidade = autoCompleteLocalidade.getText().toString();
                String posto = autoCompletePostoAdministrativo.getText().toString();
                Cliente cliente = new Cliente(nome,apelido,phone,
                        new String(numberPickerAno.getValue()+""),
                        gen, et,distrito,localidade, posto,comunidade );
                DatabaseHelper.addClientes(cliente);
                finish();
                startActivity(new Intent(AddUserActivity.this, ActivitySelectClient.class));
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                         if(result.getResultCode()== Activity.RESULT_OK){

                             Intent data=result.getData();
                             urlImageGaleria =data.getData();
                             imageUserUpload.setImageURI(urlImageGaleria);
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
    private void uploadImage(String key, Cliente.UserPl u) {

        try {
            // Cria e adiciona um novo usuário ao banco de dados

            if (urlImage != null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("clientes/"+editTextNome.getText().toString() +"-"+editTextApelido.getText().toString()+"/"+key);

                UploadTask uploadTask = storageRef.putFile(urlImage);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                // Use the download URL to display the image
                                u.setImage(downloadUrl.toString());
                                Toast.makeText(getApplicationContext(), u.getImage(), Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e) {
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
        adapterEtnia = new ArrayAdapter<String>(this, R.layout.list_item_etnia, new ArrayList<>());
        etniaInput.setAdapter(adapterEtnia);

        numberPickerAno=(NumberPicker) findViewById(R.id.ano);

    }

}