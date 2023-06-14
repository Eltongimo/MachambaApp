package com.example.machambaapp.model.helper;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.machambaapp.model.datamodel.OfflineDBModelForm;

import java.util.ArrayList;
import java.util.List;

public class OfflineDB extends  SQLiteOpenHelper{
    private static final String DBNAME =  "Formularios.db";
    private static final int DBVERSION = 1;

    private String tableName = "Formulario";

    public OfflineDB(Context context){
        super(context, DBNAME, null, DBVERSION);
    }

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Formulario";
    private static final String COLUMN_ID = "Id";
    private static String PERGUNTA = "Pergunta";
    private static String RESPOSTA = "Resposta";
    private static String IDFORMULARIO = "IdFormulario";

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IDFORMULARIO + "TEXT" + PERGUNTA+ "TEXT" + RESPOSTA + "TEXT)";
        db.execSQL(createTableQuery);
    }

    private void createTable(SQLiteDatabase db ){

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IDFORMULARIO + " TEXT," + PERGUNTA+ " TEXT," + RESPOSTA + " TEXT "+")";
        db.execSQL(createTableQuery);
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
}
