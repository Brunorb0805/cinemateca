package br.com.specialmovies.util;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class Alert {

    private Context contexto;

    public Alert(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * Exibe Dialog com Mensagem
     */
    public void exibirMensagem(String mensagem) {

        try {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this.contexto);
            dialogo.setMessage(mensagem);
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
        }catch (Exception e){
            Log.e("BancoIntermedium", e.getMessage());
        }
    }
}
