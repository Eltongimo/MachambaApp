package com.example.machambaapp.model;

import android.net.Uri;

public class UserPl {
    Uri uriImage;
    String userPl="";
    String passWordPl="";
    public UserPl(String userPl, String passWordPl, Uri uriImage) {
        this.userPl = userPl;
        this.passWordPl = passWordPl;
        this.uriImage=uriImage;
    }

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }

    public UserPl() {}
    public String getUserPl() {
        return userPl;
    }

    public void setUserPl(String userPl) {
        this.userPl = userPl;
    }

    public String getPassWordPl() {
        return passWordPl;
    }

    public void setPassWordPl(String passWordPl) {
        this.passWordPl = passWordPl;
    }
}
