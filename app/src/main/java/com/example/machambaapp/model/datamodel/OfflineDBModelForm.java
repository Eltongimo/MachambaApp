package com.example.machambaapp.model.datamodel;

public class OfflineDBModelForm {
    private String formId;
    private String pergunta;
    private String resposta;
    private String nomePA;

    public String getNomePA() {
        return nomePA;
    }

    public void setNomePA(String nomePA) {
        this.nomePA = nomePA;
    }

    public String getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    private String dataVisita;
    public String getFormId() {
        return formId;
    }

    public OfflineDBModelForm() {
        formId = "";
        pergunta = "";
        resposta = "";
    }

    public OfflineDBModelForm(String formId, String pergunta, String resposta) {
        this.formId = formId;
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}
