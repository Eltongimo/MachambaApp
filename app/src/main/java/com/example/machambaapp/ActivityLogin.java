package com.example.machambaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.databinding.ActivityChooseActionBinding;
import com.example.machambaapp.model.DB;
import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.UserAdmin;
import com.example.machambaapp.model.UserPl;

public class ActivityLogin extends AppCompatActivity {
    EditText editTextUseName;
    EditText editTextPassword;
    TextView textViewAlert;

   private Button buttonLogar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword=(EditText) findViewById(R.id.idEditPassword);
        editTextUseName=(EditText) findViewById(R.id.idEditUserName);
        textViewAlert=(TextView) findViewById(R.id.idAlert);



           buttonLogar=(Button) findViewById(R.id.idButtonlogar);
           buttonLogar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   if(editTextUseName.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                       editTextPassword.setError("Campo Vazio");
                       editTextUseName.setError("Campo Vazio");
                       textViewAlert.setText("Entrada inválida!");
                   }else {
                       if (verificationPasswordAndUserNameAdmin() || verificationPasswordAndUserNamePl()) {
                           Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                           startActivity(intent);
                       }

                   }
               }
           });
    }

    boolean verificationPasswordAndUserNameAdmin(){

        UserAdmin userAdmin=new UserAdmin();
        Privilegios privilegios=new Privilegios();
            if(userAdmin.getUserName().equalsIgnoreCase(editTextUseName.getText().toString())
                    && userAdmin.getPassWord().equalsIgnoreCase(editTextPassword.getText().toString()) ){
                textViewAlert.setText("");
                privilegios.setAllAcessView(true);
                return true;
            }else {
                textViewAlert.setText("Usuário ou senha inválido");
            }
     return false;
    }

    boolean verificationPasswordAndUserNamePl(){

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


        return false;
    }
}