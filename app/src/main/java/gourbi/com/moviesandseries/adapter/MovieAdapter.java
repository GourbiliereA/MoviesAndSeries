package gourbi.com.moviesandseries.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import gourbi.com.moviesandseries.R;
import gourbi.com.moviesandseries.model.Movie;
import gourbi.com.moviesandseries.utils.DownloadImageTask;

/**
 * Created by Alex GOURBILIERE on 04/01/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Activity activity;
    private List<Movie> movies;
    public static HashMap<Movie, Bitmap> moviesPoster;
    private static LayoutInflater inflater = null;

    public MovieAdapter (Activity activity, int textViewResourceId, List<Movie> movies) {
        super(activity, textViewResourceId, movies);
        try {
            this.activity = activity;
            this.movies = movies;

            moviesPoster = new HashMap<>();
            for (Movie movie : movies) {
                new DownloadImageTask(movie).execute("");
            }

            Thread.sleep(1000);

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return movies.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView textView_title;
        public TextView textView_description;
        public ImageView imageView_poster;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;


        try {

            if (convertView == null) {
                vi = inflater.inflate(R.layout.popular_movie, null);
                holder = new ViewHolder();

                holder.textView_title = vi.findViewById(R.id.textView_popularMovieTitle);
                holder.imageView_poster = vi.findViewById(R.id.imageView_popularMoviePoster);
                holder.textView_description = vi.findViewById(R.id.textView_popularMovieDescription);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            Movie movie = movies.get(position);

            holder.textView_title.setText(movie.getTitle());
            holder.imageView_poster.setImageBitmap(moviesPoster.get(movie));
            holder.textView_description.setText(movie.getDescription());

        } catch (Exception e) {


        }
        return vi;
    }
}
