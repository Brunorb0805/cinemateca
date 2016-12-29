package br.com.specialmovies.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.specialmovies.R;
import br.com.specialmovies.controller.MovieController;
import br.com.specialmovies.model.Movie;
import br.com.specialmovies.view.InfoActivity;

import static br.com.specialmovies.R.drawable.movie;

public class CoverFlowAdapter extends BaseAdapter {

    private List<Movie> data;
    private AppCompatActivity activity;

    private ImageView iv_poster_dialog;
    private TextView tv_title_dialog, tv_title_movie_dialog, tv_value_year_dialog, tv_value_runtime_dialog, tv_value_writer_dialog, tv_value_director_dialog,
            tv_value_genre_dialog, tv_value_awards_dialog, tv_value_country_dialog, tv_value_actors_dialog,
            tv_value_plot_dialog;
    private Button bt_delete;

    public CoverFlowAdapter(AppCompatActivity context, List<Movie> objects) {
        this.activity = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Movie getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String poster = getItem(position).getPoster();

        if(poster != null && !poster.equals("N/A")) {
            File imgFile = new  File(getItem(position).getLocalPoster());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                viewHolder.gameImage.setImageBitmap(myBitmap);

            }
//            Picasso.with(activity).load(getItem(position).getPoster()).into(viewHolder.gameImage);
        } else {
            viewHolder.gameName.setText(data.get(position).getTitle());
        }
//        viewHolder.gameImage.setImageResource(data.get(position).getPoster());
//        viewHolder.gameName.setText(data.get(position).getTitle());

        convertView.setOnClickListener(onClickListener(position));
        convertView.setOnLongClickListener(onLongClickListener(position));

        return convertView;
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, InfoActivity.class);
                intent.putExtra("movie", getItem(position));
                activity.startActivity(intent);
//                final Dialog dialog = new Dialog(activity);
//                dialog.setContentView(R.layout.dialog_movie_info);
//                dialog.setCancelable(true); // dimiss when touching outside
//                dialog.setTitle(activity.getString(R.string.tv_movie_detailse));

//                TextView text = (TextView) dialog.findViewById(R.id.title);
//                text.setText(getItem(position).getTitle());
//                ImageView image = (ImageView) dialog.findViewById(R.id.image);

//                inputData(dialog, position);

//                String poster = getItem(position).getPoster();
//                if(!poster.equals("N/A")) {
//                    Picasso.with(activity).load(getItem(position).getPoster()).into(image);
//                }

//                dialog.show();
            }
        };
    }

    private View.OnLongClickListener onLongClickListener(final int position) {
        return new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        };
    }


    private static class ViewHolder {
        private TextView gameName;
        private ImageView gameImage;

        public ViewHolder(View v) {
            gameImage = (ImageView) v.findViewById(R.id.iv_poster);
            gameName = (TextView) v.findViewById(R.id.tv_title);
        }
    }

//    private void inputData(final Dialog dialog, final int position) {
//        iv_poster_dialog = (ImageView) dialog.findViewById(R.id.iv_poster_dialog);
//        tv_title_dialog = (TextView) dialog.findViewById(R.id.tv_title_dialog);
//
//        tv_title_movie_dialog = (TextView) dialog.findViewById(R.id.tv_title_movie_dialog);
//        tv_title_movie_dialog.setText(getItem(position).getTitle());
//
//        tv_value_year_dialog = (TextView) dialog.findViewById(R.id.tv_value_year_dialog);
//        tv_value_year_dialog.setText(getItem(position).getReleased());
//
//        tv_value_runtime_dialog = (TextView) dialog.findViewById(R.id.tv_value_runtime_dialog);
//        tv_value_runtime_dialog.setText(getItem(position).getRuntime());
//
//        tv_value_writer_dialog = (TextView) dialog.findViewById(R.id.tv_value_writer_dialog);
//        tv_value_writer_dialog.setText(getItem(position).getWriter());
//
//        tv_value_director_dialog = (TextView) dialog.findViewById(R.id.tv_value_director_dialog);
//        tv_value_director_dialog.setText(getItem(position).getDirector());
//
//        tv_value_genre_dialog = (TextView) dialog.findViewById(R.id.tv_value_genre_dialog);
//        tv_value_genre_dialog.setText(getItem(position).getGenre());
//
//        tv_value_awards_dialog = (TextView) dialog.findViewById(R.id.tv_value_awards_dialog);
//        tv_value_awards_dialog.setText(getItem(position).getAwards());
//
//        tv_value_country_dialog = (TextView) dialog.findViewById(R.id.tv_value_country_dialog);
//        tv_value_country_dialog.setText(getItem(position).getCountry());
//
//        tv_value_actors_dialog = (TextView) dialog.findViewById(R.id.tv_value_actors_dialog);
//        tv_value_actors_dialog.setText(getItem(position).getActors());
//
//        tv_value_plot_dialog = (TextView) dialog.findViewById(R.id.tv_value_plot_dialog);
//        tv_value_plot_dialog.setText(getItem(position).getPlot());
//
//
//        if(!getItem(position).getPoster().equals("N/A")) {
//            File imgFile = new  File(getItem(position).getLocalPoster());
//            if(imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                iv_poster_dialog.setImageBitmap(myBitmap);
//            }
//        } else {
//            tv_title_dialog.setText(data.get(position).getTitle());
//        }
//
//        bt_delete = (Button) dialog.findViewById(R.id.bt_delete);
//        bt_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MovieController movieController = new MovieController(activity);
//                movieController.deleteMovie(data.get(position));
//
//                dialog.dismiss();
//            }
//        });
//    }
}