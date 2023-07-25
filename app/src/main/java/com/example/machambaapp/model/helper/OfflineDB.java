package com.example.machambaapp.model.helper;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.machambaapp.ActivityLogin;
import com.example.machambaapp.MainActivity;
import com.example.machambaapp.Network.NetworkUtils;
import com.example.machambaapp.SplashScreen;
import com.example.machambaapp.model.Privilegios;
import com.example.machambaapp.model.datamodel.Cliente;
import com.example.machambaapp.model.datamodel.OfflineDBModelForm;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OfflineDB extends  SQLiteOpenHelper{
    private static final String DBNAME =  "Formularios.db";
    private static final int DBVERSION = 1;

    private String tableName = "Formulario";

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
    private static String TABLECLIENTES = "Clientes";
    private static String  USERID = "Id";
    private static String NOMEFORMULARIO = "NomeFormulario";

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Cliente> clientes;
    private Context context;

    public OfflineDB(Context context){
        super(context, DBNAME, null, DBVERSION);
        createTable(this.getWritableDatabase());
        createUsersTable();
        setContext(context);
        clientes = new ArrayList<>();
    }

    public void removeAllRowsFromTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }



    public long insertClient(Cliente c ){

        long i = -1;

        ContentValues values = new ContentValues();
        values.put("Nome", c.getNome());
        values.put("Apelido", c.getApelido());
        values.put("Etnia", c.getEtnia());
        values.put("Genero", c.getGenero());
        values.put("Numero", c.getNumero());
        values.put("Ano", c.getAno());
        values.put("Distrito", c.getDistrito());
        values.put("Localidade", c.getLocalidade());
        values.put("Posto", c.getPosto());
        values.put("Comunidade", c.getComunidade());
        values.put("NomePL", c.getNomePl());
        values.put("NumeroPL", c.getNumeroPl());
        values.put("Imagem", c.getImage());
        values.put("Documento", c.getDocumento());

        i = this.getWritableDatabase().insert("Clientes", null, values);

        return i;

    }

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }


    public int getTableLength(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            // Handle the exception appropriately
            Log.e("SQLiteException", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }


    public void getClientes(){

        clientes = new ArrayList<>();

        DatabaseHelper.databaseReference.child("clientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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

                    if (nomePl.equals(SplashScreen.currentUser.getNome())){
                     long a =  insertClient(new Cliente(nome,apelido,numero,ano,genero, etnia,d,loc, pt,com,image, documento, nomePl, numeroPl));
                     a = 0;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Codigo melhorado para buscar clientes na base de dados offline
    public ArrayList<Cliente> getClientesOffline() {
        ArrayList<Cliente> data = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            boolean b = isTableExists(db, "Clientes") ;
            // Check if the "Clientes" table exists
            if (b) {
                cursor = db.rawQuery("SELECT * FROM Clientes", null);

                if (cursor.getCount() == 0){

                    // Here we should see if the internet connection is online then download Clients from RTDB

                    if (NetworkUtils.isNetworkAvailable(context)){
                        // Download the clients from RTDB and persist to SQLite
                        getClientes();
                    }else {
                        Toast.makeText(context, "Por favor conecte o despositivo a Intenet para baixar os clientes do Usuario "+ SplashScreen.currentUser.getNome(), Toast.LENGTH_SHORT).show();
                        // Calling method should know that it should pop one activity from Stack Navigation
                        return null;
                    }
                }
                if (cursor.moveToFirst()) {
                    do {
                        // Retrieve column values using column names
                        @SuppressLint("Range")String nome = cursor.getString(cursor.getColumnIndex("Nome"));
                        @SuppressLint("Range")String apelido = cursor.getString(cursor.getColumnIndex("Apelido"));
                        @SuppressLint("Range")String etnia = cursor.getString(cursor.getColumnIndex("Etnia"));
                        @SuppressLint("Range")String genero = cursor.getString(cursor.getColumnIndex("Genero"));
                        @SuppressLint("Range")String numero = cursor.getString(cursor.getColumnIndex("Numero"));
                        @SuppressLint("Range")String ano = cursor.getString(cursor.getColumnIndex("Ano"));
                        @SuppressLint("Range")String distrito = cursor.getString(cursor.getColumnIndex("Distrito"));
                        @SuppressLint("Range")String localidade = cursor.getString(cursor.getColumnIndex("Localidade"));
                        @SuppressLint("Range")String posto = cursor.getString(cursor.getColumnIndex("Posto"));
                        @SuppressLint("Range")String comunidade = cursor.getString(cursor.getColumnIndex("Comunidade"));
                        @SuppressLint("Range")String nomePL = cursor.getString(cursor.getColumnIndex("NomePL"));
                        @SuppressLint("Range")String imagem = cursor.getString(cursor.getColumnIndex("Imagem"));
                        @SuppressLint("Range")String documento = cursor.getString(cursor.getColumnIndex("Documento"));

                        // Create a Cliente object and set the values
                        Cliente cl = new Cliente();
                        cl.setNome(nome);
                        cl.setImage(imagem);
                        cl.setNomePl(nomePL);
                        cl.setNumero(numero);
                        cl.setDistrito(distrito);
                        cl.setAno(ano);
                        cl.setApelido(apelido);
                        cl.setEtnia(etnia);
                        cl.setGenero(genero);
                        cl.setLocalidade(localidade);
                        cl.setPosto(posto);
                        cl.setComunidade(comunidade);
                        cl.setDocumento(documento);

                        if (SplashScreen.currentUser.getNome().equals(nomePL)){
                            data.add(cl);
                        }
                    } while (cursor.moveToNext());
                }

            } else {
                uploadClientesFromRTDB();
                data = getClientesOffline();
                Log.e("Table Not Found", "The 'Clientes' table does not exist in the database.");
            }
        } catch (SQLiteException e) {
            // Handle the exception appropriately
            Log.e("SQLiteException", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return data;
    }

    // Metodo para verificar se a tabela existe na base de dados
    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + tableName + "'", null);
        boolean tableExists = false;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                tableExists = true;
            }
            cursor.close();
        }
        return tableExists;
    }

    public void uploadClientesFromRTDB(){

        long a = 0;
        String createTableClientes = "CREATE TABLE IF NOT EXISTS " + TABLECLIENTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nome" + " TEXT," + "Apelido" + " TEXT,"
                + "Etnia" + " TEXT," + "Genero"+ " TEXT," +"Numero"+ " TEXT," + "Ano "+
                " TEXT," + " Distrito "+ " TEXT," +"Localidade "+" TEXT,"+ "Posto"+ " TEXT,"+
                " Comunidade " +" TEXT,"+ "NomePL"+ " TEXT," + "NumeroPL"+ " TEXT,"+ "Imagem" + " TEXT,"+
                "Documento" + " TEXT)";

        this.getWritableDatabase().execSQL(createTableClientes);

        if ( NetworkUtils.isNetworkAvailable(getContext()) ) {
            for (Cliente c : SplashScreen.clientes) {
                insertClient(c);
            }
        }
        else  {
            Toast.makeText(context, "Por favor conecte o despositivo a internet para poder sincronizar com os dados online", Toast.LENGTH_SHORT).show();
        }
        return ;
    }


    private void createUsersTable(){

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLEUSERS+ "(" +
                 USERID + " TEXT," + COMUNIDADE + " TEXT," + DISTRITO + " TEXT," + LOCALIDADE + " TEXT," + NOME + " TEXT," + PHONE + " TEXT," + SENHA + " TEXT)";
        this.getWritableDatabase().execSQL(createTableQuery);

    }

    private void createTableCulturas(){

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + "Culturas"+ "(" +
                "Id" + " TEXT," + "Nome " + "TEXT)";
                this.getWritableDatabase().execSQL(createTableQuery);

    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IDFORMULARIO + " TEXT," + PERGUNTA+ " TEXT," + RESPOSTA + " TEXT,"+ NOMEFORMULARIO + "TEXT)";
        db.execSQL(createTableQuery);
    }

    public long saveCulturas(){
        long id = 0;

        createTableCulturas();

        SQLiteDatabase db = this.getWritableDatabase();

        for (String c : SplashScreen.culturas){

            if (!NetworkUtils.isNetworkAvailable(context)){
                Toast.makeText(context, "Por favor conecte o despositivo a Internet para sincronizar as culturas", Toast.LENGTH_SHORT).show();
                return -1;
            }else{
                ContentValues values = null;
                    values = new ContentValues();
                    values.put("Id", DatabaseHelper.getSha());
                    values.put("Nome", c);
                    id = db.insert("Culturas", null, values);
                }
                db.close();
            }
        return id;
    }


    public ArrayList<Cliente.UserPl> getUsers(){

        ArrayList<Cliente.UserPl> users = selectAllUsersFromDB();

        if (users.isEmpty() || users.get(0) == null ){
            // if the offline DB dont have users we donwload them from the RTDB
            if(NetworkUtils.isNetworkAvailable(getContext())){

                Toast.makeText(context, "Sincronizando dados com o RTDB online", Toast.LENGTH_LONG).show();

                long a = saveUsers();


                if (a >= 1){
                    users = selectAllUsersFromDB();
                    getClientes();
                    Toast.makeText(context, "Usuarios sincronizados com sucesso", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, "Sem registos, ligue a internet para sincronizar", Toast.LENGTH_LONG).show();
            }
        }

        return users ;
    }
    public ArrayList<Cliente.UserPl> selectAllUsersFromDB(){
        ArrayList<Cliente.UserPl> data = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Usuarios", null);

        // here we test if there are some data on the

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
        db.execSQL("DROP TABLE "+ TABLEUSERS);
        db.close();
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

        String createTableClientes = "CREATE TABLE IF NOT EXISTS " + TABLECLIENTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nome" + " TEXT," + "Apelido" + " TEXT,"
                + "Etnia" + " TEXT," + "Genero"+ " TEXT," +"Numero"+ " TEXT," + "Ano "+
                " TEXT," + " Distrito "+ " TEXT," +"Localidade "+" TEXT,"+ "Posto"+ " TEXT,"+
                " Comunidade " +" TEXT,"+ "NomePL"+ " TEXT," + "NumeroPL"+ " TEXT,"+ "Imagem" + " TEXT,"+
                "Documento" + " TEXT)";

        db.execSQL(createTableClientes);

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

        final long[] rowId = {0};

        ContentValues values = null;

        for (Cliente.UserPl u : SplashScreen.users) {
            values = new ContentValues();
            values.put(NOME,u.getNome());
            values.put(COMUNIDADE,u.getComunidade());
            values.put(DISTRITO,u.getComunidade());
            values.put(PHONE,u.getPhone());
            values.put(SENHA, u.getSenha());
            rowId[0] = db.insert(TABLEUSERS, null, values);
        }
        db.close();

        return rowId[0];
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

