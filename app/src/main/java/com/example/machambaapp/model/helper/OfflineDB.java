package com.example.machambaapp.model.helper;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.machambaapp.ActivityLogin;
import com.example.machambaapp.MainActivity;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OfflineDB extends  SQLiteOpenHelper{
    private static final String DBNAME =  "Formularios.db";
    private static final int DBVERSION = 1;

    private String tableName = "Formulario";

    public OfflineDB(Context context){
        super(context, DBNAME, null, DBVERSION);
        createTable(this.getWritableDatabase());
    }

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Formulario";
    private static final String COLUMN_ID = "Id";
    private static String PERGUNTA = "Pergunta";
    private static String RESPOSTA = "Resposta";
    private static String IDFORMULARIO = "IdFormulario";

    private static String COMUNIDADE = "comunidade";
    private static String DISTRITO = "distrito";
    private static String LOCALIDADE = "Localidade";
    private static String NOME = "Nome";
    private static String PHONE = "Phone";
    private static String SENHA = "Senha";
    private static String TABLEUSERS = "Usuarios";
    private static String  USERID = "Id";
    private static String NOMEFORMULARIO = "NomeFormulario";

    private void createUsersTable(){

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLEUSERS+ "(" +
                 USERID + " TEXT," + COMUNIDADE + " TEXT," + DISTRITO + " TEXT," + LOCALIDADE + " TEXT," + NOME + " TEXT," + PHONE + " TEXT," + SENHA + " TEXT)";
        this.getWritableDatabase().execSQL(createTableQuery);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IDFORMULARIO + " TEXT," + PERGUNTA+ " TEXT," + RESPOSTA + " TEXT,"+ NOMEFORMULARIO + "TEXT)";
        db.execSQL(createTableQuery);
    }

    public ArrayList<Cliente.UserPl> getUsers(){
        ArrayList<Cliente.UserPl> data = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Usuarios", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex(NOME));
                @SuppressLint("Range") String comunidade = cursor.getString(cursor.getColumnIndex(COMUNIDADE));
                @SuppressLint("Range") String distrito = cursor.getString(cursor.getColumnIndex(DISTRITO));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                @SuppressLint("Range") String senha = cursor.getString(cursor.getColumnIndex(SENHA));

                Cliente.UserPl u = new Cliente.UserPl();
                u.setNome(nome);
                u.setComunidade(comunidade);
                u.setDistrito(distrito);
                u.setPhone(phone);
                u.setSenha(senha);
                data.add(u);
            } while (cursor.moveToNext());
        }
        return data;

    }
    private void createTable(SQLiteDatabase db ){

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IDFORMULARIO + " TEXT," + PERGUNTA+ " TEXT," + RESPOSTA + " TEXT)";
        db.execSQL(createTableQuery);

    }

    public void cleanDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+ TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public long insertForm(ArrayList<OfflineDBModelForm> offLineData) {
        SQLiteDatabase db = this.getWritableDatabase();

        createTable(db);

        long rowId = 0;
        ContentValues values = null;
        for (OfflineDBModelForm data : offLineData) {
            values = new ContentValues();
            values.put(IDFORMULARIO, data.getFormId());
            values.put(PERGUNTA, data.getPergunta());
            values.put(RESPOSTA, data.getResposta());
            rowId = db.insert(TABLE_NAME, null, values);
        }
        db.close();
        return rowId;
    }

    public ArrayList<OfflineDBModelForm> readForms() {

        ArrayList<OfflineDBModelForm> data = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Formulario", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String pergunta = cursor.getString(cursor.getColumnIndex(PERGUNTA));
                @SuppressLint("Range") String resposta = cursor.getString(cursor.getColumnIndex(RESPOSTA));
                @SuppressLint("Range") String idFormulario = cursor.getString(cursor.getColumnIndex(IDFORMULARIO));
                data.add(new OfflineDBModelForm(idFormulario, pergunta, resposta));
            } while (cursor.moveToNext());
        }
        return data;
    }

    public long saveUsers(){


        SQLiteDatabase db = this.getWritableDatabase();

        createUsersTable();

        long rowId = 0;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://machambaapp-default-rtdb.firebaseio.com/");

        databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ContentValues values = null;

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    values = new ContentValues();
                    values.put(NOME,userSnapshot.child("nome").getValue(String.class));
                    values.put(COMUNIDADE, userSnapshot.child("comunidade").getValue(String.class));
                    values.put(DISTRITO, userSnapshot.child("distrito").getValue(String.class));
                    values.put(PHONE,userSnapshot.child("phone").getValue(String.class));
                    values.put(SENHA, userSnapshot.child("senha").getValue(String.class));

                    long rowId = db.insert(TABLEUSERS, null, values);

                }
                db.close();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return rowId;
    }

    public ArrayList<OfflineDBModelForm> getDataFromFormId(String formId){
        ArrayList<OfflineDBModelForm> data = new ArrayList<>();

        for (OfflineDBModelForm d : this.readForms()){
            if (d.getFormId().equals(formId)){
                data.add(d);
            }
        }
        return data;
    }
}
