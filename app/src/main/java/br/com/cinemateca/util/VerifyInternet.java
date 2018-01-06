package br.com.cinemateca.util;


import android.content.Context;
import android.net.ConnectivityManager;

public class VerifyInternet {
    /**
     * Verifica existência de conexão com a internet
     *
     * @param contexto
     * @return boolean
     */
    public boolean verificarConexao(Context contexto) {
        boolean conectado = false;
        ConnectivityManager conectivtyManager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
