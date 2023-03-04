package com.example.machambaapp.model;

import android.net.Uri;

import com.example.machambaapp.Cultura;

import java.util.ArrayList;

public class DB {


      static ArrayList<DataClassClient> listClient=new ArrayList<>();
      static ArrayList<UserPl> listUsePl=new ArrayList<>();
    static ArrayList<Cultura> listCultura=new ArrayList<>();


    public static ArrayList<Cultura> getListCultura() {
        return listCultura;
    }

    public void addCultura(String cultura){
        listCultura.add(new Cultura(cultura));
    }


    public static ArrayList<DataClassClient> getListClient() {
        return listClient;
    }

    public void addArrayListClient(String nome, String apelido,String phone,String distrito,String localidade, Uri image, Uri imageDocumento){
          listClient.add(new DataClassClient(nome, apelido,phone,distrito,localidade,image,imageDocumento));

    }

    public void addArrayListUserPl(String name , String apelido,String phone, String senha, Uri uriImage,String distrito, String  localidade,String postoAdministrativo, String  comunidade){
        listUsePl.add(new UserPl(name,apelido,phone,senha,uriImage, distrito, localidade, postoAdministrativo, comunidade));

    }

    public static ArrayList<UserPl> getListUsePl() {
        return listUsePl;
    }
}
