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
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.datamodel.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity {

    NumberPicker numberPickerAno;
    AutoCompleteTextView etniaInput;
    ArrayAdapter<String> adapterEtnia;
    CheckBox checkBoxMale;
    static boolean isCamera;
    static boolean isPhotoCaptureForDocument;
    CheckBox checkBoxFeme;
    Uri urlImageGaleria;
    Uri urlImageCamera;
    Uri urlImageCaptureFace;
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
                        gen, et,distrito,localidade, posto,comunidade,"", "" );
                uploadImageDoc(cliente.getNome()+"-"+cliente.getApelido(),cliente);

                uploadImage(cliente.getNome()+"-"+cliente.getApelido(),cliente);
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
                            urlImage =data.getData();
                            imageDocumentUpload.setImageURI(urlImage);
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

                // if user opens camera and take a picture urlImageGaleria is assigned image Uri
                urlImageGaleria = Uri.parse(path);

                if (!isPhotoCaptureForDocument) {
                    urlImageCaptureFace=urlImageCamera;
                    imageUserUpload.setImageURI(urlImageGaleria);
                } else {
                    imageDocumentUpload.setImageURI(urlImage);
                }

            }
        }
    }
    private void uploadImage(String key, Cliente u) {

        try {
            // Cria e adiciona um novo usuário ao banco de dados

            if (urlImageGaleria != null) {

                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("clientes/"+key+"/"+key);

                UploadTask uploadTask = storageRef.putFile(urlImageGaleria);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                u.setImage(downloadUrl.toString());
                                DatabaseHelper.addCliente(u);
                                Toast.makeText(getApplicationContext(),"Usuario Adicionado com Sucesso", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(),"NULLL", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
        }
    }

    private void uploadImageDoc(String key, Cliente u) {

        try {
            // Cria e adiciona um novo usuário ao banco de dados

            if (urlImage != null) {

                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("clientes/"+key+"/documento"+key);

                UploadTask uploadTask = storageRef.putFile(urlImage);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                u.setDocumento(downloadUrl.toString());
                                DatabaseHelper.addCliente(u);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(),"NULLL", Toast.LENGTH_SHORT).show();
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
        checkBoxFeme=(CheckBox) findViewById(R.id.idCheckBoxfeme);
        checkBoxMale=(CheckBox) findViewById(R.id.idCheckBoxMale);
        etniaInput=(AutoCompleteTextView) findViewById(R.id.etnia_select);
        adapterEtnia = new ArrayAdapter<String>(this, R.layout.list_item_etnia, new ArrayList<>());
        etniaInput.setAdapter(adapterEtnia);

        numberPickerAno=(NumberPicker) findViewById(R.id.ano);

    }

}