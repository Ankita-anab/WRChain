package com.example.wrchain.View.ModalClass;

public class Users {
    String username, email,password,user_type,number;


    public Users(String username, String email, String password, String user_type,String number) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
        this.number=number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}


