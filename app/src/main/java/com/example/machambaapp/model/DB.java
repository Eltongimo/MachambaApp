package com.example.machambaapp.model;

import android.net.Uri;

import java.util.ArrayList;

public class DB {


      static ArrayList<DataClassClient> listClient=new ArrayList<>();
      static ArrayList<UserPl> listUsePl=new ArrayList<>();


    public static ArrayList<DataClassClient> getListClient() {
        return listClient;
    }

    public void addArrayListClient(String nome, String apelido,String distrito,String localidade, Uri image){
          listClient.add(new DataClassClient(nome, apelido,distrito,localidade,image));

    }

    public void addArrayListUserPl(String fullname, String senha, Uri uriImage){
        listUsePl.add(new UserPl(fullname, senha,uriImage));

    }

    public static ArrayList<UserPl> getListUsePl() {
        return listUsePl;
    }
}
