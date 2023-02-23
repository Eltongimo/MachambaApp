package com.example.machambaapp.model;

public class UserAdmin {
    String userName ="barata";
    String passWord="barata";


    public UserAdmin(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public UserAdmin() {

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
