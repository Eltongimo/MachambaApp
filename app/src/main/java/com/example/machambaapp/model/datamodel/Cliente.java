package com.example.machambaapp.model.datamodel;

import android.net.Uri;

public class Cliente {
    private String nome, apelido, numero, ano, etnia, genero;
    public Cliente(String nome, String apelido, String numero, String ano, String genero,String etnia) {
        this.nome = nome;
        this.apelido = apelido;
        this.numero = numero;
        this.ano = ano;
        this.etnia = etnia;
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public static class UserPl {
        private Uri uriImage;
        private String genero;
        private String distrito;
        private String localidade;
        private String postoAdministrativo;
        private String comunidade;

        private String senha;
        private String phone;
        private String nome ="";
        private String apelido;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        private String key;
        public UserPl(String nome,String apelido, String senha,String genero,String phone, Uri uriImage, String distrito,String localidade,String postoAdministrativo, String comunidade, String key) {
            this.nome = nome;
            this.uriImage=uriImage;
            this.phone=phone;
            this.apelido=apelido;
            this.distrito=distrito;
            this.localidade=localidade;
            this.postoAdministrativo=postoAdministrativo;
            this.comunidade=comunidade;
            this.genero=genero;
            this.senha = senha;
            this.key = key;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
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

    }
}
