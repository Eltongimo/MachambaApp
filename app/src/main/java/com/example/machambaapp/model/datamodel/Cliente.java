package com.example.machambaapp.model.datamodel;

import android.net.Uri;

public class Cliente {
    private String nome, apelido, numero, ano, etnia, genero, key;
    private String distrito, localidade, comunidade,posto;
    private String image, documento;

    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getKey() {
        return key;
    }

    public UserPl pl = new UserPl();

    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public Cliente(){

    }
    public Cliente(String nome, String apelido,
                   String numero, String ano,
                   String genero, String etnia,
                   String distrito, String localidade,
                   String comunidade, String posto,String image, String documento
    ) {
        this.image = image;
        this.nome = nome;
        this.apelido = apelido;
        this.numero = numero;
        this.ano = ano;
        this.etnia = etnia;
        this.genero = genero;
        this.distrito = distrito;
        this.localidade = localidade;
        this.comunidade = comunidade;
        this.posto = posto;
        this.documento = documento;
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
        private String image;
        private String genero;
        private String distrito;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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
        public UserPl(String nome,String apelido, String senha,String genero,String phone, String distrito,String localidade,String postoAdministrativo, String comunidade, String key,String image) {
            this.nome = nome;
            this.phone=phone;
            this.apelido=apelido;
            this.distrito=distrito;
            this.localidade=localidade;
            this.postoAdministrativo=postoAdministrativo;
            this.comunidade=comunidade;
            this.genero=genero;
            this.senha = senha;
            this.key = key;
            this.image = image;
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

        public UserPl() {}

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

    }
}
