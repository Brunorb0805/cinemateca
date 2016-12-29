package br.com.specialmovies.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.specialmovies.R;
import br.com.specialmovies.adapter.CoverFlowAdapter;
import br.com.specialmovies.controller.MovieController;
import br.com.specialmovies.model.Movie;
import br.com.specialmovies.util.ConvertData;
import br.com.specialmovies.webservice.SearchMovieWS;
import br.com.specialmovies.webservice.util.GetUrl;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class HomeActivity extends AppCompatActivity {

    private static String TAG = "HomeActivity";

    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private List<Movie> movies;
    private FloatingActionButton fabSearch;
    private MovieController movieController;
    private ImageView ivNoMovies;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startComponents();
        actions();

        settingDummyData();
        initAdapter();

    }


    @Override
    protected void onResume() {
        super.onResume();
        settingDummyData();
        initAdapter();
    }


    private void initAdapter() {
        if( !(movies.size() > 0) ) {
            movies.add(new Movie());
            ivNoMovies.setVisibility(View.VISIBLE);
            coverFlow.setVisibility(View.GONE);
        } else {
            ivNoMovies.setVisibility(View.GONE);
            coverFlow.setVisibility(View.VISIBLE);
        }

        adapter = new CoverFlowAdapter(this, movies);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());
    }


    /**
     * Inicializa os componentes da tela
     */
    private void startComponents() {
        movieController = new MovieController(getApplicationContext());

        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        fabSearch = (FloatingActionButton) findViewById(R.id.fabSearch);

        ivNoMovies = (ImageView) findViewById(R.id.ivNoMovies);
    }

    /**
     * define as acoes dos cliques nos objetos da tela
     */
    private void actions() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SearchMovieWS searchMovieWS = new SearchMovieWS();

                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                Log.v(TAG, "position: " + position);
            }

            @Override
            public void onScrolling() {
                Log.i(TAG, "scrolling");
            }
        };
    }

    private void settingDummyData() {
        movies = movieController.getListMovies();
    }
}
