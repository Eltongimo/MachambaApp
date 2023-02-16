package com.example.machambaapp.model;

import java.util.ArrayList;

public class DB {


      static ArrayList<DataClassClient> listClient=new ArrayList<>();


    public static ArrayList<DataClassClient> getListClient() {
        return listClient;
    }

    public void addArrayListClient(String fullname, int image){
          listClient.add(new DataClassClient(fullname,image));
          view();
    }


    public void view(){
        System.out.println(listClient.toString());
    }
}
