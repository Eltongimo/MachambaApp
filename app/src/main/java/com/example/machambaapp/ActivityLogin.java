package com.example.machambaapp;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.UserAdmin;
import com.example.machambaapp.model.UserPl;
import com.google.android.gms.common.data.DataBuffer;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ActivityLogin extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    EditText editTextPhone;
    EditText editTextPassword;
    TextView textViewAlert;

   private Button buttonLogar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword=(EditText) findViewById(R.id.idEditPassword);
        editTextPhone =(EditText) findViewById(R.id.idEditUserName);
        textViewAlert=(TextView) findViewById(R.id.idAlert);



           buttonLogar=(Button) findViewById(R.id.idButtonlogar);
           buttonLogar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

<<<<<<< HEAD
                   if(editTextPhone.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                       editTextPassword.setError("campo vazio");
                       editTextPhone.setError("campo vazio");
=======
                   if(editTextUseName.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                       editTextPassword.setError("Campo Vazio");
                       editTextUseName.setError("Campo Vazio");
>>>>>>> 2b8bf757073189ea1daba2f79a36542bd13a92e0
                       textViewAlert.setText("Entrada inválida!");
                   }else {
                       if (verificationPasswordAndUserNameAdmin()) {
                           editTextPassword.setText("");
                           editTextPhone.setText("");
                           textViewAlert.setText("");
                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                           startActivity(intent);
                       } else {

//                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
//                           startActivity(intent);


                           databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {



                                   // Este método é chamado uma vez com o valor inicial e novamente sempre que os dados no nó "users" são alterados.

                                   // Percorra todos os nós filhos do nó "users"
                                   for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                                       String phone = userSnapshot.child("telemovel").getValue(String.class);
                                       String password = userSnapshot.child("senha").getValue(String.class);


                                       if(phone.equalsIgnoreCase(editTextPhone.getText().toString()) &&
                                               password.equalsIgnoreCase(editTextPassword.getText().toString())){

                                           Privilegios privilegios= new Privilegios();
                                           privilegios.setAllAcessView(false);

                                           startActivity(new Intent(ActivityLogin.this,MainActivity.class));
                                       }

                                   }






                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }

                           });

                       }
                   }


               }

           });
           }


    boolean verificationPasswordAndUserNameAdmin(){

        UserAdmin userAdmin=new UserAdmin();
        Privilegios privilegios=new Privilegios();
            if(userAdmin.getUserName().equalsIgnoreCase(editTextPhone.getText().toString())
                    && userAdmin.getPassWord().equalsIgnoreCase(editTextPassword.getText().toString()) ){
                textViewAlert.setText("");
                privilegios.setAllAcessView(true);
                return true;
<<<<<<< HEAD
=======
            }else {
                textViewAlert.setText("Usuário ou senha inválido");
>>>>>>> 2b8bf757073189ea1daba2f79a36542bd13a92e0
            }
     return false;
    }

    boolean verificationPasswordAndUserNamePl(){
<<<<<<< HEAD
        final boolean isTrue = false;
=======

        DB db=new DB();
        Privilegios privilegios=new Privilegios();
        for(UserPl user : db.getListUsePl()){
            System.out.println(db.getListUsePl());
            if(user.getPassWordPl().equalsIgnoreCase(editTextUseName.getText().toString())
                    && user.getPassWordPl().equalsIgnoreCase(editTextPassword.getText().toString())){
                textViewAlert.setText("");
                privilegios.setAllAcessView(false);
                return true;
            }else {
                textViewAlert.setText("Usuário ou senha inválido");
            }
        }
>>>>>>> 2b8bf757073189ea1daba2f79a36542bd13a92e0


        return false;
    }
}