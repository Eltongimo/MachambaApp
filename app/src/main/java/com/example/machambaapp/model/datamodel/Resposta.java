package com.example.machambaapp.model.datamodel;

import java.util.ArrayList;

public class Resposta {

    private String tipoResposta;
    private ArrayList<String> nRespostas;
    public ArrayList<String> perguntas;
    public ArrayList<String> respostas;

    public Resposta() {
        perguntas = new ArrayList<>();
        respostas = new ArrayList<>();

    }

    public String getTipoResposta() {
        return tipoResposta;
    }

    public void setTipoResposta(String tipoResposta) {
        this.tipoResposta = tipoResposta;
    }

    public ArrayList<String> getnRespostas() {
        return nRespostas;
    }

    public void setnRespostas(ArrayList<String> nRespostas) {
        this.nRespostas = nRespostas;
    }
}
