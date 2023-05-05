package com.example.machambaapp.model.datamodel;

public class PerguntaCondicional {

    private String opcaoDeConficao;
    private String condicaoDeComparacao;

    private Pergunta pergunta;

    public PerguntaCondicional(){
        this.pergunta = new Pergunta();
        this.condicaoDeComparacao = "";
        this.opcaoDeConficao = "";
    }
    public PerguntaCondicional(String opcaoDeConficao, String condicaoDeComparacao, Pergunta pergunta) {
        this.opcaoDeConficao = opcaoDeConficao;
        this.condicaoDeComparacao = condicaoDeComparacao;
        this.pergunta = pergunta;
    }

    public void setOpcaoDeConficao(String opcaoDeConficao) {
        this.opcaoDeConficao = opcaoDeConficao;
    }

    public String getCondicaoDeComparacao() {
        return condicaoDeComparacao;
    }

    public void setCondicaoDeComparacao(String condicaoDeComparacao) {
        this.condicaoDeComparacao = condicaoDeComparacao;
    }
}
