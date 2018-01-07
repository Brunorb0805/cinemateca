package br.com.cinemateca.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.cinemateca.R;
import br.com.cinemateca.model.Movie;
import br.com.cinemateca.util.ConvertData;
import br.com.cinemateca.view.Details.DetailsActivity;
import br.com.cinemateca.view.Search.SearchActivity;

/**
 * Created by brunorb on 03/01/2018.
 */

public class SearchMovieListAdapter extends RecyclerView.Adapter<SearchMovieListAdapter.SearchMovieViewHolder> {

    private SearchActivity mContext;
    private Movie mMovieSelected;

    private final List<Movie> mMovieList;

    public SearchMovieListAdapter(SearchActivity context, List<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public SearchMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movie_list, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchMovieViewHolder holder, int position) {

        final Movie mMovieSelected = mMovieList.get(position);

        if (mMovieSelected.getPoster() != null && !mMovieSelected.getPoster().isEmpty() && !mMovieSelected.getPoster().equals("N/A")) {
            Glide.with(mContext).load(mMovieSelected.getPoster()).into(holder.mPosterImageView);
        } else {
            holder.mPosterImageView.setBackground(mContext.getResources().getDrawable(R.drawable.shadow));
        }

        holder.mTitleTextView.setText(mMovieSelected.getTitle());
        holder.mYearTextView.setText(mMovieSelected.getYear());
        holder.mTypeTextView.setText(ConvertData.firstWordUppercase(mMovieSelected.getType()));

        holder.mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(DetailsActivity.getActivityIntent(mContext, mMovieSelected.getImdbID()));
                mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    class SearchMovieViewHolder extends RecyclerView.ViewHolder {

        final RelativeLayout mMainView;
        final ImageView mPosterImageView;
        final TextView mTitleTextView;
        final TextView mYearTextView;
        final TextView mTypeTextView;


        SearchMovieViewHolder(View itemView) {
            super(itemView);

            mMainView = (RelativeLayout) itemView.findViewById(R.id.main_view);

            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_imageview);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            mYearTextView = (TextView) itemView.findViewById(R.id.year_textView);
            mTypeTextView = (TextView) itemView.findViewById(R.id.type_textView);

        }
    }
}