package com.example.techgirls.Models;

public class Users {
    private String email;
    private String password;
    private String name;
    private String login;
    private String gender;
    private String birthday;
    private String role;
    private String country;
    private String uid;
    public Users(){}
    public Users(String email, String password, String name, String login, String gender, String birthday) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.login = login;
        this.gender = gender;
        this.birthday = birthday;
        this.role="USER";
        this.country="Україна";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setUid(String uid){
        this.uid=uid;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public String getCountry(){
        return country;
    }
    public String getUid(){
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String birthday) {
        this.role = role;
    }
}
