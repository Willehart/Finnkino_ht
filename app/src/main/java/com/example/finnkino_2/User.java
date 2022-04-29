package com.example.finnkino_2;

public class User {
    private final String username;
    private final String password;

    public User(String n, String pw) {
        username = n;
        password = pw;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
