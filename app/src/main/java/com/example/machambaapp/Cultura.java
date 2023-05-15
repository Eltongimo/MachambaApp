package com.example.machambaapp;

public class Cultura {

    String cultura;
    String imagem;
    String Key;

    public Cultura() {
        this.cultura = cultura;}

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Cultura(String cultura, String key, String imagem) {
        this.cultura = cultura;
        this.Key = key;
        this.imagem = imagem;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }
}
