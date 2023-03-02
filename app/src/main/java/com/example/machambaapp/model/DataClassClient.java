package com.example.machambaapp.model;

import android.net.Uri;

public class DataClassClient {

         private  String nome;

         private  Uri imageDocument;

         private  String apelido;

         private  String distrito;
         private String  localidade;
         private  String  idade;
         private  String  sexo;
         private  String  etnia;
         private  String  numeroEmola;
         private Uri faceImage;
         private  String phone;



    public DataClassClient(String fullName, String apelido,String phone, String distrito, String localidade, Uri faceImage, Uri imageDocument) {
        this.nome = fullName;
        this.apelido = apelido;
        this.distrito = distrito;
        this.localidade = localidade;
        this.faceImage = faceImage;
        this.imageDocument=imageDocument;
        this.phone=phone;
    }


    public Uri getImageDocument() {
        return imageDocument;
    }

    public void setImageDocument(Uri imageDocument) {
        this.imageDocument = imageDocument;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getNumeroEmola() {
        return numeroEmola;
    }

    public void setNumeroEmola(String numeroEmola) {
        this.numeroEmola = numeroEmola;
    }

    public Uri getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(Uri faceImage) {
        this.faceImage = faceImage;
    }

    @Override
    public String toString() {
        return "DataClassClient{" +
                "fullName='" + nome + '\'' +
                ", faceImage='" + faceImage + '\'' +
                '}';
    }
}
