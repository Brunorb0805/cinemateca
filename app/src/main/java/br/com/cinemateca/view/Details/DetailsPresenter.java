package br.com.cinemateca.view.Details;

import android.content.Context;

import java.util.List;

import br.com.cinemateca.model.Movie;
import br.com.cinemateca.network.CinematecaResponse;
import br.com.cinemateca.network.CinematecaService;
import br.com.cinemateca.util.VerifyInternet;

/**
 * Created by brunorb on 04/01/2018.
 */

public class DetailsPresenter {

    private final Context mContext;
    private IDetailsView mView;
    private VerifyInternet verifyInternet;

    public DetailsPresenter(final Context context) {
        this.mContext = context;
        verifyInternet = new VerifyInternet();
    }

    public void setView(final IDetailsView view) {
        this.mView = view;
    }

    void getMovie(String id) {
        if (verifyInternet.verificarConexao(mContext)) {
            mView.showLoading();
            CinematecaService.getMovie(id, new CinematecaResponse<Movie>() {
                @Override
                public void onResponseSuccess(Movie response) {
                    mView.callbackSuccessGetMovie(response);
                }

                @Override
                public void onResponseError(String response) {
                    mView.callbackErrorGetMovie(response);
                }
            });
        } else {
            mView.callbackNoInternet();
        }
    }
}
