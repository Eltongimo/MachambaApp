package com.example.machambaapp.ui.TestesSQLlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.machambaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Teste extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private SqlHelper sqlHelper;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        sqlHelper = new SqlHelper(this);

        // Set up image selection button click listener
        Button selectImageButton = findViewById(R.id.btImagem);
        image = findViewById(R.id.image);
        Button  btFetch = findViewById(R.id.btFetch);

        btFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the image from the database
                SQLiteDatabase db = sqlHelper.getReadableDatabase();
                Cursor cursor = db.query(SqlHelper.TABLE_NAME, new String[]{SqlHelper.COLUMN_IMAGE}, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") byte[] imageData = cursor.getBlob(cursor.getColumnIndex(SqlHelper.COLUMN_IMAGE));
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                    // Display the image
                    image.setImageBitmap(imageBitmap);
                }

                cursor.close();
                db.close();
            }
        });



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
            Bitmap originalBitmap = convertUriToBitmap(selectedImageUri);

            // Calculate the desired dimensions for the scaled image
            int desiredWidth = 800; // Change this value to your desired width
            int desiredHeight = 600; // Change this value to your desired height

            // Scale the image
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);

            // Convert the scaled image to a byte array
            byte[] imageData = convertBitmapToByteArray(scaledBitmap);

            // Store the image in the database
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SqlHelper.COLUMN_IMAGE, imageData);
            db.insert(SqlHelper.TABLE_NAME, null, values);
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    private Bitmap convertUriToBitmap(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}