package br.com.specialmovies.controller;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.specialmovies.dao.MovieDAO;
import br.com.specialmovies.model.Movie;

public class MovieController {

    private MovieDAO dao;
    private Context context;

    public MovieController(Context context) {
        this.context = context;
    }


    public long saveMovie(Movie movie) {
        dao = new MovieDAO(context);

        return dao.saveMovie(movie);
    }


    public List<Movie> getListMovies() {
        dao = new MovieDAO(context);

        return dao.listMovies();
    }

    public long deleteMovie(Movie movie) {
        dao = new MovieDAO(context);

        return dao.deleteMovie(movie);
    }
}
