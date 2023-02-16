package com.example.machambaapp.model;

public class DataClassClient {

         private  String  fullName;
         private  String  idade;
         private  String  sexo;
         private  String  etnia;
         private  String  numeroEmola;
         private  int  faceImage;


    public DataClassClient(String faceImage, int image) {
        this.fullName = faceImage;
        this.faceImage= image;
    }

    public DataClassClient(String fullName, String idade, String sexo, String etnia, String numeroEmola, int faceImage) {
        this.fullName = fullName;
        this.idade = idade;
        this.sexo = sexo;
        this.etnia = etnia;
        this.numeroEmola = numeroEmola;
        this.faceImage = faceImage;
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

    public int getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(int faceImage) {
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
