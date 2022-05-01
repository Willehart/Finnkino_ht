package com.example.finnkino_2;

import java.util.ArrayList;

public class Accounts {

    String currentUser = "";

    // Singleton
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

    // methods for getting the username when logging in or registering and using it in main activity
    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String s) {
        currentUser = s;
    }
}
