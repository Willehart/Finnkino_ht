package com.example.finnkino_2;

public class Movie {
    private String name;
    private String ID;

    public Movie(String nam, String movieID) {
        name = nam;
        ID = movieID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }
}
