package com.example.machambaapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.SavedStateHandle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.Sha;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

 public class ActivityUserRegister extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");


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
    EditText editSenha;
    EditText editPhone;
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

        editTextNome=(EditText) findViewById(R.id.idNomePl);
        editSenha=(EditText) findViewById(R.id.passWordPlr);
        editPhone=(EditText) findViewById(R.id.idPhonePl);
        editTextApelido=(EditText) findViewById(R.id.idApelidoPl);
        imageViewUser=(ImageView) findViewById(R.id.idImageUserViewPl);
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


        addUser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if (editTextApelido.getText().toString().isEmpty()
                         || editTextNome.getText().toString().isEmpty()
                         || editTextApelido.getText().toString().isEmpty()
                         || editSenha.getText().toString().isEmpty()
                         || autoCompleteDistrito.getText().toString().isEmpty()
                         || autoCompleteLocalidade.getText().toString().isEmpty()
                         || autoCompletePostoAdministrativo.getText().toString().isEmpty()
                         || autoCompleteComunidade.getText().toString().isEmpty()

                 ) {
                     Toast.makeText(ActivityUserRegister.this, "Preenche todos campos", Toast.LENGTH_SHORT).show();

                     if (editTextApelido.getText().equals("") || editTextNome.getText().equals("")) {
                         editTextNome.setError(" Campo Vazio ");
                         editTextApelido.setError(" Campo Vazio ");

                     } else {

                         DB db = new DB();
                         db.addArrayListUserPl(
                                 editTextNome.getText().toString(),
                                 editTextApelido.getText().toString(),
                                 editPhone.getText().toString(),
                                 editSenha.getText().toString(),
                                 urlImage,
                                 autoCompleteDistrito.getText().toString(),
                                 autoCompleteLocalidade.getText().toString(),
                                 autoCompletePostoAdministrativo.getText().toString(),
                                 autoCompleteComunidade.getText().toString());
                         Intent intent = new Intent(ActivityUserRegister.this, ActivityUserPL.class);
                         startActivity(intent);
                         databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {

                                 if (snapshot.hasChild(editPhone.getText().toString())) {
                                     Toast.makeText(ActivityUserRegister.this, "Usuario ja registado", Toast.LENGTH_SHORT).show();
                                 } else {
                                     String sha = getSha(editPhone.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("telemovel").setValue(editPhone.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("nome").setValue(editTextNome.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("apelido").setValue(editTextApelido.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("senha").setValue(editSenha.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("distrito").setValue(autoCompleteDistrito.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("localidade").setValue(autoCompleteLocalidade.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("postoAdministrativo").setValue(autoCompletePostoAdministrativo.getText().toString());
                                     databaseReference.child("usuarios").child(sha).child("comunidade").setValue(autoCompleteComunidade.getText().toString());

                                     databaseReference.child("usuarios").child(editPhone.getText().toString()).child("imagem").setValue(urlImage + "");

//                                   Intent intent=new Intent(ActivityUserRegister.this, ActivityUserPL.class);
//                                   startActivity(intent);
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {
                                 Toast.makeText(ActivityUserRegister.this, "Verifica a ligação de internet", Toast.LENGTH_SHORT).show();

                             }
                         });
                     }

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

    void sendMessage(String phone, String password){
        String sms=" MachambaApp: Nome do usuario- "+phone+ "senha -"+password;
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phone.toString().trim(),null,sms,null,null);

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityUserRegister.this, ActivityUserPL.class));
        super.onBackPressed();
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