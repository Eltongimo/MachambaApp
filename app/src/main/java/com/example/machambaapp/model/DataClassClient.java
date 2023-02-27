package com.example.machambaapp.model;

import android.net.Uri;

public class DataClassClient {

         private  String  fullName;

         private  String apelido;

         private  String distrito;
         private String  localidade;
         private  String  idade;
         private  String  sexo;
         private  String  etnia;
         private  String  numeroEmola;
         private Uri faceImage;


    public DataClassClient(String fullName, String apelido, String distrito, String localidade, Uri faceImage) {
        this.fullName = fullName;
        this.apelido = apelido;
        this.distrito = distrito;
        this.localidade = localidade;
        this.faceImage = faceImage;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
                "fullName='" + fullName + '\'' +
                ", faceImage='" + faceImage + '\'' +
                '}';
    }
}
