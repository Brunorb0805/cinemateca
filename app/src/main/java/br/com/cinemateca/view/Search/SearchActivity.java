package br.com.cinemateca.view.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.cinemateca.R;
import br.com.cinemateca.adapter.SearchMovieListAdapter;
import br.com.cinemateca.model.Movie;
import br.com.cinemateca.util.EndlessScrollListener;


/**
 * Created by brunorb on 02/01/2018.
 */

public class SearchActivity extends AppCompatActivity implements ISearchView {

    //region Atributes
    private static String TAG = "Search Activity";

    private int mPage = 1;
    private boolean isSearch = false;

    private SearchMovieListAdapter mAdapter;
    private SearchPresenter mPresenter;

    private AVLoadingIndicatorView mLoader;
    private EditText mSearchEditText;
    private List<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RelativeLayout mAlertNotFoundRelativeLayout;
    private RelativeLayout mAlertNoInternetRelativeLayout;
    private LinearLayout mLoaderLinearLayout;

    private Toolbar mToolbar;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPresenter = new SearchPresenter(this);
        mPresenter.setView(this);

        initToolbar();
        initView();
        initRecyclerView();
        action();

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
        forceHideKeyboard();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void callbackSuccessSearchMovies(List<Movie> response) {
        mMovieList.clear();
        mMovieList.addAll(response);
        mAdapter.notifyDataSetChanged();
        isSearch = false;
        hideLoading();
    }

    @Override
    public void callbackErrorSearchMovies(String response) {
        mMovieList.clear();
        mAdapter.notifyDataSetChanged();
        hideLoading();
        mAlertNotFoundRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void callbackSuccessSearchMoviesPage(List<Movie> response) {
        mMovieList.addAll(response);
        mAdapter.notifyDataSetChanged();
        isSearch = false;
        hideLoading();
    }

    @Override
    public void callbackErrorSearchMoviesPage(String response) {
        hideLoading();
    }

    @Override
    public void callbackNoInternet() {
        mMovieList.clear();
        mAdapter.notifyDataSetChanged();
        mAlertNoInternetRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mAlertNoInternetRelativeLayout.setVisibility(View.GONE);
        mAlertNotFoundRelativeLayout.setVisibility(View.GONE);
        mLoaderLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoaderLinearLayout.setVisibility(View.GONE);
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

    }

    /**
     * Inicializa os componentes da tela
     */
    private void initView() {
        mLoader = (AVLoadingIndicatorView) findViewById(R.id.loader);
        mSearchEditText = (EditText) findViewById(R.id.et_search);
        mLoaderLinearLayout = (LinearLayout) findViewById(R.id.loader_linearlayout);
        mAlertNoInternetRelativeLayout = (RelativeLayout) findViewById(R.id.alert_no_internet_relativelayout);
        mAlertNotFoundRelativeLayout = (RelativeLayout) findViewById(R.id.alert_not_found_relativelayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recyclerview);

    }

    private void initRecyclerView() {
        mAdapter = new SearchMovieListAdapter(this, mMovieList);

        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void action() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    mPage = 1;
                    sendRequest();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                showLoading();
                if (this != null) {
                    mPage++;
                    sendRequest();
                }
            }
        });

    }


    private void sendRequest() {
        isSearch = true;
        mPresenter.searchMovies(mSearchEditText.getText().toString(), mPage);
    }

    public void forceHideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}