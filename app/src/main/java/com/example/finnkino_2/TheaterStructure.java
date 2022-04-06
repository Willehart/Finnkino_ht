package com.example.finnkino_2;

import java.util.ArrayList;

public class TheaterStructure {
    private static TheaterStructure instance = null;
    ArrayList<Theater> theaterList = new ArrayList<Theater>();

    public static TheaterStructure getInstance() {
        if (instance == null) {
            instance = new TheaterStructure();
        }
        return instance;
    }

    private TheaterStructure() {
    }

    public void newTheater(String name, String ID) {
        Theater theater = new Theater(name, ID);
        theaterList.add(theater);
    }

    public String findTheater(String tName) {
        String tID = "";
        for (Theater theater : theaterList) {
            if (tName == theater.getName()) {
                tID = theater.getID();
            }
        }
        return tID;
    }
}
