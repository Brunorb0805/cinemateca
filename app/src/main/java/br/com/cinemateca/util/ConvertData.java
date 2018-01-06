package br.com.cinemateca.util;


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

        StringBuilder sb = new StringBuilder("");
        for (i = 0; i < words.length-1; i++) {
            sb.append(words[i]);
            sb.append("+");
        }
        sb.append(words[words.length-1]);


        return sb.toString();
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


    /**
     * Change the first string's word in Uppercase
     *
     * @param word
     * @return String
     */
    public static String firstWordUppercase(String word) {
        StringBuilder sb = new StringBuilder(word);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

        return sb.toString();

    }


    /**
     * Parse string with minutes in hour and minutes
     *
     * @param runTime
     * @return String
     */
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
}
