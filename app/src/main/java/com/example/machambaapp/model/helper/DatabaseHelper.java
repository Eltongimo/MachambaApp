package com.example.machambaapp.model.helper;

import androidx.annotation.NonNull;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.SplashScreen;
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

    public static void deleteUserPL(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("usuarios/" + key);
        userRef.removeValue();
    }

    public static void updateUserPl(Cliente.UserPl u, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");
        myRef.child(key).child("nome").setValue(u);
    }

    public static void updatePosto(String posto, String localidade, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("postosAdministrativos");
        myRef.child(key).child("nome").setValue(posto);
        myRef.child(key).child("localidade").setValue(localidade);
    }
    public static void updateComunidade(String comunidade, String posto, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("comunidades");
        myRef.child(key).child("nome").setValue(comunidade);
        myRef.child(key).child("postoAdministrativo").setValue(posto);
    }

    public static void updateCultura(String cultura, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("culturas");
        myRef.child(key).child("nome").setValue(cultura);
    }

    public static void updateLocalidade(String localidade, String distrito, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("localidades");
        myRef.child(key).child("nome").setValue(localidade);
        myRef.child(key).child("distrito").setValue(distrito);

    }
    public static void updateEtnia(String et, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("etnias");
        myRef.child(key).child("nome").setValue(et);
    }

   public static void  updateDistrito(String distrito, String key){
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference("distritos");
       myRef.child(key).child("nome").setValue(distrito);
    }

    public static void deletePosto(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("postosAdministrativos/" + key);
        userRef.removeValue();
    }
    public static void deleteComunidade(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("comunidades/" + key);
        userRef.removeValue();
    }

    public static void deleteLocalidade(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("localidades/" + key);
        userRef.removeValue();
    }

    public static void deleteDistrito(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("distritos/" + key);
        userRef.removeValue();
    }

    public static void deleteEtnia(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("etnias/" + key);
        userRef.removeValue();
    }

    public static void updateLocations(String childValue, String childKey, String parentValue, String parentKey){


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

    public static void addUserPl(Cliente.UserPl u){
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
}
