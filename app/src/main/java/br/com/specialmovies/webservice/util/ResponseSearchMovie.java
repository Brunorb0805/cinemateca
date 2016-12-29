package br.com.specialmovies.webservice.util;


import br.com.specialmovies.model.Movie;

public class ResponseSearchMovie {

    private Movie movie;
    private Boolean status;


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
