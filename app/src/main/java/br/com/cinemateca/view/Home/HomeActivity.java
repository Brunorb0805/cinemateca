package br.com.cinemateca.view.Home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import br.com.cinemateca.R;
import br.com.cinemateca.adapter.HomeMovieListAdapter;
import br.com.cinemateca.model.Movie;
import br.com.cinemateca.view.Search.SearchActivity;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, IHomeView {

    //region Atributes
    private static final String TAG = "HomeActivity";
    public static boolean sIsNewItem = false;

    private HomeMovieListAdapter mHomeMovieListAdapter;
    private List<Movie> mMovieList = new ArrayList<>();
    private HomePresenter mPresenter;

    private RecyclerView mRecyclerView;
    private RelativeLayout mRecyclerViewRelativeLayout;
    private RelativeLayout mNoFavoriteRelativeLayout;
    private TextView mTitleToolbarTextView;
    //endregion


    //region Override Methods
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new HomePresenter(this);
        mPresenter.setView(this);

        initView();
        initRecyclerView();

        mPresenter.getFavoritesMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sIsNewItem) {
            mPresenter.getFavoritesMovies();
            sIsNewItem = false;
        }
    }

    @Override
    public void callbackSuccessGetMovie(List<Movie> response) {
        mMovieList.clear();
        mMovieList.addAll(response);
        mHomeMovieListAdapter.notifyDataSetChanged();
        mRecyclerViewRelativeLayout.setVisibility(View.VISIBLE);
        mNoFavoriteRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void callbackSuccessEmptyGetMovie() {
        mMovieList.clear();
        mHomeMovieListAdapter.notifyDataSetChanged();
        mRecyclerViewRelativeLayout.setVisibility(View.GONE);
        mNoFavoriteRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mHomeMovieListAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mTitleToolbarTextView.setText(getResources().getString(R.string.app_name).toUpperCase());
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleToolbarTextView.setText(null);
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            goSearchActivity();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    //region Private Methods
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recyclerview);
        mNoFavoriteRelativeLayout = (RelativeLayout) findViewById(R.id.alert_no_favorite_relativelayout);
        mRecyclerViewRelativeLayout = (RelativeLayout) findViewById(R.id.recyclerview_relativelayout);
        mTitleToolbarTextView = (TextView) findViewById(R.id.title_toolbar_textView);
    }

    private void initRecyclerView() {
        mHomeMovieListAdapter = new HomeMovieListAdapter(this, mMovieList);

        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHomeMovieListAdapter);
    }

    private void goSearchActivity() {
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_down);
    }
    //endregion
}
