package com.example.machambaapp;

public class Cultura {

    String cultura;
    String Key;

    public Cultura(String cultura) {
        this.cultura = cultura;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Cultura(String cultura, String key) {
        this.cultura = cultura;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }
}
