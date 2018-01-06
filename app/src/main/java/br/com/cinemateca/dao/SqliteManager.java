package br.com.cinemateca.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteManager extends SQLiteOpenHelper {

    // Versao do Banco
    public static final int DATABASE_VERSION = 1;

    // Nome do banco
    public static final String DATABASE_NAME = "Cinemateca.db";


    // TABELA MOVIES
    public static final String TABLE_MOVIES = "TB_MOVIES";


    /**
     * Criação de Nomes de Colunas das Tabelas
     */

    // COLUNAS - TABELA CLIENTE
    static final String TABLE_MOVIES_imdbID = "imdbID";
    static final String TABLE_MOVIES_TITLE = "title";
    static final String TABLE_MOVIES_YEAR = "year";
    static final String TABLE_MOVIES_RATED = "rated";
    static final String TABLE_MOVIES_RELEASED = "released";
    static final String TABLE_MOVIES_RUNTIME = "runtime";
    static final String TABLE_MOVIES_GENRE = "genre";
    static final String TABLE_MOVIES_DIRECTOR = "director";
    static final String TABLE_MOVIES_WRITER = "writer";
    static final String TABLE_MOVIES_ACTORS = "actors";
    static final String TABLE_MOVIES_PRODUCTION = "production";
    static final String TABLE_MOVIES_PLOT = "plot";
    static final String TABLE_MOVIES_LANGUAGE = "language";
    static final String TABLE_MOVIES_COUNTRY = "country";
    static final String TABLE_MOVIES_AWARDS = "awards";
    static final String TABLE_MOVIES_POSTER = "poster";
    static final String TABLE_MOVIES_imdbRating = "imdbRating";
    static final String TABLE_MOVIES_TYPE = "type";
    static final String TABLE_MOVIES_BOX_OFFICE = "boxOffice";
    static final String TABLE_MOVIES_LOCAL_POSTER = "localPoster";



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
                    + TABLE_MOVIES_PRODUCTION + " text,"
                    + TABLE_MOVIES_PLOT + " text,"
                    + TABLE_MOVIES_LANGUAGE + " text,"
                    + TABLE_MOVIES_COUNTRY + " text,"
                    + TABLE_MOVIES_AWARDS + " text,"
                    + TABLE_MOVIES_POSTER + " text,"
                    + TABLE_MOVIES_imdbRating + " text,"
                    + TABLE_MOVIES_TYPE + " text,"
                    + TABLE_MOVIES_BOX_OFFICE + " text,"
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
