package br.com.cinemateca.view.Home;

import android.content.Context;

import java.util.List;

import br.com.cinemateca.dao.MovieDAO;
import br.com.cinemateca.model.Movie;

/**
 * Created by brunorb on 06/01/2018.
 */

public class HomePresenter {

    private final Context mContext;
    private IHomeView mView;

    public HomePresenter(final Context context) {
        this.mContext = context;
    }

    public void setView(final IHomeView view) {
        this.mView = view;
    }


    void getFavoritesMovies() {
        MovieDAO movieDAO = new MovieDAO(mContext);

        List<Movie> list = movieDAO.listMovies();

        if(list == null || list.isEmpty()) {
            mView.callbackSuccessEmptyGetMovie();
        } else {
            mView.callbackSuccessGetMovie(movieDAO.listMovies());
        }
    }
}