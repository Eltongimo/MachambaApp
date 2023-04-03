package com.example.machambaapp.model.datamodel;

import java.util.ArrayList;

public class Resposta {
    private String nomeResposta;
    private ArrayList<String> outrasRespostas;

    public ArrayList<String> getOutrasRespostas() {
        return outrasRespostas;
    }

    public void setOutrasRespostas(ArrayList<String> outrasRespostas) {
        this.outrasRespostas = outrasRespostas;
    }

    public Resposta(){

    }

    public String getNomeResposta() {
        return nomeResposta;
    }

    public Resposta(String nomeResposta) {
        this.nomeResposta = nomeResposta;
    }

    public void setNomeResposta(String nomeResposta) {
        this.nomeResposta = nomeResposta;
    }
}
