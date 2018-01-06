package br.com.cinemateca.view.Details;

import java.util.List;

import br.com.cinemateca.model.Movie;

/**
 * Created by brunorb on 04/01/2018.
 */

public interface IDetailsView {

    void callbackSuccessGetMovie(Movie response);
    void callbackErrorGetMovie(String response);
    void callbackNoInternet();

    void showLoading();
    void hideLoading();
}
