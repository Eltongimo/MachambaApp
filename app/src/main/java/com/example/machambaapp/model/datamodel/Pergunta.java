package com.example.machambaapp.model.datamodel;

import java.util.ArrayList;

public class Pergunta {
    private String nomeDaPergunta;
    private Resposta resposta;

    private ArrayList<String> opcoes;
    private String tipoPergunta;
    public String getTipoPergunta() {
        return tipoPergunta;
    }

    public void setTipoPergunta(String tipoPergunta) {
        this.tipoPergunta = tipoPergunta;
    }

    public ArrayList<String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(ArrayList<String> opcoes) {
        this.opcoes = opcoes;
    }

    public Pergunta (){
        this.opcoes = new ArrayList<>();
        this.nomeDaPergunta = "";
    }

    public Pergunta(String nomeDaPergunta) {
        this.opcoes = new ArrayList<>();
        this.nomeDaPergunta = nomeDaPergunta;
    }

    public Pergunta(String nomeDaPergunta, String tipo) {

        this.nomeDaPergunta = nomeDaPergunta;
        this.tipoPergunta = tipo;
    }

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }

    public String getNomeDaPergunta() {
        return nomeDaPergunta;
    }

    public void setNomeDaPergunta(String nomeDaPergunta) {
        this.nomeDaPergunta = nomeDaPergunta;
    }
}
