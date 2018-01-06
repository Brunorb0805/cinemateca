package br.com.cinemateca.view.Search;

import java.util.List;

import br.com.cinemateca.model.Movie;

/**
 * Created by brunorb on 02/01/2018.
 */

public interface ISearchView {

    void callbackSuccessSearchMovies(List<Movie> response);
    void callbackErrorSearchMovies(String response);
    void callbackSuccessSearchMoviesPage(List<Movie> response);
    void callbackErrorSearchMoviesPage(String response);
    void callbackNoInternet();

    void showLoading();
    void hideLoading();
}
