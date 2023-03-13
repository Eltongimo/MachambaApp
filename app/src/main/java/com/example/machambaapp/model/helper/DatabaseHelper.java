package com.example.machambaapp.model.helper;

import androidx.annotation.NonNull;

import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.UserPl;
import com.example.machambaapp.model.datamodel.Cliente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import java.util.UUID;

public class DatabaseHelper extends AppCompatActivity{

    public final static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    public static String getSha(){
        return UUID.randomUUID().toString();
    }

    public static void addLocation(String locationName, String locatioParentName, String ParentKey){

    }

    public static void addCultura(String c, String t){
        databaseReference.child(t).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child(t).child(getSha()).child("nome").setValue(c);
                if (t.equals("distritos")){
                    SplashScreen.distritos.add(c);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static ArrayList<String> getLocation(String pathName){

        ArrayList<String> s = new ArrayList<String>();

        databaseReference.child(pathName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {
                    s.add(userPLSnapshot.child("nome").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return s;
    }

    public static void addUserPl(UserPl u){
         databaseReference.child("usuarios").child(getSha()).setValue(u);
    }

    public static void  addClientes(Cliente c){

        databaseReference.child("clientes").child(getSha()).setValue(c);

    }
    public static ArrayList<UserPl> getUsersPL(){
        ArrayList<UserPl> userPls = new ArrayList<UserPl>();


        databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {
                    String comunidade=userPLSnapshot.child("comunidade").getValue(String.class);;
                    String nome = userPLSnapshot.child("nome").getValue(String.class);
                    String phone=  userPLSnapshot.child("telefone").getValue(String.class);
                    String apelido= userPLSnapshot.child("apelido").getValue(String.class);
                    String distrito= userPLSnapshot.child("senha").getValue(String.class);
                    String localidade= userPLSnapshot.child("localidade").getValue(String.class);;
                    String postoAdministrativo=userPLSnapshot.child("postoAdministrativo").getValue(String.class);;
                    String genero=userPLSnapshot.child("genero").getValue(String.class);;
                    String senha=userPLSnapshot.child("senha").getValue(String.class);;

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
