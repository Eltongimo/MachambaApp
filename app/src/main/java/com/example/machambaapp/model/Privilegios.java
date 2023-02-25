package com.example.machambaapp.model;

public class Privilegios {
    static  boolean allAcessView=false;


    public static boolean isAllAcessView() {
        return allAcessView;
    }

    public static void setAllAcessView(boolean allAcessView) {
        Privilegios.allAcessView = allAcessView;
    }
}
