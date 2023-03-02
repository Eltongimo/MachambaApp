package com.example.machambaapp.model;

import android.net.Uri;

public class UserPl {
    private Uri uriImage;

    private String distrito;
    private String localidade;
    private String postoAdministrativo;
    private String comunidade;

    private String phone;
    private String nome ="";
    private String apelido;
    private String passWordPl="";
    public UserPl(String nome,String apelido,String phone, String passWordPl, Uri uriImage, String distrito,String localidade,String postoAdministrativo, String comunidade) {
        this.nome = nome;
        this.passWordPl = passWordPl;
        this.uriImage=uriImage;
        this.phone=phone;
        this.apelido=apelido;
        this.distrito=distrito;
        this.localidade=localidade;
        this.postoAdministrativo=postoAdministrativo;
        this.comunidade=comunidade;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getPostoAdministrativo() {
        return postoAdministrativo;
    }

    public void setPostoAdministrativo(String postoAdministrativo) {
        this.postoAdministrativo = postoAdministrativo;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }

    public UserPl() {}
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassWordPl() {
        return passWordPl;
    }

    public void setPassWordPl(String passWordPl) {
        this.passWordPl = passWordPl;
    }
}

