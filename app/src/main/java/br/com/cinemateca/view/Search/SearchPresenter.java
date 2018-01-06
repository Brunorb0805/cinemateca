package br.com.cinemateca.view.Search;

import android.content.Context;

import java.util.List;

import br.com.cinemateca.model.Movie;
import br.com.cinemateca.network.CinematecaResponse;
import br.com.cinemateca.network.CinematecaService;
import br.com.cinemateca.util.ConvertData;
import br.com.cinemateca.util.VerifyInternet;

/**
 * Created by brunorb on 04/01/2018.
 */

public class SearchPresenter {

    private final Context mContext;
    private ISearchView mView;
    private VerifyInternet verifyInternet;

    public SearchPresenter(final Context context) {
        this.mContext = context;
        verifyInternet = new VerifyInternet();
    }

    public void setView(final ISearchView view) {
        this.mView = view;
    }

    void searchMovies(String name, int page) {
        if (verifyInternet.verificarConexao(mContext)) {
            mView.showLoading();
            if (page == 1) {
                CinematecaService.searchMovies(ConvertData.generateDataRequest(name), page, new CinematecaResponse<List<Movie>>() {
                    @Override
                    public void onResponseSuccess(List<Movie> response) {
                        mView.callbackSuccessSearchMovies(response);
                    }

                    @Override
                    public void onResponseError(String response) {
                        mView.callbackErrorSearchMovies(response);
                    }
                });
            } else {
                CinematecaService.searchMoviesPage(ConvertData.generateDataRequest(name), page, new CinematecaResponse<List<Movie>>() {
                    @Override
                    public void onResponseSuccess(List<Movie> response) {
                        mView.callbackSuccessSearchMoviesPage(response);
                    }

                    @Override
                    public void onResponseError(String response) {
                        mView.callbackErrorSearchMoviesPage(response);
                    }
                });
            }
        } else {
            mView.callbackNoInternet();
        }
    }
}
