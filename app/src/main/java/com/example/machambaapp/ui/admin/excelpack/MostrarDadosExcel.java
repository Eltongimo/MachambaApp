package com.example.machambaapp.ui.admin.excelpack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.machambaapp.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MostrarDadosExcel extends AppCompatActivity {
    private ListView listView;
    private ImageView docAdd;
    private Button btnCarregarExcel;
    private String filePath;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_dados_excel);


        listView = findViewById(R.id.listView);
        docAdd = (ImageView) findViewById(R.id.docAdd);
        btnCarregarExcel = (Button) findViewById(R.id.btnCarregarExcel);



        btnCarregarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        docAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma intenção para selecionar um arquivo
              //  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/*"); // Somente arquivos Excel

                // Inicia a atividade para selecionar um arquivo
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
    }

    // Trata o resultado da seleção do arquivo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se o resultado é do código de solicitação correto e se foi bem-sucedido
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {
            // Obtém o caminho do arquivo selecionado


            filePath = data.getData().getPath();

            // Exibe o caminho do arquivo selecionado em um TextView
            TextView filePathTextView = (TextView) findViewById(R.id.text_doc);
            filePathTextView.setText(filePath);

            String fileName = data.getData().toString().split("Download%2F")[1];

            System.out.println("MY FILE NAME =>>>>>>>>>>" + fileName);

            String a = Environment.getExternalStorageDirectory().getPath()+"/Download/"+fileName;
            // Lê os dados do arquivo Excel e os exibe em um ListView
            readExcelData(a);
            docAdd.setVisibility(View.INVISIBLE);
            btnCarregarExcel.setVisibility(View.INVISIBLE);
            filePathTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void readExcelData(String filePath) {
        try {
            // Abre o arquivo Excel como uma planilha
            Workbook workbook = WorkbookFactory.create(new File(filePath));
            Sheet sheet = workbook.getSheetAt(0);

            // Lê as linhas da planilha e adiciona os dados a um ArrayList
            List<String> excelDataList = new ArrayList<>();
            for (Row row : sheet) {
                String rowData = "";
                for (Cell cell : row) {
                    rowData += cell.toString() + "\t";
                }
                excelDataList.add(rowData);
            }

            // Exibe os dados do Excel em um ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, excelDataList);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}