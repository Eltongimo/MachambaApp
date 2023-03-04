package com.example.machambaapp.model;

import java.security.MessageDigest;

public class Sha {


    public static byte[] encryptSHA(byte[] data, String shaN) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(data);
        return sha.digest();
    }
}
