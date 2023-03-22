package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.UserAdmin;
import com.example.machambaapp.model.datamodel.Cliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                   if(editTextPhone.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                       editTextPassword.setError("Campo Vazio");
                       editTextPhone.setError("Campo Vazio");
                       textViewAlert.setText("Entrada inválida!");
                   }else {
                       if (verificationPasswordAndUserNameAdmin()) {
                           editTextPassword.setText("");
                           editTextPhone.setText("");
                           textViewAlert.setText("");
                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                           startActivity(intent);
                       } else {

                           databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {

                                   // Este método é chamado uma vez com o valor inicial e novamente sempre que os dados no nó "users" são alterados.

                                   // Percorra todos os nós filhos do nó "users"

                                   for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                                       String phone = userSnapshot.child("phone").getValue(String.class);
                                       String password = userSnapshot.child("senha").getValue(String.class);

                                       if(phone.equals(editTextPhone.getText().toString()) &&
                                               password.equalsIgnoreCase(editTextPassword.getText().toString())){

                                           Privilegios privilegios= new Privilegios();
                                           privilegios.setAllAcessView(false);

                                           Cliente.UserPl u = new Cliente.UserPl();
                                           u.setNome(userSnapshot.child("nome").getValue(String.class));
                                           u.setPhone(userSnapshot.child("phone").getValue(String.class));

                                           SplashScreen.currentUser = u;

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
                    && userAdmin.getPassWord().equalsIgnoreCase(editTextPassword.getText().toString()) ) {
                textViewAlert.setText("");
                privilegios.setAllAcessView(true);
                return true;
            }
        return false;
    }
}