package com.example.machambaapp.model.helper;

import com.example.machambaapp.model.datamodel.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CollectionOperations {


    public static ArrayList<Cliente.UserPl> sortUsersPL(ArrayList<Cliente.UserPl> users){
        Collections.sort(users, new Comparator<Cliente.UserPl>() {
            @Override
            public int compare(Cliente.UserPl u1, Cliente.UserPl u2) {

                return u1.getNome().compareTo(u2.getNome());

            }
        });
        return users;
    }
  }
