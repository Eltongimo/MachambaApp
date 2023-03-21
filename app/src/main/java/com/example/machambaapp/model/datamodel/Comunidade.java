package com.example.machambaapp.model.datamodel;

public class Comunidade {
    String nome, postoAdministrativo, key;

    public Comunidade(String nome, String postoAdministrativo, String key) {
        this.nome = nome;
        this.postoAdministrativo = postoAdministrativo;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPostoAdministrativo() {
        return postoAdministrativo;
    }

    public void setPostoAdministrativo(String postoAdministrativo) {
        this.postoAdministrativo = postoAdministrativo;
    }
}
