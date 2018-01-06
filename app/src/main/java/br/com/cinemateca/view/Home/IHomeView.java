package br.com.cinemateca.view.Home;

import java.util.List;

import br.com.cinemateca.model.Movie;

/**
 * Created by brunorb on 06/01/2018.
 */

public interface IHomeView {

    void callbackSuccessGetMovie(List<Movie> response);
    void callbackSuccessEmptyGetMovie();

}
