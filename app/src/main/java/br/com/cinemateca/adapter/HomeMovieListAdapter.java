package br.com.cinemateca.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.cinemateca.R;
import br.com.cinemateca.model.Movie;
import br.com.cinemateca.util.ConvertData;
import br.com.cinemateca.view.Details.DetailsActivity;

/**
 * Created by brunorb on 06/01/2018.
 */

public class HomeMovieListAdapter extends RecyclerView.Adapter<HomeMovieListAdapter.MovieViewHolder> implements Filterable {


    private List<Movie> mMoviesList;
    private List<Movie> mFilteredList;
    private final Activity mContext;
    private MoviesFilter mMoviesFilter;

    public HomeMovieListAdapter(Activity context, List<Movie> newsList) {
        mContext = context;
        mMoviesList = newsList;
        mFilteredList = newsList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        final Movie mMovieSelected = mFilteredList.get(position);

        if (!mMovieSelected.getPoster().equals("N/A")) {
            Glide.with(mContext).load(mMovieSelected.getPoster()).into(holder.mPosterImageView);
        } else {
            holder.mPosterImageView.setBackground(mContext.getResources().getDrawable(R.drawable.shadow));
        }

        //Set Title value
        holder.mTitleTextView.setText(mMovieSelected.getTitle());
        //Set Genre value
        holder.mGenreTextView.setText(mMovieSelected.getGenre());

        //Set Type, Year and Runtime values
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml("&#8226;");
        }
        String typeYearRuntime = "";
        if (mMovieSelected.getRuntime() != null && !mMovieSelected.getRuntime().isEmpty() && !mMovieSelected.getRuntime().toLowerCase().equals("n/a")) {
            typeYearRuntime = String.format(
                    "%s %s %s %s %s",
                    ConvertData.firstWordUppercase(mMovieSelected.getType()),
                    result.toString(),
                    mMovieSelected.getYear(),
                    result.toString(),
                    ConvertData.formatRunTime(mMovieSelected.getRuntime())
            );
        } else {
            typeYearRuntime = String.format(
                    "%s %s %s",
                    ConvertData.firstWordUppercase(mMovieSelected.getType()),
                    result.toString(),
                    mMovieSelected.getYear()
            );
        }
        holder.mTypeYearRuntimeTextView.setText(typeYearRuntime);

        //Set Country value
        holder.mCountryTextView.setText(mMovieSelected.getCountry());


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
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        if (mMoviesFilter == null) {
            mMoviesFilter = new HomeMovieListAdapter.MoviesFilter();
        }
        return mMoviesFilter;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        final RelativeLayout mMainView;
        final ImageView mPosterImageView;
        final TextView mTitleTextView;
        final TextView mTypeYearRuntimeTextView;
        final TextView mGenreTextView;
        final TextView mCountryTextView;


        MovieViewHolder(View itemView) {
            super(itemView);

            mMainView = (RelativeLayout) itemView.findViewById(R.id.main_view);

            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_imageview);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            mTypeYearRuntimeTextView = (TextView) itemView.findViewById(R.id.type_year_runtime_textView);
            mGenreTextView = (TextView) itemView.findViewById(R.id.genre_textView);
            mCountryTextView = (TextView) itemView.findViewById(R.id.country_textView);

        }
    }

    private class MoviesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Movie> tempList = new ArrayList<>();

                // search content in friend list
                for (Movie news : mMoviesList) {
                    if (news.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(news);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mMoviesList.size();
                filterResults.values = mMoviesList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredList = (ArrayList<Movie>) results.values;
            notifyDataSetChanged();
        }
    }

}
