package com.example.machambaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivitySelectClient extends AppCompatActivity {
    RecyclerView recyclerView ;
    EditText editName;
    ImageView imageFaceUser;
    TextView distrito, localidade, posto, comunidade;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    ArrayList<Cliente> clients = new ArrayList<>();

    private void getCliensFromDatabase(){
        databaseReference.child("clientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clients.clear();
                for (DataSnapshot clientesSnap : snapshot.getChildren()) {
                    String nome = clientesSnap.child("nome").getValue(String.class);
                    String apelido = clientesSnap.child("apelido").getValue(String.class);
                    String etnia = clientesSnap.child("etnia").getValue(String.class);
                    String genero = clientesSnap.child("genero").getValue(String.class);
                    String numero = clientesSnap.child("numero").getValue(String.class);
                    String ano = clientesSnap.child("ano").getValue(String.class);

                    String nomePl = clientesSnap.child("pl").child("nome").getValue(String.class);
                    String numeroPl = clientesSnap.child("pl").child("phone").getValue(String.class);

                    if (nomePl.equals(SplashScreen.currentUser.getNome()) && numeroPl.equals(SplashScreen.currentUser.getPhone())){
                        clients.add(new Cliente( nome,  apelido,  numero, ano, genero, etnia) );
                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setAdapter();
            }
        });

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);
        getCliensFromDatabase();
        recyclerView = findViewById(R.id.idRecyclerviewClients2);

        distrito = (TextView) findViewById(R.id.nomeDistrito);
        localidade =  (TextView) findViewById(R.id.nomeLocalidade);
        posto =  (TextView) findViewById(R.id.nomePosto);
        comunidade = (TextView) findViewById(R.id.nomeComunidade);

  }

    private void setAdapter(){
        ClientAdapter clientAdapter = new ClientAdapter(this, clients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(clientAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}