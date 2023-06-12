package com.example.machambaapp.model.helper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

public class OfflineDB {
    public static void saveFormOffline(String txt){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Forrmularios").child(UUID.randomUUID().toString());
        databaseReference.setValue(txt);
    }
}
