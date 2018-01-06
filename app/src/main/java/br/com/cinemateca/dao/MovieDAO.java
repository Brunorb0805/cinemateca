package br.com.cinemateca.dao;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cinemateca.model.Movie;

public class MovieDAO {

    private SQLiteDatabase database;
    private SqliteManager sqliteManager;

    @SuppressWarnings("unused")
    private Context context;

    private final String LOG = "MovieDAO";

    private String[] columnTableMovie = {
            sqliteManager.TABLE_MOVIES_imdbID,
            sqliteManager.TABLE_MOVIES_TITLE,
            sqliteManager.TABLE_MOVIES_YEAR,
            sqliteManager.TABLE_MOVIES_RATED,
            sqliteManager.TABLE_MOVIES_RELEASED,
            sqliteManager.TABLE_MOVIES_RUNTIME,
            sqliteManager.TABLE_MOVIES_GENRE,
            sqliteManager.TABLE_MOVIES_DIRECTOR,
            sqliteManager.TABLE_MOVIES_WRITER,
            sqliteManager.TABLE_MOVIES_ACTORS,
            sqliteManager.TABLE_MOVIES_PRODUCTION,
            sqliteManager.TABLE_MOVIES_PLOT,
            sqliteManager.TABLE_MOVIES_LANGUAGE,
            sqliteManager.TABLE_MOVIES_COUNTRY,
            sqliteManager.TABLE_MOVIES_AWARDS,
            sqliteManager.TABLE_MOVIES_POSTER,
            sqliteManager.TABLE_MOVIES_imdbRating,
            sqliteManager.TABLE_MOVIES_TYPE,
            sqliteManager.TABLE_MOVIES_BOX_OFFICE,
            sqliteManager.TABLE_MOVIES_LOCAL_POSTER};

    public MovieDAO(Context context) {
        sqliteManager = new SqliteManager(context);
        this.context = context;
    }

    private void open() {
        sqliteManager = new SqliteManager(context);
        database = sqliteManager.getWritableDatabase();
    }

    private void close() {
        sqliteManager.close();
    }


    /**
     * Get List Movie - Get all movies in database
     *
     * @return List
     */
    public List<Movie> listMovies() {
        ArrayList<Movie> list = new ArrayList<Movie>();

        open();
        @SuppressLint("Recycle")
        Cursor cursor = database.query(SqliteManager.TABLE_MOVIES, new String[]{"*"}, null, null, null, null, columnTableMovie[1]);

        try {
            while (cursor.moveToNext()) {
                Movie movie = new Movie();

                movie.setImdbID(cursor.getString(0));
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getString(2));
                movie.setRated(cursor.getString(3));
                movie.setReleased(cursor.getString(4));
                movie.setRuntime(cursor.getString(5));
                movie.setGenre(cursor.getString(6));
                movie.setDirector(cursor.getString(7));
                movie.setWriter(cursor.getString(8));
                movie.setActors(cursor.getString(9));
                movie.setProduction(cursor.getString(10));
                movie.setPlot(cursor.getString(11));
                movie.setLanguage(cursor.getString(12));
                movie.setCountry(cursor.getString(13));
                movie.setAwards(cursor.getString(14));
                movie.setPoster(cursor.getString(15));
                movie.setImdbRating(cursor.getString(16));
                movie.setType(cursor.getString(17));
                movie.setBoxOffice(cursor.getString(18));
                movie.setLocalPoster(cursor.getString(19));

                list.add(movie);
            }
        } catch (Exception e) {
            Log.e(LOG, "Erro MovieSqlite - listMovies(): " + e.getMessage());
        }

        close();
        return list;
    }


    /**
     * Get Movie - Get a movie with your imdbID
     *
     * @return Boolean
     */
    public Movie getMovie(String imdbID) {

        open();
        Log.i(LOG, "Iniciando Busca");
        @SuppressLint("Recycle")
        Cursor cursor = database.query(SqliteManager.TABLE_MOVIES, new String[]{"*"},
                SqliteManager.TABLE_MOVIES_imdbID + " = ? ", new String[]{imdbID}, null, null, null);
        Log.i(LOG, "Busca realizada. Total de registros: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            Movie movie = new Movie();

            movie.setImdbID(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setYear(cursor.getString(2));
            movie.setRated(cursor.getString(3));
            movie.setReleased(cursor.getString(4));
            movie.setRuntime(cursor.getString(5));
            movie.setGenre(cursor.getString(6));
            movie.setDirector(cursor.getString(7));
            movie.setWriter(cursor.getString(8));
            movie.setActors(cursor.getString(9));
            movie.setProduction(cursor.getString(10));
            movie.setPlot(cursor.getString(11));
            movie.setLanguage(cursor.getString(12));
            movie.setCountry(cursor.getString(13));
            movie.setAwards(cursor.getString(14));
            movie.setPoster(cursor.getString(15));
            movie.setImdbRating(cursor.getString(16));
            movie.setType(cursor.getString(17));
            movie.setBoxOffice(cursor.getString(18));
            movie.setLocalPoster(cursor.getString(19));

            close();
            return movie;
        }

        close();
        return null;
    }


    /**
     * Save movie
     *
     * @return long
     */
    public long saveMovie(Movie movie) {
        ContentValues values = new ContentValues();

        values.put(columnTableMovie[0], movie.getImdbID());
        values.put(columnTableMovie[1], movie.getTitle());
        values.put(columnTableMovie[2], movie.getYear());
        values.put(columnTableMovie[3], movie.getRated());
        values.put(columnTableMovie[4], movie.getReleased());
        values.put(columnTableMovie[5], movie.getRuntime());
        values.put(columnTableMovie[6], movie.getGenre());
        values.put(columnTableMovie[7], movie.getDirector());
        values.put(columnTableMovie[8], movie.getWriter());
        values.put(columnTableMovie[9], movie.getActors());
        values.put(columnTableMovie[10], movie.getProduction());
        values.put(columnTableMovie[11], movie.getPlot());
        values.put(columnTableMovie[12], movie.getLanguage());
        values.put(columnTableMovie[13], movie.getCountry());
        values.put(columnTableMovie[14], movie.getAwards());
        values.put(columnTableMovie[15], movie.getPoster());
        values.put(columnTableMovie[16], movie.getImdbRating());
        values.put(columnTableMovie[17], movie.getType());
        values.put(columnTableMovie[18], movie.getBoxOffice());
        values.put(columnTableMovie[19], movie.getLocalPoster());

        long result = -1;

        open();
        try {
            result = database.insertOrThrow(SqliteManager.TABLE_MOVIES, null, values);
        } catch (SQLiteConstraintException e) {
            result = -1;
        } catch (Exception e) {
            result = -1;
        }

        close();
        return result;
    }

    /**
     * Delete a movie
     *
     * @return long
     */
    public long deleteMovie(Movie movie) {
        open();

        long result = database.delete(SqliteManager.TABLE_MOVIES, columnTableMovie[0] + "=?", new String[]{movie.getImdbID()});

        close();
        return result;
    }
}
