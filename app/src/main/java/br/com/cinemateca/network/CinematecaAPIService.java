package br.com.cinemateca.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.google.gson.JsonObject;


/**
 * Created by brunorb on 02/01/2018.
 */

public interface CinematecaAPIService {

    @GET("?")
    Call<JsonObject> searchMovie(
            @Query("apikey") String key,
            @Query("s") String name,
            @Query("page") int page);


    @GET("?")
    Call<JsonObject> getMovie(
            @Query("apikey") String key,
            @Query("i") String id,
            @Query("plot") String plot);
}
