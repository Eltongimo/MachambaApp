package com.example.machambaapp.model.datamodel;

public class Pergunta {
    private String nomeDaPergunta;
    private String tipoPergunta;
    private Resposta resposta;

    public Pergunta (){
        this.nomeDaPergunta = "";
    }

    public Pergunta(String nomeDaPergunta) {
        this.nomeDaPergunta = nomeDaPergunta;
    }

    public String getTipoPergunta() {
        return tipoPergunta;
    }

    public void setTipoPergunta(String tipoPergunta) {
        this.tipoPergunta = tipoPergunta;
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
