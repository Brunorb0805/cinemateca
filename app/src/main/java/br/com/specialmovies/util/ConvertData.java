package br.com.specialmovies.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertData {

    /**
     * Funcao usada para geral url request com o nome do filme passado pelo usuario
     *
     * @param titleMovie
     * @return String
     */
    public static String generateDataRequest(String titleMovie) {

        String[] words = titleMovie.split(" ");
        int i;

        StringBuilder medicamento = new StringBuilder("t=");
        for (i = 0; i < words.length-1; i++) {
            medicamento.append(words[i]);
            medicamento.append("+");
        }
        medicamento.append(words[words.length-1]);


        return medicamento.toString()+"&y=&plot=full&r=json";
    }


    /**
     * Trasnforma string '01 Jan 2000' em um Date
     *
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static Date formatStringToDate(String date ) throws ParseException {
        final DateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");

        return fmt.parse(date);
    }


    public static String formatRunTime(String runTime) {
        Integer time = Integer.parseInt( runTime.split(" ")[0] );

        if(time >= 60) {
            int hour = time / 60;
            int minutes = time % 60;

            return String.valueOf(hour)+"h"+String.valueOf(minutes)+"min";
        }
        else {
            return runTime;
        }
    }

    public static String concatList(String[] strings) {
        String aux = "";
        for(String s : strings) {
            aux += s;
        }

        return aux;
    }
}
