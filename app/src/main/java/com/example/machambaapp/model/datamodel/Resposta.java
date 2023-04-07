package com.example.machambaapp.model.datamodel;

import java.util.ArrayList;

public class Resposta {

    private String tipoResposta;
    private ArrayList<String> nRespostas;

    public Resposta() {

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
