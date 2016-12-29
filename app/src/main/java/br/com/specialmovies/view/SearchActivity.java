package br.com.specialmovies.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.specialmovies.R;
import br.com.specialmovies.controller.MovieController;
import br.com.specialmovies.model.Movie;
import br.com.specialmovies.util.Alert;
import br.com.specialmovies.util.ConvertData;
import br.com.specialmovies.util.VerifyInternet;
import br.com.specialmovies.webservice.util.GetUrl;

public class SearchActivity extends AppCompatActivity {

    private static String TAG = "Search Activity";

    private static final String url = GetUrl.getUrlWS();
    private Movie movie;
    private String urlGet;
    private File file;
    private String path;

    private ProgressDialog progressDialog;
    private VerifyInternet verifyInternet;
    private Alert alert;
    private MovieController movieController;

    private FloatingActionButton fabSave, fabCancel;
    private Button bt_search;
    private RelativeLayout rl_search;
    private EditText et_search;
    private ImageView iv_poster;
    private TextView tv_title_movie, tv_value_year, tv_value_runtime, tv_value_writer, tv_value_director,
            tv_value_genre, tv_value_awards, tv_value_country, tv_value_actors,
            tv_value_plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startComponents();
        actions();

        if(savedInstanceState != null) {
            movie = (Movie) savedInstanceState.get("objectMovie");
            inputData();
        }
    }

    /**
     * Inicializa os componentes da tela
     */
    private void startComponents() {
        verifyInternet = new VerifyInternet();
        alert = new Alert(SearchActivity.this);
        movieController = new MovieController(getApplicationContext());

        bt_search = (Button) findViewById(R.id.bt_search);
        et_search = (EditText) findViewById(R.id.et_search);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabCancel = (FloatingActionButton) findViewById(R.id.fabCancel);

        rl_search = (RelativeLayout) findViewById(R.id.rl_search);

        iv_poster = (ImageView) findViewById(R.id.iv_poster);
        iv_poster.setBackgroundResource(R.drawable.shadow);

        tv_title_movie = (TextView) findViewById(R.id.tv_title_movie);
        tv_value_year = (TextView) findViewById(R.id.tv_value_year);
        tv_value_runtime = (TextView) findViewById(R.id.tv_value_runtime);
        tv_value_writer= (TextView) findViewById(R.id.tv_value_writer);
        tv_value_director = (TextView) findViewById(R.id.tv_value_director);
        tv_value_genre = (TextView) findViewById(R.id.tv_value_genre);
        tv_value_awards = (TextView) findViewById(R.id.tv_value_awards);
        tv_value_country = (TextView) findViewById(R.id.tv_value_country);
        tv_value_actors = (TextView) findViewById(R.id.tv_value_actors);
        tv_value_plot = (TextView) findViewById(R.id.tv_value_plot);
    }

    /**
     * define as acoes dos cliques nos objetos da tela
     */
    private void actions() {
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( verifyInternet.verificarConexao(SearchActivity.this) ) {
                    progressDialog = ProgressDialog.show(SearchActivity.this, null,
                            getString(R.string.searching));
//                    progressDialog.show();

                    ocultarTecladoVirtual();
                    sendRequest();
                } else {
                    alert.exibirMensagem(getString(R.string.msg_no_internet));
                }


            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(SearchActivity.this, null,
                        getString(R.string.saving));
//                progressDialog.show();

                long resp = -1;
                try {
                    if (movie != null) {

                        String nameFile = ConvertData.concatList(movie.getTitle().split(" "));

                        if (!movie.getPoster().equals("N/A")) {
                            Picasso.with(getApplicationContext())
                                    .load(movie.getPoster())
                                    .into(getTarget(nameFile + ".jpeg"));

//                            String path = file.getAbsoluteFile().toString();
                            while(path == null) {
                                Thread.sleep(1000);
                            }
                            movie.setLocalPoster(path);
                        }

                        resp = movieController.saveMovie(movie);
                        path = null;

                        if (resp > 0) {
                            //cadastro feito
                            alert.exibirMensagem(movie.getTitle() + " " + getString(R.string.msg_movie_saved));
                            movie = null;
                            rl_search.setVisibility(View.GONE);
                        } else if (resp == 0) {
                            //ja cadastrado
                            alert.exibirMensagem(movie.getTitle() + " " + getString(R.string.msg_movie_dont_saved));
                            movie = null;
                            rl_search.setVisibility(View.GONE);

                        } else {
                            //falha cadastro
                            alert.exibirMensagem(getString(R.string.msg_error_save));
                        }
                    } else {
                        alert.exibirMensagem(getString(R.string.msg_movie_null));
                    }
                } catch (Exception e) {
                    alert.exibirMensagem(getString(R.string.msg_error_path));
                }
                progressDialog.dismiss();
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void sendRequest() {

        String params = et_search.getText().toString();

        if(params.length() > 0) {
            urlGet = url + ConvertData.generateDataRequest(params);

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urlGet, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            movie = new Movie();
                            try {
                                Log.e("Resposta", response.toString());
                                JSONObject jsonObjectResposta = new JSONObject(response.toString());

                                //Verifica se encontrou a pesquisa do cliente
                                if (Boolean.parseBoolean(jsonObjectResposta.getString("Response"))) {

                                    //Pega a url do poster da producao
                                    if (jsonObjectResposta.getString("Poster") != null) {
                                        movie.setPoster(jsonObjectResposta.getString("Poster"));

                                    }

                                    //Pega titulo da producao
                                    if (jsonObjectResposta.getString("Title") != null) {
                                        movie.setTitle(jsonObjectResposta.getString("Title"));
                                    }

                                    //Pega ano de lancamento da producao
                                    if (jsonObjectResposta.getString("Year") != null) {
                                        movie.setYear( jsonObjectResposta.getString("Year"));
                                    }

                                    //Pega avaliação da producao
                                    if (jsonObjectResposta.getString("Rated") != null) {
                                        movie.setRated(jsonObjectResposta.getString("Rated"));
                                    }

                                    //Pega o dia de lancamento da producao
                                    if (jsonObjectResposta.getString("Released") != null) {
                                        movie.setReleased(jsonObjectResposta.getString("Released"));
                                    }

                                    //Pega o tempo de duracao da producao
                                    if (jsonObjectResposta.getString("Runtime") != null) {
                                        movie.setRuntime(jsonObjectResposta.getString("Runtime"));
                                    }

                                    //Pega o genero da producao
                                    if (jsonObjectResposta.getString("Genre") != null) {
                                        movie.setGenre(jsonObjectResposta.getString("Genre"));
                                    }

                                    //Pega o nome do diretor da producao
                                    if (jsonObjectResposta.getString("Director") != null) {
                                        movie.setDirector(jsonObjectResposta.getString("Director"));
                                    }

                                    //Pega o nome do roterista do filme
                                    if (jsonObjectResposta.getString("Writer") != null) {
                                        movie.setWriter(jsonObjectResposta.getString("Writer"));
                                    }

                                    //Pega o nome dos atores da producao
                                    if (jsonObjectResposta.getString("Actors") != null) {
                                        movie.setActors(jsonObjectResposta.getString("Actors"));
                                    }

                                    //Pega o enredo da producao
                                    if (jsonObjectResposta.getString("Plot") != null) {
                                        movie.setPlot(jsonObjectResposta.getString("Plot"));
                                    }

                                    //Pega os paises de origem da producao
                                    if (jsonObjectResposta.getString("Country") != null) {
                                        movie.setCountry(jsonObjectResposta.getString("Country"));
                                    }

                                    //Pega o idiomas originais da producao
                                    if (jsonObjectResposta.getString("Language") != null) {
                                        movie.setLanguage(jsonObjectResposta.getString("Language"));
                                    }

                                    //Pega os premios conqusitados pela producao
                                    if (jsonObjectResposta.getString("Awards") != null) {
                                        movie.setAwards(jsonObjectResposta.getString("Awards"));
                                    }

                                    //Pega o metascore da producao
                                    if (jsonObjectResposta.getString("Metascore") != null) {
                                        movie.setMetascore(jsonObjectResposta.getString("Metascore"));
                                    }

                                    //
                                    if (jsonObjectResposta.getString("imdbRating") != null) {
                                        movie.setImdbRating(jsonObjectResposta.getString("imdbRating"));
                                    }

                                    //
                                    if (jsonObjectResposta.getString("imdbVotes") != null) {
                                        movie.setImdbVotes(jsonObjectResposta.getString("imdbVotes"));
                                    }

                                    //
                                    if (jsonObjectResposta.getString("imdbID") != null) {
                                        movie.setImdbID(jsonObjectResposta.getString("imdbID"));
                                    }

                                    //Pega o tipo da producao
                                    if (jsonObjectResposta.getString("Type") != null) {
                                        movie.setType(jsonObjectResposta.getString("Type"));
                                    }

                                    inputData();
                                    progressDialog.dismiss();

                                } else {
                                    progressDialog.dismiss();
                                    alert.exibirMensagem(getString(R.string.msg_movie_dont_found));

                                }
                            } catch (Exception e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            try {
                                progressDialog.dismiss();

                                String responseBody = new String(volleyError.getMessage());
//                                JSONObject jsonObjectRespostaErro = new JSONObject(responseBody);

                                alert.exibirMensagem(getString(R.string.msg_error_server));

                                Log.d(TAG, "ResponseErro: "+responseBody);//jsonObjectRespostaErro.toString());
                            } catch (Exception e) {
                            }
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(getRequest);
        }
        else {
            progressDialog.dismiss();

            alert.exibirMensagem(getString(R.string.msg_et_search_empty));
        }
    }


    private void inputData() {
        if( movie != null ) {
            Picasso.with(SearchActivity.this).load(movie.getPoster()).into(iv_poster);
            tv_title_movie.setText(movie.getTitle());

            String dateRunTime = movie.getReleased();
            dateRunTime += " (" + ConvertData.formatRunTime(movie.getRuntime()) + ")";
            tv_value_year.setText(dateRunTime);

            tv_value_genre.setText(movie.getGenre());
            tv_value_director.setText(movie.getDirector());
            tv_value_writer.setText(movie.getWriter());

            tv_value_actors.setText(movie.getActors());
//
            tv_value_plot.setText(movie.getPlot());

            String countryLanguage = movie.getCountry() + "/" + movie.getLanguage();
            tv_value_country.setText(countryLanguage);

            tv_value_awards.setText(movie.getAwards());

            rl_search.setVisibility(View.VISIBLE);
            et_search.setText("");
        }
    }

    /**
     * Gera um arquivo para salvar imagem do poster
     *
     * @param dirName
     * @param fileName
     * @param context
     */
    private File getSdCardFile(String dirName, String fileName, Context context) {
        File sdcard = context.getExternalFilesDir("emfile");
        File dir = new File(sdcard, dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, fileName);
        return file;
    }


    /**
     *Gera um objeto Target para salvar imagem no dispositivo
     *
     * @param url
     * @return
     */
    private Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        file = getSdCardFile("specialmovie", url, SearchActivity.this);
//                        file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                            path = file.getAbsolutePath().toString();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }


    /**
     * Esconde o teclado
     */
    private void ocultarTecladoVirtual() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if( movie != null )
            outState.putSerializable("objectMovie", movie);
    }
}
