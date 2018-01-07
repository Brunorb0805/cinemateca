package br.com.cinemateca.view.Details;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import br.com.cinemateca.R;
import br.com.cinemateca.dao.MovieDAO;
import br.com.cinemateca.model.Movie;
import br.com.cinemateca.util.ConvertData;
import br.com.cinemateca.util.InterpolatorCustom;
import br.com.cinemateca.view.Home.HomeActivity;

public class DetailsActivity extends AppCompatActivity implements IDetailsView {

    //region Attributes
    private static String TAG = "DetailsActivity";
    private static String IMDB_ID = "IMDB_ID";

    private DetailsPresenter mPresenter;
    private Movie mMovie;

    private ImageView mPosterImageView;
    private ImageView mLikedImageView;
    private RelativeLayout mLikedRelativeLayout;
    private RelativeLayout mNoInternetRelativeLayout;
    private RelativeLayout mLoaderRelativeLayout;
    private ScrollView mMainScrollView;
    private TextView mTitleTextView;
    private TextView mYearTextView;
    private TextView mGenreTextView;
    private TextView mYearRuntimeTextView;
    private TextView mPlotTextView;
    private TextView mActorsTextView;
    private TextView mDirectedByTextView;
    private TextView mWrittenByTextView;
    private TextView mProducedByTextView;
    private TextView mCountryLanguageTextView;
    private TextView mReleasedTextView;
    private TextView mAwardsTextView;
    private TextView mBoxOfficeTextView;
    private TextView mRatingTextView;
    private Toolbar mToolbar;


    public static Intent getActivityIntent(Context context, String id) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(IMDB_ID, id);

