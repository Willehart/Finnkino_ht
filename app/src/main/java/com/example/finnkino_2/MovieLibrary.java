package com.example.finnkino_2;

import java.util.ArrayList;

public class MovieLibrary {
    Boolean ifExists = false;
    Movie theOne = null;
    String time;

    // Singleton
    private static MovieLibrary instance = null;
    ArrayList<Movie> movieList = new ArrayList<Movie>();

    public static MovieLibrary getInstance() {
        if (instance == null) {
            instance = new MovieLibrary();
        }
        return instance;
    }

    private MovieLibrary() {
    }

    public void newMovie(String title, String ID, String lengthInMinutes, String genre, String year, String age) {

        ifExists = false;

        for (Movie movie : movieList) {
            if (ID.equals(movie.getID())) {
                ifExists = true;
                break;
            }
        }
        if (!ifExists){
            Movie movie = new Movie(title, ID, lengthInMinutes, genre, year, age);
            movieList.add(movie);
        }
    }

    public void findMovie(String id) {
        for (Movie m : movieList) {
            if (id.equals(m.getID())) {
                theOne = m;
            }
        }
    }
    public Movie returnMovie() {
        return theOne;
    }

    // methods used for transferring the selected movie time to the preview activity
    public void setTime(String t) {
        time = t;
    }
    public String getTime() {
        return time;
    }
}
