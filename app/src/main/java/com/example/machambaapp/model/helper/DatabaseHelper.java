package com.example.machambaapp.model.helper;

import androidx.annotation.NonNull;

import com.example.machambaapp.Cultura;
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
import java.util.concurrent.CountDownLatch;

public class DatabaseHelper extends AppCompatActivity{

    public final static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    public static String getSha(){
        return UUID.randomUUID().toString();
    }

    public static void deleteCultura(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("culturas/" + key);
        userRef.removeValue();
    }
    public static void addLocations(String childValue, String childKey, String parentValue, String parentKey){
        databaseReference.child(childKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String key = getSha();
                databaseReference.child(childKey).child(key).child("nome").setValue(childValue);

                if ( !parentKey.equals("") && !parentValue.equals("")){
                    databaseReference.child(childKey).child(key).child(parentKey).setValue(parentValue);
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
    public static ArrayList<String> getEtnia(String pathName){

        ArrayList<String> e = new ArrayList<String>();

        databaseReference.child(pathName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {
                    e.add(userPLSnapshot.child("nome").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return e;
    }

    public static void addUserPl(UserPl u){
         databaseReference.child("usuarios").child(getSha()).setValue(u);
    }

    public static void  addClientes(Cliente c){

        String key = getSha();
        databaseReference.child("clientes").child(key).setValue(c);
        databaseReference.child("clientes").child(key).child("pl").child("nome").setValue(SplashScreen.currentUser.getNome());
        databaseReference.child("clientes").child(key).child("pl").child("phone").setValue(SplashScreen.currentUser.getPhone());
    }

    public static ArrayList<Cultura> getCulturas(){
        ArrayList<Cultura> culturas = new ArrayList<Cultura>();

        CountDownLatch myLatch = new CountDownLatch(1);

        databaseReference.child("culturas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                culturas.clear();
                for (DataSnapshot culturasSnap : snapshot.getChildren()) {
                    String nomeCultura = culturasSnap.child("nome").getValue(String.class);
                    culturas.add(new Cultura(nomeCultura));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myLatch.countDown();
            }

        });
        myLatch.countDown();
        return culturas;
    }
    public static ArrayList<UserPl> getUsersPL(){
        ArrayList<UserPl> userPls = new ArrayList<UserPl>();

        CountDownLatch myLatch = new CountDownLatch(1);

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
                myLatch.countDown();
            }
        });
        myLatch.countDown();
        return userPls;

    }
}
