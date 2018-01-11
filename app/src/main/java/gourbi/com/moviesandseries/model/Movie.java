package gourbi.com.moviesandseries.model;

/**
 * Created by Alex GOURBILIERE on 04/01/2018.
 */

public class Movie {
    private int id;

    private String title;

    private String overview;

    private double rating;

    private String posterUrl;

    public Movie(int id, String title, String overview, double rating, String posterUrl) {
        this.id = id;
        this.title = title;
        this.overview = "      " + overview;
        this.rating = rating;
        this.posterUrl = "http://image.tmdb.org/t/p/w185" + posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
