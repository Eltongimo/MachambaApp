package com.example.machambaapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;

import java.io.ByteArrayOutputStream;

public class ActivityUserRegister extends AppCompatActivity {
     Button addUser;

    String[] itemsDistrito = {"Mecúfi"};
    String[] itemsPostoAdministrativo = {"Murrebuê"};
    String[] itemsLocalidade = {"L-Muitua"};
    String[] itemsComunidade = {"Sicura B", "Muitua Sede", "Murripa","Singura A"};
    AutoCompleteTextView autoCompleteDistrito;
    Dialog dialog;
    AutoCompleteTextView autoCompletePostoAdministrativo;
    AutoCompleteTextView autoCompleteLocalidade;
    AutoCompleteTextView autoCompleteComunidade;
    ArrayAdapter<String> adapterItems;

    EditText editTextNome;
    static Uri urlImage;
    Button cancel;
    CardView galeria;
    CardView camera;
    ImageView imageViewUser;
    EditText editTextApelido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        addUser=(Button) findViewById(R.id.registerUser);
        editTextNome=(EditText) findViewById(R.id.idNome);
        editTextApelido=(EditText) findViewById(R.id.idApelido);
        imageViewUser=(ImageView) findViewById(R.id.idImageUserView);
        autoCompleteDistrito = (AutoCompleteTextView) findViewById(R.id.auto_select);

        autoCompletePostoAdministrativo = (AutoCompleteTextView) findViewById(R.id.idPostoAdministrativo);
        autoCompleteLocalidade = (AutoCompleteTextView) findViewById(R.id.idLocalidade);
        autoCompleteComunidade = (AutoCompleteTextView) findViewById(R.id.idComunidade);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_distrito, itemsDistrito);
        autoCompleteDistrito.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_posto_administrativo, itemsPostoAdministrativo);
        autoCompletePostoAdministrativo.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_localidade, itemsLocalidade);
        autoCompleteLocalidade.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_comunidade, itemsComunidade);
        autoCompleteComunidade.setAdapter(adapterItems);

        addUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                if(editTextApelido.getText().equals("") || editTextNome.getText().equals("")){
                    editTextNome.setError(" Campo Vazio ");
                    editTextApelido.setError(" Campo Vazio ");
                }else{
                    DB db =new DB();
                    db.addArrayListUserPl(editTextNome.getText().toString(),editTextApelido.getText().toString(), urlImage);
                    Intent intent=new Intent(ActivityUserRegister.this, ActivityUserPL.class);
                    startActivity(intent);

                }

             }
         });


        ActivityResultLauncher<Intent> activityResultLauncherImageUsers = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){

                            Intent data=result.getData();
                            urlImage=data.getData();
                            imageViewUser.setImageURI(urlImage);
                        }else {
                            Toast.makeText(ActivityUserRegister.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog =new Dialog(ActivityUserRegister.this);
                dialog.setContentView(R.layout.alert_view_dialog_choose_camera);


                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;

                camera=dialog.findViewById(R.id.idCardCamera);
                galeria=dialog.findViewById(R.id.idCardGaleria);
                cancel=dialog.findViewById(R.id.alertButton);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(openCamera,1);
                        dialog.dismiss();
                    }
                });

                galeria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

                dialog.show();
            }



        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode==RESULT_OK) {
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path= MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
            urlImage = Uri.parse(path);
            imageViewUser.setImageURI(urlImage);
        }

    }
}


