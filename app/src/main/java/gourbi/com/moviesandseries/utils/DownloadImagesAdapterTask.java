package gourbi.com.moviesandseries.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import gourbi.com.moviesandseries.adapter.MovieAdapter;
import gourbi.com.moviesandseries.model.Movie;

/**
 * Created by Alex GOURBILIERE on 05/01/2018.
 */

public class DownloadImagesAdapterTask extends AsyncTask<String, Void, Bitmap> {
    Movie movie;

    public DownloadImagesAdapterTask(Movie movie) {
        this.movie = movie;
    }

    protected Bitmap doInBackground(String... urls) {
        InputStream is = null;
        Bitmap bm = null;
        try {
            is = (InputStream) new URL(movie.getPosterUrl()).getContent();
            bm = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bm;
    }

    protected void onPostExecute(Bitmap result) {
        MovieAdapter.moviesPoster.put(movie, result);
    }
}
