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

    public PerguntaCondicional conditionalQuestion;
    public void setTipoPergunta(String tipoPergunta) {
        this.tipoPergunta = tipoPergunta;
    }

    public ArrayList<String> getOpcoes() {
        return opcoes;
    }

    public ArrayList<Pergunta> perguntasCondicionais;
    public ArrayList<Pergunta> perguntasP;

    public Pergunta(ArrayList<Pergunta> perguntasP) {
        this.perguntasP = perguntasP;
    }

    public ArrayList<Pergunta> getPerguntasP() {
        return perguntasP;
    }

    public void setPerguntasP(ArrayList<Pergunta> perguntasP) {
        this.perguntasP = perguntasP;
    }

    public void setOpcoes(ArrayList<String> opcoes) {
        this.opcoes = opcoes;
    }

    public String condicaoTexto = "";

    public String getCondicaoTexto() {
        return condicaoTexto;
    }

    public void setCondicaoTexto(String condicaoTexto) {
        this.condicaoTexto = condicaoTexto;
    }

    public Pergunta (){
        this.opcoes = new ArrayList<>();
        this.nomeDaPergunta = "";
    }

    public ArrayList<Pergunta> getPerguntasCondicionais() {
        return perguntasCondicionais;
    }

    public void setPerguntasCondicionais(ArrayList<Pergunta> perguntasCondicionais) {
        this.perguntasCondicionais = perguntasCondicionais;
    }

    public Pergunta(String nomeDaPergunta) {
        this.opcoes = new ArrayList<>();
        this.nomeDaPergunta = nomeDaPergunta;
        this.conditionalQuestion = new PerguntaCondicional();
    }

    public PerguntaCondicional getConditionalQuestion() {
        return conditionalQuestion;
    }

    public void setConditionalQuestion(PerguntaCondicional conditionalQuestion) {
        this.conditionalQuestion = conditionalQuestion;
    }

    public Pergunta(String nomeDaPergunta, String tipo) {
        this.conditionalQuestion = new PerguntaCondicional();
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
