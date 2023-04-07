package com.example.machambaapp.model.datamodel;

public class Pergunta {
    private String nomeDaPergunta;
    private Resposta resposta;

    private String tipoPergunta;

    public String getTipoPergunta() {
        return tipoPergunta;
    }

    public void setTipoPergunta(String tipoPergunta) {
        this.tipoPergunta = tipoPergunta;
    }

    public Pergunta (){
        this.nomeDaPergunta = "";
    }

    public Pergunta(String nomeDaPergunta) {
        this.nomeDaPergunta = nomeDaPergunta;
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
