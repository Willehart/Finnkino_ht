package com.example.finnkino_2;

import java.util.ArrayList;

public class MovieLibrary {
    Boolean ifExists = false;
    Movie theOne = null;

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

    public void newMovie(String name, String ID) {

        ifExists = false;

        for (Movie movie : movieList) {
            if (ID.equals(movie.getID())) {
                ifExists = true;
                break;
            }
        }
        if (!ifExists){
            Movie movie = new Movie(name, ID);
            movieList.add(movie);
        }
    }

    public void getMovie(String id) {
        for (Movie m : movieList) {
            if (id.equals(m.getID())) {
                theOne = m;
            }
        }
    }

    public Movie returnMovie() {
        return theOne;
    }
}
