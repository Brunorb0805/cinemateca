package br.com.cinemateca.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.cinemateca.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunorb on 02/01/2018.
 */

public class CinematecaService {

    private static CinematecaAPIService cinematecaApiService;
    private static final String key = "af253783";
    private static final String plot = "full";

    private static CinematecaAPIService getService() {

        if (cinematecaApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.omdbapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            cinematecaApiService = retrofit.create(CinematecaAPIService.class);
        }

        return cinematecaApiService;
    }


    public static void searchMovies(String nameMovie, int page, final CinematecaResponse<List<Movie>> listener) {
            getService().searchMovie(key, nameMovie, page ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    List<Movie> movieList = new ArrayList<>();
                    JsonArray jsonArray = response.body().getAsJsonArray("Search");
                    if(jsonArray != null ) {
                        for (JsonElement element : jsonArray) {
                            movieList.add(gson.fromJson(element, Movie.class));
                        }
                        listener.onResponseSuccess(movieList);

                    } else {
                        listener.onResponseError("");
                    }

                } else {
                    try {
                        listener.onResponseError("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onResponseError("");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onResponseError("");
            }
        });
    }


    public static void searchMoviesPage(String nameMovie, int page, final CinematecaResponse<List<Movie>> listener) {
        getService().searchMovie(key, nameMovie, page ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    List<Movie> movieList = new ArrayList<>();
                    JsonArray jsonArray = response.body().getAsJsonArray("Search");
                    if(jsonArray != null ) {
                        for (JsonElement element : jsonArray) {
                            movieList.add(gson.fromJson(element, Movie.class));
                        }
                        listener.onResponseSuccess(movieList);

                    } else {
                        listener.onResponseError("");
                    }

                } else {
                    try {
                        listener.onResponseError("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onResponseError("");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onResponseError("");
            }
        });
    }

    public static void getMovie(String id, final CinematecaResponse<Movie> listener) {
        getService().getMovie(key, id, plot ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    JsonElement element = response.body();
                    if(element != null ) {
                        listener.onResponseSuccess(gson.fromJson(element, Movie.class));

                    } else {
                        listener.onResponseError("");
                    }

                } else {
                    try {
                        listener.onResponseError("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onResponseError("");                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onResponseError("");            }
        });
    }
}
