package com.example.machambaapp.model.helper;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.machambaapp.ActivityViewAddCultura;
import com.example.machambaapp.AddCultura;
import com.example.machambaapp.Cultura;
import com.example.machambaapp.model.UserPl;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.machambaapp.model.adapter.CulturaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHelper extends AppCompatActivity{

    private final static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    private static String getSha(String s){
        return UUID.randomUUID().toString();
    }
    public static void addCultura(String c, String t){
        databaseReference.child(t).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(t).child(getSha(c)).child("nome").setValue(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void addUserPl(UserPl u){

         databaseReference.child("usuarios").child(getSha(u.getApelido())).setValue(u);
        /*
        databaseReference.child("usuarios").child(sha).child("telemovel").setValue(editPhone.getText().toString());
        databaseReference.child("usuarios").child(sha).child("nome").setValue(editTextNome.getText().toString());
        databaseReference.child("usuarios").child(sha).child("apelido").setValue(editTextApelido.getText().toString());
        databaseReference.child("usuarios").child(sha).child("senha").setValue(editSenha.getText().toString());
        databaseReference.child("usuarios").child(sha).child("distrito").setValue(autoCompleteDistrito.getText().toString());
        databaseReference.child("usuarios").child(sha).child("localidade").setValue(autoCompleteLocalidade.getText().toString());
        databaseReference.child("usuarios").child(sha).child("postoAdministrativo").setValue(autoCompletePostoAdministrativo.getText().toString());
        databaseReference.child("usuarios").child(sha).child("comunidade").setValue(autoCompleteComunidade.getText().toString());

        databaseReference.child("usuarios").child(editPhone.getText().toString()).child("imagem").setValue(urlImage + "");
*/
    }

    public static ArrayList<UserPl> getUsersPL(){
        ArrayList<UserPl> userPls = new ArrayList<UserPl>();

        databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot culturasSnap : snapshot.getChildren()) {
                    String comunidade=culturasSnap.child("comunidade").getValue(String.class);;
                    String nome = culturasSnap.child("nome").getValue(String.class);
                    String passWordPl = culturasSnap.child("senha").getValue(String.class);
                    String phone=  culturasSnap.child("telefone").getValue(String.class);
                    String apelido= culturasSnap.child("apelido").getValue(String.class);
                    String distrito= culturasSnap.child("senha").getValue(String.class);
                    String localidade= culturasSnap.child("localidade").getValue(String.class);;
                    String postoAdministrativo=culturasSnap.child("postoAdministrativo").getValue(String.class);;
                    String genero=culturasSnap.child("genero").getValue(String.class);;
                    String senha=culturasSnap.child("senha").getValue(String.class);;


                    userPls.add(new UserPl( nome, apelido, senha,genero,phone,  null,  distrito, localidade, postoAdministrativo,  comunidade));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userPls;

    }
}
