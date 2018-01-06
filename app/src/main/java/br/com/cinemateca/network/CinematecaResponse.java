package br.com.cinemateca.network;

/**
 * Created by brunorb on 02/01/2018.
 */

public interface CinematecaResponse<T> {

    void onResponseSuccess(T response);
    void onResponseError(String message);

}
