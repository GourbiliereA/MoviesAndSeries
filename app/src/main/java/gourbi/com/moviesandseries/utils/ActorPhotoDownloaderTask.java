package gourbi.com.moviesandseries.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

import gourbi.com.moviesandseries.adapter.ActorAdapter;
import gourbi.com.moviesandseries.adapter.MovieAdapter;
import gourbi.com.moviesandseries.model.Actor;
import gourbi.com.moviesandseries.model.Movie;

/**
 * Created by Alex GOURBILIERE on 05/01/2018.
 */

public class ActorPhotoDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    Actor actor;

    public ActorPhotoDownloaderTask(Actor actor) {
        this.actor = actor;
    }

    protected Bitmap doInBackground(String... urls) {
        InputStream is = null;
        Bitmap bm = null;
        try {
            is = (InputStream) new URL(actor.getPhoto()).getContent();
            bm = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bm;
    }

    protected void onPostExecute(Bitmap result) {
        ActorAdapter.actorsPhoto.put(actor, result);
    }
}
