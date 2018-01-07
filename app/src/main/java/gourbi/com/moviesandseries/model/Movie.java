package gourbi.com.moviesandseries.model;

/**
 * Created by Alex GOURBILIERE on 04/01/2018.
 */

public class Movie {
    private String title;

    private String description;

    private double rating;

    private String posterUrl;

    public Movie(String title, String description, double rating, String posterUrl) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.posterUrl = "http://image.tmdb.org/t/p/w185" + posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
