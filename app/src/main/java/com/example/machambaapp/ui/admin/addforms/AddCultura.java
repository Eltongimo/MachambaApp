package com.example.machambaapp.ui.admin.addforms;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.R;

import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.ui.admin.views.ActivityUserPL;
import com.example.machambaapp.ui.admin.views.ActivityViewAddCultura;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddCultura extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    Button button;
    EditText cultura;
    ImageView imageAddCultura;
    static Uri urlImage;
    Dialog dialog;
    Uri urlImageCaptureFace;

    Button cancel;
    CardView galeria;
    CardView camera;
    static boolean isCamera;
    ImageView imageUserUpload;
    ImageView imageDocumentUpload;
    static boolean isPhotoCaptureForDocument;
    Uri urlImageGaleria;
    Uri urlImageCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultura);

        button=(Button) findViewById(R.id.addCultura);
        cultura=(EditText) findViewById(R.id.idAddCultura);
        imageAddCultura = (ImageView) findViewById(R.id.imageAddCultura);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Adicionar Cultura!");
                builder.setMessage("Deseja mesmo adicionar a cultura?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DatabaseHelper.addLocations(cultura.getText().toString(), "culturas","","");
                        Cultura c = new Cultura();
                        c.setCultura(cultura.getText().toString());
                        uploadImage(cultura.getText().toString().toString(), c);
                        Toast.makeText(getApplicationContext(), "Cultura adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
                            imageAddCultura.setImageURI(urlImageGaleria);
                        }else {
                            Toast.makeText(AddCultura.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        imageAddCultura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhotoCaptureForDocument=false;
                dialog =new Dialog(AddCultura.this);
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
                    imageAddCultura.setImageURI(urlImageGaleria);
                } else {
                    imageDocumentUpload.setImageURI(urlImage);
                }

            }
        }
    }
    private void uploadImage(String key, Cultura c) {

        try {
            // Cria e adiciona uma nova cultura

            if (urlImageGaleria != null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("culturas/"+key);

                UploadTask uploadTask = storageRef.putFile(urlImageGaleria);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                // Use the download URL to display the image
                                c.setImagem(downloadUrl.toString());
                                Toast.makeText(getApplicationContext(), c.getImagem(), Toast.LENGTH_SHORT).show();

                                //After Uploading the image now Im uploading the Culture to RTDB
                                DatabaseHelper.addCultura(c);

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao gravar cultura", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}