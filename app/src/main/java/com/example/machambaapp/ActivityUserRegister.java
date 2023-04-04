package com.example.machambaapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActivityUserRegister extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    Button addUser;
    String[] itemsDistrito = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);
    String[] itemsPostoAdministrativo = SplashScreen.postosAdministrativos.toArray(new String[SplashScreen.postosAdministrativos.size()]);
    String[] itemsLocalidade = SplashScreen.localiadades.toArray(new String[SplashScreen.localiadades.size()]);
    String[] itemsComunidade = SplashScreen.comunidades.toArray(new String[SplashScreen.comunidades.size()]);

    private FirebaseStorage storage;

    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    Dialog dialog;
    AutoCompleteTextView autoCompleteDistrito;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterItems;
    EditText editTextNome;
    EditText editSenha;
    EditText editPhone;
    static Uri urlImage;
    Button cancel;
    CardView galeria;
    CardView camera;
    ImageView imageViewUser;
    EditText editTextApelido;

    ProgressDialog loadingBar;

    private void setButtonsAndFields(){
        addUser = (Button) findViewById(R.id.registerUser);

        editTextNome = (EditText) findViewById(R.id.idNomePl);
        editSenha = (EditText) findViewById(R.id.passWordPlr);
        editPhone = (EditText) findViewById(R.id.idPhonePl);
        editTextApelido = (EditText) findViewById(R.id.idApelidoPl);
        imageViewUser = (ImageView) findViewById(R.id.idImageUserViewPl);
        storage = FirebaseStorage.getInstance();

        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.auto_selectPl);
        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.idPostoAdministrativoPl);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.idLocalidadePl);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.idComunidadePl);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterItems);

    }

    private void setButtonEvents(){
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Adicionar produtor líder");
                builder.setMessage("Deseja mesmo adicionar o produtor líder?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Cliente.UserPl u = new Cliente.UserPl();
                        u.setNome(editTextNome.getText().toString());
                        u.setPhone(editPhone.getText().toString());
                        u.setApelido(editTextApelido.getText().toString());
                        u.setSenha(editSenha.getText().toString());
                        u.setLocalidade(autoCompleteLocalidade.getText().toString());
                        u.setDistrito(autoCompleteDistrito.getText().toString());
                        u.setComunidade(autoCompleteComunidade.getText().toString());
                        u.setPostoAdministrativo(autoCompletePostoAdministrativo.getText().toString());

                        uploadImage(editTextNome.getText().toString()+"-"+editTextApelido.getText().toString(), u);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
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
                            Toast.makeText(ActivityUserRegister.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(ActivityUserRegister.this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setButtonsAndFields();

        setButtonEvents();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "val", null);
            urlImage = Uri.parse(path);
            imageViewUser.setImageURI(urlImage);
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


    @Override
    public void onBackPressed () {
        super.onBackPressed();
        finish();
    }
}

