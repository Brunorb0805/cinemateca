package br.com.specialmovies.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.specialmovies.model.Movie;

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
            sqliteManager.TABLE_MOVIES_PLOT,
            sqliteManager.TABLE_MOVIES_LANGUAGE,
            sqliteManager.TABLE_MOVIES_COUNTRY,
            sqliteManager.TABLE_MOVIES_AWARDS,
            sqliteManager.TABLE_MOVIES_POSTER,
            sqliteManager.TABLE_MOVIES_METASCORE,
            sqliteManager.TABLE_MOVIES_imdbRating,
            sqliteManager.TABLE_MOVIES_imdbVotes,
            sqliteManager.TABLE_MOVIES_TYPE,
            sqliteManager.TABLE_MOVIES_LOCAL_POSTER};

    public MovieDAO(Context context) {
        sqliteManager = new SqliteManager(context);
        this.context = context;
    }

    public void open() {
        sqliteManager = new SqliteManager(context);
        database = sqliteManager.getWritableDatabase();
    }

    public void close() {
        sqliteManager.close();
    }


    /**
     * Retorna lista com todos filmes da tabela
     * @return List
     */
    public List<Movie> listMovies(){
        ArrayList<Movie> list = new ArrayList<Movie>();

        open();
        Cursor cursor = database.query(SqliteManager.TABLE_MOVIES, new String[] {"*"},  null, null, null, null, columnTableMovie[1]);

        try {
            while(cursor.moveToNext()){
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
                movie.setPlot(cursor.getString(10));
                movie.setLanguage(cursor.getString(11));
                movie.setCountry(cursor.getString(12));
                movie.setAwards(cursor.getString(13));
                movie.setPoster(cursor.getString(14));
                movie.setMetascore(cursor.getString(15));
                movie.setImdbRating(cursor.getString(16));
                movie.setImdbVotes(cursor.getString(17));
                movie.setType(cursor.getString(18));
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
     * Busca Cliente - Tabela tem apenas um cliente
     * @return Boolean
     */
    public Boolean movieInTable(String imdbID) {

        open();
        Log.i(LOG, "Iniciando Busca");
        Cursor cursor = database.query(SqliteManager.TABLE_MOVIES, new String[] {"*"},
                SqliteManager.TABLE_MOVIES_imdbID + " = ? ", new String[] {imdbID}, null, null, null);
        Log.i(LOG, "Busca realizada. Total de registros: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            close();
            return true;
        }

        close();
        return false;
    }


    /**
     * Adiciona Filme
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
        values.put(columnTableMovie[10], movie.getPlot());
        values.put(columnTableMovie[11], movie.getLanguage());
        values.put(columnTableMovie[12], movie.getCountry());
        values.put(columnTableMovie[13], movie.getAwards());
        values.put(columnTableMovie[14], movie.getPoster());
        values.put(columnTableMovie[15], movie.getMetascore());
        values.put(columnTableMovie[16], movie.getImdbRating());
        values.put(columnTableMovie[17], movie.getImdbVotes());
        values.put(columnTableMovie[18], movie.getType());
        values.put(columnTableMovie[19], movie.getLocalPoster());



        long result = -1;

        if ( !movieInTable(movie.getImdbID()) ) {
            open();
            try {
                result = database.insertOrThrow(SqliteManager.TABLE_MOVIES, null, values);
            } catch (SQLiteConstraintException e) {
                result = -1;
            } catch (Exception e) {
                result = -1;
            }
        } else {
            result = 0;
        }

        close();
        return result;
    }

    /**
     * Exclui um Filme
     * @return long
     */
    public long deleteMovie(Movie movie) {
        open();

        long result = database.delete(SqliteManager.TABLE_MOVIES, columnTableMovie[0]+"=?", new String[] {movie.getImdbID()});

        close();
        return result;
    }
}
