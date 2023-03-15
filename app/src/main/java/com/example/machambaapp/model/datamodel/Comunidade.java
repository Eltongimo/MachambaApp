package com.example.machambaapp.model.datamodel;

public class Comunidade {
    String nome, postoAdministrativo;

    public Comunidade(String nome, String postoAdministrativo) {
        this.nome = nome;
        this.postoAdministrativo = postoAdministrativo;
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
