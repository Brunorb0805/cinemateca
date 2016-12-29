package br.com.specialmovies.webservice;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.specialmovies.model.Movie;
import br.com.specialmovies.util.ConvertData;
import br.com.specialmovies.webservice.util.GetUrl;
import br.com.specialmovies.webservice.util.ResponseSearchMovie;

public class SearchMovieWS  {

    private static final String TAG = "Special Movies";
    private static final String url = GetUrl.getUrlWS();
    private int TimeOut = 60000;

    public ResponseSearchMovie consultarBoletos(String param, Context context) throws IOException {

        final ResponseSearchMovie responseSearchMovie = new ResponseSearchMovie();
        final Movie movie = new Movie();
        String urlGet = url + ConvertData.generateDataRequest(param);

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urlGet, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e("Resposta", response.toString());
                            JSONObject jsonObjectResposta = new JSONObject(response.toString());

                            //Verifica se encontrou a pesquisa do usuario
                            if (Boolean.parseBoolean(jsonObjectResposta.getString("Response")) ) {

                                responseSearchMovie.setStatus(true);

                                //Pega titulo
                                if (jsonObjectResposta.getString("Title") != null) {
                                    movie.setTitle( jsonObjectResposta.getString("Title") );
                                }

                                //Pega ano de lancamento
                                if (jsonObjectResposta.getString("Year") != null) {
                                    movie.setYear( jsonObjectResposta.getString("Year") );
                                }

                                //Pega avaliação
                                if (jsonObjectResposta.getString("Rated") != null) {
                                    movie.setRated( jsonObjectResposta.getString("Rated") );
                                }

                                //Pega o dia de lancamento
                                if (jsonObjectResposta.getString("Released") != null) {
//                                    movie.setReleased( ConvertData.formatStringToDate( jsonObjectResposta.getString("Released") ) );
                                    movie.setReleased( jsonObjectResposta.getString("Released") );
                                }

                                //Pega o tempo de duracao
                                if (jsonObjectResposta.getString("Runtime") != null) {
                                    movie.setRuntime( jsonObjectResposta.getString("Runtime") );
                                }

                                //Pega o genero
                                if (jsonObjectResposta.getString("Genre") != null) {
                                    movie.setGenre( jsonObjectResposta.getString("Genre") );
                                }

                                //Pega o nome do diretor
                                if (jsonObjectResposta.getString("Director") != null) {
                                    movie.setDirector( jsonObjectResposta.getString("Director") );
                                }

                                //Pega o nome do roterista
                                if (jsonObjectResposta.getString("Writer") != null) {
                                    movie.setWriter( jsonObjectResposta.getString("Writer") );
                                }

                                //Pega o nome dos atores
                                if (jsonObjectResposta.getString("Actors") != null) {
                                    movie.setActors( jsonObjectResposta.getString("Actors") );
                                }

                                //Pega o enredo
                                if (jsonObjectResposta.getString("Plot") != null) {
                                    movie.setPlot( jsonObjectResposta.getString("Plot") );
                                }

                                //Pega o idiomas originais
                                if (jsonObjectResposta.getString("Language") != null) {
                                    movie.setLanguage( jsonObjectResposta.getString("Language") );
                                }

                                //Pega os paises de origem
                                if (jsonObjectResposta.getString("Country") != null) {
                                    movie.setCountry( jsonObjectResposta.getString("Country") );
                                }

                                //Pega os premios
                                if (jsonObjectResposta.getString("Awards") != null) {
                                    movie.setAwards( jsonObjectResposta.getString("Awards") );
                                }

                                //Pega a url do poster
                                if (jsonObjectResposta.getString("Poster") != null) {
                                    movie.setPoster( jsonObjectResposta.getString("Poster") );
                                }

                                //Pega o metascore
                                if (jsonObjectResposta.getString("Metascore") != null) {
                                    movie.setMetascore( jsonObjectResposta.getString("Metascore") );
                                }

                                //
                                if (jsonObjectResposta.getString("imdbRating") != null) {
                                    movie.setImdbRating( jsonObjectResposta.getString("imdbRating") );
                                }

                                //
                                if (jsonObjectResposta.getString("imdbVotes") != null) {
                                    movie.setImdbVotes( jsonObjectResposta.getString("imdbVotes") );
                                }

                                //
                                if (jsonObjectResposta.getString("imdbID") != null) {
                                    movie.setImdbID( jsonObjectResposta.getString("imdbID") );
                                }

                                //Pega o tipo
                                if (jsonObjectResposta.getString("Type") != null) {
                                    movie.setType( jsonObjectResposta.getString("Type") );
                                }

                                responseSearchMovie.setMovie(movie);

                            } else {
                                responseSearchMovie.setStatus(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            String responseBody = new String(volleyError.getMessage());
                            JSONObject jsonObjectRespostaErro = new JSONObject(responseBody);


                            Log.d("ResponseErro", jsonObjectRespostaErro.toString());
                        } catch (JSONException e) {
                        }
                    }
                });

        queue.add(getRequest);
        return responseSearchMovie;
    }
}
