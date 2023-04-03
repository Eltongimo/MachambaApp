package com.example.machambaapp.model.datamodel;

import java.util.ArrayList;

public class Formulario {
    private String nome;

    private ArrayList<Pergunta> perguntas;
    private ArrayList<Resposta> respostas;

    public Formulario(){
        perguntas = new ArrayList<>();
        respostas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(ArrayList<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public ArrayList<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(ArrayList<Resposta> respostas) {
        this.respostas = respostas;
    }
}
