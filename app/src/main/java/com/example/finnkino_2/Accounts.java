package com.example.finnkino_2;

import java.util.ArrayList;

public class Accounts {

    String currentUser = "";

    private static Accounts instance = null;
    ArrayList<User> accountsList = new ArrayList<User>();

    public static Accounts getInstance() {
        if (instance == null) {
            instance = new Accounts();
        }
        return instance;
    }

    private Accounts() {
    }

    public void addAccount(String name, String password) {
        User user = new User(name, password);
        accountsList.add(user);
    }

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String s) {
        currentUser = s;
    }
}
