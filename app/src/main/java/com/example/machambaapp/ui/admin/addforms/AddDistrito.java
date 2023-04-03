package com.example.machambaapp.ui.admin.addforms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.machambaapp.R;

import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.helper.DatabaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDistrito extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

    Button btnRegistar;
    EditText nomeDistrito;
    ArrayAdapter<String> adapterProvincias;
    AutoCompleteTextView autoProvincias;


    @Override
    public void onBackPressed() {
        // Add your code here
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_distrito);

        nomeDistrito = (EditText) findViewById(R.id.nomeDistrito);
        btnRegistar = (Button) findViewById(R.id.addDistrito);
        autoProvincias= (AutoCompleteTextView) findViewById(R.id.idProvincias);

        String [] distritosArray = SplashScreen.distritos.toArray(new String[SplashScreen.distritos.size()]);
        String [] provinciasArray = SplashScreen.provincias.toArray(new String[SplashScreen.provincias.size()]);


        adapterProvincias = new ArrayAdapter<>(this, R.layout.list_item_distrito, provinciasArray);
        autoProvincias.setAdapter(adapterProvincias);


        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper.addLocations(nomeDistrito.getText().toString(), "distritos",autoProvincias.getText().toString(),"provincia");
                finish();
                //    startActivity(new Intent(AddDistrito.this, ActivityViewDistrito.class));
            }
        });
    }
}

