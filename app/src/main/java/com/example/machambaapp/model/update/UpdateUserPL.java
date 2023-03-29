package com.example.machambaapp.model.update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.machambaapp.ActivityUserRegister;
import com.example.machambaapp.ui.admin.views.ActivityUserPL;
import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateUserPL extends AppCompatActivity {

    Button update;
    String[] itemsDistrito = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);
    String[] itemsPostoAdministrativo = SplashScreen.postosAdministrativos.toArray(new String[SplashScreen.postosAdministrativos.size()]);
    String [] itemsLocalidade = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);;
    String [] itemsComunidade = SplashScreen.comunidades.toArray(new String[SplashScreen.comunidades.size()]);;
    AutoCompleteTextView autoCompleteDistrito;
    Dialog dialog;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterDistrito;
    ArrayAdapter<String> adapterLocalidade;
    ArrayAdapter<String> adapterComunidade;
    ArrayAdapter<String> adapterPosto;
    EditText editTextNome;
    EditText editSenha;
    EditText editPhone;
    static Uri urlImage;
    Button cancel;
    CardView galeria;
    CardView camera;
    ImageView imageViewUser;
    EditText editTextApelido;
    private FirebaseStorage storage;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private String imageLink = "";


    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    private void setAttributesFomPutExtra(){
        autoCompleteDistrito.setHint(getIntent().getStringExtra("distrito"));
        autoCompletePostoAdministrativo.setHint(getIntent().getStringExtra("posto"));
        autoCompleteLocalidade.setHint(getIntent().getStringExtra("localidade"));
        autoCompleteComunidade.setHint(getIntent().getStringExtra("comunidade"));
        editTextNome.setText(getIntent().getStringExtra("nome"));
        editSenha.setText(getIntent().getStringExtra("senha"));
        editPhone.setText(getIntent().getStringExtra("phone"));
        editTextApelido.setText(getIntent().getStringExtra("apelido"));
        imageLink = getIntent().getStringExtra("image");
    }

    private void InitializeFields(){
        update = (Button) findViewById(R.id.updateUserPL);
        editTextNome = (EditText) findViewById(R.id.nomePl) ;
        editSenha = (EditText) findViewById(R.id.passwordPl);
        editPhone = (EditText) findViewById(R.id.phonePl);
        editTextApelido = (EditText) findViewById(R.id.apelidoPl);
        storage = FirebaseStorage.getInstance();
        imageViewUser = (ImageView) findViewById(R.id.idImageUserViewPl);

        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.distritoPL);
        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.postoadmnistrativoPL);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.localidadePl);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.comunidadePL);


        adapterDistrito = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterDistrito);

        adapterPosto = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterPosto);

        adapterLocalidade = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterLocalidade);

        adapterComunidade = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterComunidade);
    }

    private Cliente.UserPl setUpdatesUser(){
        Cliente.UserPl u = new Cliente.UserPl(editTextNome.getText().toString(),
                editTextApelido.getText().toString(),
                editSenha.getText().toString(),"",
                editPhone.getText().toString(),
                autoCompleteDistrito.getText().toString(),
                autoCompleteLocalidade.getText().toString(),
                autoCompletePostoAdministrativo.getText().toString(),
                autoCompleteComunidade.getText().toString(),
                getIntent().getStringExtra("key"), "");
        return u;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_userpl);

        InitializeFields();
        setAttributesFomPutExtra();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente.UserPl u = new Cliente.UserPl();
                u = setUpdatesUser();

                // Upload new Image to firebase storage and then update user on RTDB
                UpdateImageStorage(u.getNome()+"-"+u.getApelido(),u);
                finish();
                startActivity(new Intent(UpdateUserPL.this, ActivityUserPL.class));
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            urlImage = data.getData();
                            imageViewUser.setImageURI(urlImage);
                        } else {
                            Toast.makeText(UpdateUserPL.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(UpdateUserPL.this);
                dialog.setContentView(R.layout.alert_view_dialog_choose_camera);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                camera = dialog.findViewById(R.id.idCardCamera);
                galeria = dialog.findViewById(R.id.idCardGaleria);
                cancel = dialog.findViewById(R.id.alertButton);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(openCamera, 1);
                        dialog.dismiss();
                    }
                });

                galeria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPicker = new Intent(Intent.ACTION_PICK);
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

                dialog.show();
            }
        });
    }

    private void UpdateImageStorage(String key, Cliente.UserPl u){
        try {

            if (urlImage != null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("imagens/"+key);

                storageRef.delete();

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
                                DatabaseHelper.updateUserPl(u,u.getKey());
                                Toast.makeText(getApplicationContext(), "Usuario Actualizado com sucesso", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao gravar usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e) {
        }
    }
    private void uploadImage(String key, Cliente.UserPl u) {

        try {
            // Cria e adiciona um novo usuário ao banco de dados

            if (urlImage != null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("imagens/"+key);

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

                                //After Uploading the image now Im uploading the user to RTDB
                                DatabaseHelper.addUserPl(u);

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao gravar usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e) {
        }
    }


}