        return intent;
    }
    //endregion


    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mPresenter = new DetailsPresenter(this);
        mPresenter.setView(this);

        initView();
        initToolbar();
        initActions();
        loadMovieInfo();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loadLocalMovie();
        }

        if (savedInstanceState != null) {
            mMovie = (Movie) savedInstanceState.get("objectMovie");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovie != null)
            outState.putSerializable("objectMovie", mMovie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void callbackSuccessGetMovie(Movie response) {
        mMovie = response;
        setInformationMovie();
        mMainScrollView.setVisibility(View.VISIBLE);
        hideLoading();
    }

    @Override
    public void callbackErrorGetMovie(String response) {
        hideLoading();
    }

    @Override
    public void callbackNoInternet() {
        mLikedRelativeLayout.setEnabled(false);
        mMainScrollView.setVisibility(View.GONE);
        mNoInternetRelativeLayout.setVisibility(View.VISIBLE);
        hideLoading();
    }

    @Override
    public void showLoading() {
        mMainScrollView.setVisibility(View.GONE);
        mLoaderRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoaderRelativeLayout.setVisibility(View.GONE);
    }
    //endregion


    private void initView() {
        mLikedImageView = (ImageView) findViewById(R.id.liked_imageview);
        mNoInternetRelativeLayout = (RelativeLayout) findViewById(R.id.alert_no_internet_relativelayout);
        mLikedRelativeLayout = (RelativeLayout) findViewById(R.id.liked_relativelayout);
        mLoaderRelativeLayout = (RelativeLayout) findViewById(R.id.progress_bar_relativelayout);
        mMainScrollView = (ScrollView) findViewById(R.id.main_scrollview);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initActions() {
        mLikedRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrDeleteMovie();
            }
        });
    }

    private void loadMovieInfo() {

    }

    private void saveOrDeleteMovie() {
        if (mLoaderRelativeLayout.getVisibility() == View.GONE) {
            MovieDAO database = new MovieDAO(this);

            Movie movie = database.getMovie(mMovie.getImdbID());

            if (movie != null) {
                database.deleteMovie(movie);

                final Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim);
                InterpolatorCustom interpolator = new InterpolatorCustom(0.2, 20);
                bounceAnimation.setInterpolator(interpolator);
                bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mLikedImageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_no_like));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                mLikedImageView.startAnimation(bounceAnimation);

                HomeActivity.sIsNewItem = true;
                Toast.makeText(this, getResources().getString(R.string.msg_movie_delete), Toast.LENGTH_SHORT).show();
            } else {
                database.saveMovie(mMovie);
                //database.getMovieWithImdbId(mMovie.getImdbId());

                final Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim);
                InterpolatorCustom interpolator = new InterpolatorCustom(0.2, 20);
                bounceAnimation.setInterpolator(interpolator);
                bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mLikedImageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_like));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                mLikedImageView.startAnimation(bounceAnimation);

                HomeActivity.sIsNewItem = true;
                Toast.makeText(this, getResources().getString(R.string.msg_movie_saved), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_movie_wait), Toast.LENGTH_SHORT).show();
        }
    }


    private void loadLocalMovie() {
        String mMovieImdbId = getIntent().getStringExtra(IMDB_ID);

        MovieDAO database = new MovieDAO(this);
        mMovie = database.getMovie(mMovieImdbId);

        if (mMovie != null) {
            // Setting the "Liked" button.
            mLikedImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_like));
            setInformationMovie();
        } else {
            mPresenter.getMovie(mMovieImdbId);
        }
    }


    private void setInformationMovie() {

        mTitleTextView = (TextView) findViewById(R.id.title_textview);
        mTitleTextView.setText(mMovie.getTitle());

        mYearTextView = (TextView) findViewById(R.id.year_textview);
        mYearTextView.setText(mMovie.getYear());

        mGenreTextView = (TextView) findViewById(R.id.genre_textview);
        mGenreTextView.setText(mMovie.getGenre());

        mYearRuntimeTextView = (TextView) findViewById(R.id.type_runtime_textview);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml("&#8226;");
        }
        String typeRuntime = "";
        if (mMovie.getRuntime() != null && !mMovie.getRuntime().isEmpty() && !mMovie.getRuntime().toLowerCase().equals("n/a")) {
            typeRuntime = String.format(
                    "%s %s %s",
                    ConvertData.firstWordUppercase(mMovie.getType()),
                    result.toString(),
                    ConvertData.formatRunTime(mMovie.getRuntime())
            );
        } else {
            typeRuntime = mMovie.getType();
        }
        mYearRuntimeTextView.setText(typeRuntime);

        mPlotTextView = (TextView) findViewById(R.id.plot_textview);
        mPlotTextView.setText(mMovie.getPlot());

        mActorsTextView = (TextView) findViewById(R.id.actors_textview);
        mActorsTextView.setText(mMovie.getActors());

        mDirectedByTextView = (TextView) findViewById(R.id.directedby_textview);
        mDirectedByTextView.setText(mMovie.getDirector());

        mWrittenByTextView = (TextView) findViewById(R.id.writtenby_textview);
        mWrittenByTextView.setText(mMovie.getWriter());

        mProducedByTextView = (TextView) findViewById(R.id.producedby_textview);
        mProducedByTextView.setText(mMovie.getProduction());

        mCountryLanguageTextView = (TextView) findViewById(R.id.language_textview);
        String countryLanguage = String.format(
                "%s/%s",
                mMovie.getCountry(),
                mMovie.getLanguage()
        );
        mCountryLanguageTextView.setText(countryLanguage);

        mReleasedTextView = (TextView) findViewById(R.id.released_textview);
        mReleasedTextView.setText(mMovie.getReleased());

        mAwardsTextView = (TextView) findViewById(R.id.awards_textview);
        mAwardsTextView.setText(mMovie.getAwards());

        mBoxOfficeTextView = (TextView) findViewById(R.id.box_office_textview);
        mBoxOfficeTextView.setText(mMovie.getBoxOffice());

        mRatingTextView = (TextView) findViewById(R.id.rating_textview);
        mRatingTextView.setText(mMovie.getImdbRating());

        mPosterImageView = (ImageView) findViewById(R.id.poster_imageview);
        if (mMovie.getPoster() != null && !mMovie.getPoster().isEmpty() && !mMovie.getPoster().equals("N/A")) {
            Glide.with(this).load(mMovie.getPoster()).into(mPosterImageView);
        }
    }
}
