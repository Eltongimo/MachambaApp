package com.example.machambaapp.ui.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machambaapp.R;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.adapter.ClientAdapter;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.example.machambaapp.model.helper.OfflineDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.io.SessionOutputBuffer;

import java.util.ArrayList;

public class ActivitySelectClient extends AppCompatActivity {
    RecyclerView recyclerView ;
    EditText editName;
    TextView distrito;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    ArrayList<Cliente> clients = new ArrayList<>();
    ProgressDialog loadingBar;

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
                    String d = clientesSnap.child("distrito").getValue(String.class);
                    String loc = clientesSnap.child("localidade").getValue(String.class);
                    String pt = clientesSnap.child("posto").getValue(String.class);
                    String com = clientesSnap.child("comunidade").getValue(String.class);
                    String nomePl = clientesSnap.child("nomePl").getValue(String.class);
                    String numeroPl = clientesSnap.child("numeroPl").getValue(String.class);
                    String image = clientesSnap.child("image").getValue(String.class);
                    String documento = clientesSnap.child("documento").getValue(String.class);

//                    if (nomePl.equals("Albino")){
//                        clients.add(new Cliente(nome,apelido,numero,ano,genero, etnia,d,loc, pt,com,image, documento, nomePl, numeroPl));
//                    }

                    if (nomePl.equals(SplashScreen.currentUser.getNome())){
                        clients.add(new Cliente(nome,apelido,numero,ano,genero, etnia,d,loc, pt,com,image, documento, nomePl, numeroPl));
                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //setAdapter();
                Toast.makeText(getApplicationContext(), "Falha na conexão com o RTDB", Toast.LENGTH_LONG).show();
            }
        });
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);

        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Carregando clientes");
        loadingBar.setMessage("Aguarde por favor!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        //getCliensFromDatabase();
       ArrayList<Cliente> c = new OfflineDB(this).getClientesOffline();
       c = null;

        recyclerView = findViewById(R.id.idRecyclerviewClients2);

        distrito = (TextView) findViewById(R.id.nomeDistrito);
  }

    private void setAdapter(){
        ClientAdapter clientAdapter = new ClientAdapter(this, clients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadingBar.dismiss();
        recyclerView.setAdapter(clientAdapter);
    }
    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

}