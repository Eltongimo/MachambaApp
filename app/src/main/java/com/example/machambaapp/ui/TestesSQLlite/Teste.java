package com.example.machambaapp.ui.TestesSQLlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.machambaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Teste extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private SqlHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        sqlHelper = new SqlHelper(this);

        // Set up image selection button click listener
        Button selectImageButton = findViewById(R.id.btImagem);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromStorage();
            }
        });
    }

    private void selectImageFromStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            byte[] imageData = convertImageToByteArray(selectedImageUri);

            // Store the image in the database
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(SqlHelper.COLUMN_IMAGE, imageData);
            db.insert(SqlHelper.TABLE_NAME, null, values);
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_LONG);

            db.close();
        }
    }

    private byte[] convertImageToByteArray(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}