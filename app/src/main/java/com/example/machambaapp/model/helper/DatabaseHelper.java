package com.example.machambaapp.model.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.machambaapp.Cultura;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.Comunidade;
import com.example.machambaapp.model.datamodel.Distrito;
import com.example.machambaapp.model.datamodel.Etnia;
import com.example.machambaapp.model.datamodel.Formulario;
import com.example.machambaapp.model.datamodel.Localidade;
import com.example.machambaapp.model.datamodel.Pergunta;
import com.example.machambaapp.model.datamodel.Posto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class DatabaseHelper extends AppCompatActivity{

    public final static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");
    public static String getSha(){
        return UUID.randomUUID().toString();
    }

    private static CountDownLatch counter = new CountDownLatch(1);
    public static ArrayList<Cliente.UserPl> getUsers(){

        final CountDownLatch latch = new CountDownLatch(2);

        ArrayList<Cliente.UserPl> users = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userPLSnapshot : dataSnapshot.getChildren()) {
                    String comunidade=userPLSnapshot.child("comunidade").getValue(String.class);;
                    String nome = userPLSnapshot.child("nome").getValue(String.class);
                    String phone=  userPLSnapshot.child("phone").getValue(String.class);
                    String apelido= userPLSnapshot.child("apelido").getValue(String.class);
                    String distrito= userPLSnapshot.child("distrito").getValue(String.class);
                    String localidade= userPLSnapshot.child("localidade").getValue(String.class);;
                    String postoAdministrativo=userPLSnapshot.child("postoAdministrativo").getValue(String.class);;
                    String genero=userPLSnapshot.child("genero").getValue(String.class);;
                    String senha=userPLSnapshot.child("senha").getValue(String.class);;
                    String imagem=userPLSnapshot.child("image").getValue(String.class);;

                    users.add(new Cliente.UserPl( nome, apelido, senha,genero,phone,  distrito, localidade, postoAdministrativo,  comunidade, userPLSnapshot.getKey(),imagem));
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });
        System.out.println("Returning");
        return users;
    }
    public static ArrayList<Posto>getPostosAdministrativos(String query){
        ArrayList<Posto> postos = new ArrayList<>();
        databaseReference.child("postosAdministrativos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postosAdministrativosSnap : snapshot.getChildren()) {

                    String nomePostosAdministrativos = postosAdministrativosSnap.child("nome").getValue(String.class);
                    String nomeLocalidade = postosAdministrativosSnap.child("localidade").getValue(String.class);

                    if (nomeLocalidade.toLowerCase().contains(query.toLowerCase()) || nomePostosAdministrativos.toLowerCase().contains(query.toLowerCase())){
                        String chave = postosAdministrativosSnap.getKey().toString();
                        postos.add(new Posto(nomePostosAdministrativos, nomeLocalidade, chave));
                    }

                }
                Collections.sort(postos, new Comparator<Posto>() {
                    @Override
                    public int compare(Posto u1, Posto u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return postos;
    }
    public static void deleteCultura(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("culturas/" + key);
        userRef.removeValue();
    }

    public static ArrayList<Comunidade> getComunidades(String query){
        ArrayList<Comunidade> comunidades = new ArrayList<>();

        databaseReference.child("comunidades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comunidades.clear();
                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {
                    String nomecomunidades = comunidadesSnap.child("nome").getValue(String.class);
                    String nomePosto = comunidadesSnap.child("postoAdministrativo").getValue(String.class);
                    if (nomecomunidades.toLowerCase().contains(query.toLowerCase()) || nomePosto.toLowerCase().contains(query.toLowerCase())){
                        String chave = comunidadesSnap.getKey().toString();
                        comunidades.add(new Comunidade(nomecomunidades, nomePosto, chave));
                    }
                }
                Collections.sort(comunidades, new Comparator<Comunidade>() {
                    @Override
                    public int compare(Comunidade u1, Comunidade u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return comunidades;
    }

    public static ArrayList<Localidade> getLocalidades(String query){
        ArrayList<Localidade> localidades = new ArrayList<Localidade>();

        databaseReference.child("localidades").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                localidades.clear();
                for (DataSnapshot localidadesSnap : snapshot.getChildren()) {

                    String nomeLocalidades = localidadesSnap.child("nome").getValue(String.class);
                    String nomeDistrito = localidadesSnap.child("distrito").getValue(String.class);

                    if (nomeLocalidades.toLowerCase().contains(query.toLowerCase()) || nomeDistrito.toLowerCase().contains(query.toLowerCase())){
                        String chave = localidadesSnap.getKey().toString();
                        localidades.add(new Localidade(nomeLocalidades, nomeDistrito, chave));
                    }
                }
                Collections.sort(localidades, new Comparator<Localidade>() {
                    @Override
                    public int compare(Localidade u1, Localidade u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return localidades;
    }
    public static ArrayList<Etnia> getEtnias(String query){
        ArrayList<Etnia> etnias = new ArrayList<>();

        databaseReference.child("etnias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot etniasSnap : snapshot.getChildren()) {
                    String nomeEtnias = etniasSnap.child("nome").getValue(String.class);

                    if (nomeEtnias.toLowerCase().contains(query.toLowerCase())){
                        String chave = etniasSnap.getKey().toString();
                        etnias.add(new Etnia(nomeEtnias, chave));
                    }
                }
                Collections.sort(etnias, new Comparator<Etnia>() {
                    @Override
                    public int compare(Etnia u1, Etnia u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return etnias;
    }

    public static ArrayList<Distrito> getDistritos(String query){
        ArrayList<Distrito> distritos = new ArrayList<>();

        databaseReference.child("distritos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                distritos.clear();
                for (DataSnapshot DistritosSnap : snapshot.getChildren()) {
                    String nomeDistrito = DistritosSnap.child("nome").getValue(String.class);
                    String nomeProvincia = DistritosSnap.child("provincia").getValue(String.class);

                    if (nomeDistrito.toLowerCase().contains(query.toLowerCase())){
                        String chave = DistritosSnap.getKey().toString();

                        distritos.add(new Distrito(nomeDistrito, chave, nomeProvincia));
                    }
                }
                Collections.sort(distritos, new Comparator<Distrito>() {
                    @Override
                    public int compare(Distrito u1, Distrito u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return distritos;
    }
    public static ArrayList<Cultura> getCulturas(String query){
        ArrayList<Cultura> c = new ArrayList<>();
        databaseReference.child("culturas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot comunidadesSnap : snapshot.getChildren()) {

                    String cultura = comunidadesSnap.child("nome").getValue(String.class);

                    if (cultura.toLowerCase().contains(query.toLowerCase())){
                        String chave = comunidadesSnap.getKey().toString();
                        String imagem=comunidadesSnap.child("image").getValue(String.class);;

                        c.add(new Cultura(cultura, chave,imagem));
                    }
                 }
                Collections.sort(c, new Comparator<Cultura>() {
                    @Override
                    public int compare(Cultura c1, Cultura c2) {
                        return c1.getCultura().compareTo(c2.getCultura());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return c;
    }
    public static ArrayList<Cliente.UserPl> getUsers(String query){
        ArrayList<Cliente.UserPl> users = new ArrayList<>();


        DatabaseHelper.databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot userPLSnapshot : snapshot.getChildren()) {

                    String nome = userPLSnapshot.child("nome").getValue(String.class);
                    String apelido= userPLSnapshot.child("apelido").getValue(String.class);

                    if ( nome.toLowerCase().contains(query.toLowerCase()) || apelido.toLowerCase().contains(query.toLowerCase()) ) {

                        String comunidade = userPLSnapshot.child("comunidade").getValue(String.class);
                        ;
                        String phone = userPLSnapshot.child("phone").getValue(String.class);
                        String distrito = userPLSnapshot.child("distrito").getValue(String.class);
                        String localidade = userPLSnapshot.child("localidade").getValue(String.class);
                        ;
                        String postoAdministrativo = userPLSnapshot.child("postoAdministrativo").getValue(String.class);
                        ;
                        String genero = userPLSnapshot.child("genero").getValue(String.class);
                        ;
                        String senha = userPLSnapshot.child("senha").getValue(String.class);
                        ;
                        users.add(new Cliente.UserPl(nome, apelido, senha, genero, phone, distrito, localidade, postoAdministrativo, comunidade, userPLSnapshot.getKey(),""));
                    }
                }
                Collections.sort(users, new Comparator<Cliente.UserPl>() {
                    @Override
                    public int compare(Cliente.UserPl u1, Cliente.UserPl u2) {
                        return u1.getNome().compareTo(u2.getNome());
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return users;
    }

    public static void deleteUserPL(String key){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("usuarios/" + key);
        userRef.removeValue();
    }

    public static void updateUserPl(Cliente.UserPl u, String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");
        myRef.child(key).setValue(u);
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

   public static void  updateDistrito(String distrito, String provincia, String key){
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference("distritos");
       myRef.child(key).child("nome").setValue(distrito);
       myRef.child(key).child("provincia").setValue(provincia);

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

    public static void addForm(Formulario f){
        String key = getSha();
        databaseReference.child("formularios").child(key).setValue(f);
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
                s.clear();
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

    public static ArrayList<Formulario> getForms(){

        ArrayList<Formulario> forms = new ArrayList<>();

        databaseReference.child("formularios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Formulario f = new Formulario();
                ArrayList<Pergunta> per = new ArrayList<>();

                for (DataSnapshot formularioSnap : snapshot.getChildren()){
                    for (DataSnapshot perguntasSnap : formularioSnap.getChildren()){
                        for (DataSnapshot p : perguntasSnap.getChildren()){
                            String nomePergunta = p.child("nomeDaPergunta").getValue(String.class);
                            String tipoPergunta = p.child("tipoPergunta").getValue(String.class);
                            per.add(new Pergunta(nomePergunta,tipoPergunta));

                        }
                        f = new Formulario();
                        f.setPerguntas(per);
                    }
                    per = new ArrayList<>();
                    forms.add(f);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return forms;
    }
    private static Formulario getForm(){

        Formulario f = new Formulario();
        ArrayList<Pergunta> perguntas = new ArrayList<>();

        databaseReference.child("formularios").child("perguntas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Formulario f = new Formulario();
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    Pergunta p = userSnapshot.child("perguntas").getValue(Pergunta.class);
                    perguntas.add(p);
                }
                f.setPerguntas(perguntas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return f;
    }
    public static void addUserPl(Cliente.UserPl u){
        String key = getSha();
        databaseReference.child("usuarios").child(key).setValue(u);
    }
    public static void addCultura(Cultura c){
        String key = getSha();
        databaseReference.child("culturas").child(key).setValue(c);
    }

    public static void addClientFromExcel(Cliente c){
        String key = getSha();
        databaseReference.child("clientes").child(key).setValue(c);
    }

    public static void addCliente(Cliente c){

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
                    culturas.add(new Cultura());
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
