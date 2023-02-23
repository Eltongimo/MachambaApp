package com.example.machambaapp.model;

import java.util.ArrayList;

public class DB {


      static ArrayList<DataClassClient> listClient=new ArrayList<>();
      static ArrayList<UserPl> listUsePl=new ArrayList<>();


    public static ArrayList<DataClassClient> getListClient() {
        return listClient;
    }

    public void addArrayListClient(String fullname, int image){
          listClient.add(new DataClassClient(fullname,image));

    }

    public void addArrayListUserPl(String fullname, String senha){
        listUsePl.add(new UserPl(fullname, senha));

    }

    public static ArrayList<UserPl> getListUsePl() {
        return listUsePl;
    }
}
