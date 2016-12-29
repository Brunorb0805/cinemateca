package br.com.specialmovies.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteManager extends SQLiteOpenHelper {

    // Versao do Banco
    public static final int DATABASE_VERSION = 2;

    // Nome do banco
    public static final String DATABASE_NAME = "SpecialMovie.db";


    // TABELA MOVIES
    public static final String TABLE_MOVIES = "TB_MOVIES";


    /**
     * Criação de Nomes de Colunas das Tabelas
     */

    // COLUNAS - TABELA CLIENTE
    public static final String TABLE_MOVIES_imdbID = "imdbID";
    public static  final String TABLE_MOVIES_TITLE = "title";
    public static final String TABLE_MOVIES_YEAR = "year";
    public static final String TABLE_MOVIES_RATED = "rated";
    public static final String TABLE_MOVIES_RELEASED = "released";
    public static final String TABLE_MOVIES_RUNTIME = "runtime";
    public static final String TABLE_MOVIES_GENRE = "genre";
    public static final String TABLE_MOVIES_DIRECTOR = "director";
    public static final String TABLE_MOVIES_WRITER = "writer";
    public static final String TABLE_MOVIES_ACTORS = "actors";
    public static final String TABLE_MOVIES_PLOT = "plot";
    public static final String TABLE_MOVIES_LANGUAGE = "language";
    public static final String TABLE_MOVIES_COUNTRY = "country";
    public static final String TABLE_MOVIES_AWARDS = "awards";
    public static final String TABLE_MOVIES_POSTER = "poster";
    public static final String TABLE_MOVIES_METASCORE = "metascore";
    public static final String TABLE_MOVIES_imdbRating = "imdbRating";
    public static final String TABLE_MOVIES_imdbVotes = "imdbVotes";
    public static final String TABLE_MOVIES_TYPE = "type";
    public static final String TABLE_MOVIES_LOCAL_POSTER = "localPoster";



    /**
     * Criação de Tabelas
     */

    // TABELA MOVIES
    private static final String CREATE_TABLE_MOVIES =
            "Create Table " + TABLE_MOVIES + " ( "
                    + TABLE_MOVIES_imdbID + " text primary key, "
                    + TABLE_MOVIES_TITLE + " text, "
                    + TABLE_MOVIES_YEAR + " text, "
                    + TABLE_MOVIES_RATED + " text, "
                    + TABLE_MOVIES_RELEASED + " text,"
                    + TABLE_MOVIES_RUNTIME + " text,"
                    + TABLE_MOVIES_GENRE + " text,"
                    + TABLE_MOVIES_DIRECTOR + " text,"
                    + TABLE_MOVIES_WRITER + " text,"
                    + TABLE_MOVIES_ACTORS + " text,"
                    + TABLE_MOVIES_PLOT + " text,"
                    + TABLE_MOVIES_LANGUAGE + " text,"
                    + TABLE_MOVIES_COUNTRY + " text,"
                    + TABLE_MOVIES_AWARDS + " text,"
                    + TABLE_MOVIES_POSTER + " text,"
                    + TABLE_MOVIES_METASCORE + " text,"
                    + TABLE_MOVIES_imdbRating + " text,"
                    + TABLE_MOVIES_imdbVotes + " text,"
                    + TABLE_MOVIES_TYPE + " text,"
                    + TABLE_MOVIES_LOCAL_POSTER + " text"

                    + ");";


    public SqliteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);
    }


    private void dropTables(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES + ";");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }
}
