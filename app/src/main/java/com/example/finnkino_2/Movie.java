package com.example.finnkino_2;

public class Movie {
    private final String name;
    private final String ID;
    private final String lengthInMinutes;
    private final String genre;
    private final String yearOfProduction;
    private final String ageRestriction;

    public Movie(String nam, String movieID, String length, String gen, String y, String a) {
        name = nam;
        ID = movieID;
        lengthInMinutes = length;
        genre = gen;
        yearOfProduction = y;
        ageRestriction = a;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getLength() {
        return lengthInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return yearOfProduction;
    }

    public String getAge() {
        return ageRestriction;
    }
}
