package com.example.machambaapp.model;

public class UserPl {
    String userPl="";
    String passWordPl="";
    public UserPl(String userPl, String passWordPl) {
        this.userPl = userPl;
        this.passWordPl = passWordPl;
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
