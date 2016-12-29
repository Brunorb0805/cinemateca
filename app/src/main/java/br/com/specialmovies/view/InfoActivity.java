package br.com.specialmovies.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.specialmovies.R;
import br.com.specialmovies.controller.MovieController;
import br.com.specialmovies.model.Movie;
import br.com.specialmovies.util.ConvertData;

public class InfoActivity extends AppCompatActivity {

    private static String TAG = "InfoActivity";

    private Movie movie;

    private ImageView iv_poster_dialog;
    private TextView tv_title_dialog, tv_title_movie_dialog, tv_value_year_dialog, tv_value_writer_dialog, tv_value_director_dialog,
            tv_value_genre_dialog, tv_value_awards_dialog, tv_value_country_dialog, tv_value_actors_dialog, tv_value_plot_dialog;
    private Button bt_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            movie = (Movie) extras.get("movie");
            inputData();
        }

        if(savedInstanceState != null) {
            movie = (Movie) savedInstanceState.get("objectMovie");
            inputData();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        inputData();

    }

    private void inputData() {
        iv_poster_dialog = (ImageView) findViewById(R.id.iv_poster_dialog);
        tv_title_dialog = (TextView) findViewById(R.id.tv_title_dialog);

        tv_title_movie_dialog = (TextView) findViewById(R.id.tv_title_movie_dialog);
        tv_title_movie_dialog.setText(movie.getTitle());

        String dateRunTime = movie.getReleased();
        dateRunTime += " (" + ConvertData.formatRunTime(movie.getRuntime()) + ")";
        tv_value_year_dialog = (TextView) findViewById(R.id.tv_value_year_dialog);
        tv_value_year_dialog.setText(dateRunTime);

        tv_value_writer_dialog = (TextView) findViewById(R.id.tv_value_writer_dialog);
        tv_value_writer_dialog.setText(movie.getWriter());

        tv_value_director_dialog = (TextView) findViewById(R.id.tv_value_director_dialog);
        tv_value_director_dialog.setText(movie.getDirector());

        tv_value_genre_dialog = (TextView) findViewById(R.id.tv_value_genre_dialog);
        tv_value_genre_dialog.setText(movie.getGenre());

        tv_value_awards_dialog = (TextView) findViewById(R.id.tv_value_awards_dialog);
        tv_value_awards_dialog.setText(movie.getAwards());

        String countryLanguage = movie.getCountry() + "/" + movie.getLanguage();
        tv_value_country_dialog = (TextView) findViewById(R.id.tv_value_country_dialog);
        tv_value_country_dialog.setText(countryLanguage);

        tv_value_actors_dialog = (TextView) findViewById(R.id.tv_value_actors_dialog);
        tv_value_actors_dialog.setText(movie.getActors());

        tv_value_plot_dialog = (TextView) findViewById(R.id.tv_value_plot_dialog);
        tv_value_plot_dialog.setText(movie.getPlot());


        if(!movie.getPoster().equals("N/A")) {
            File imgFile = new  File(movie.getLocalPoster());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_poster_dialog.setImageBitmap(myBitmap);
            }
        } else {
            tv_title_dialog.setText(movie.getTitle());
        }

        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieController movieController = new MovieController(InfoActivity.this);
                movieController.deleteMovie(movie);

                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if( movie != null )
            outState.putSerializable("objectMovie", movie);
    }
}
