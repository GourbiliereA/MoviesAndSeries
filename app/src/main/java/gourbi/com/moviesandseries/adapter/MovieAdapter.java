package gourbi.com.moviesandseries.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import gourbi.com.moviesandseries.R;
import gourbi.com.moviesandseries.activity.MovieDetailsActivity;
import gourbi.com.moviesandseries.model.Movie;
import gourbi.com.moviesandseries.utils.MoviePosterDownloaderTask;

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
                new MoviePosterDownloaderTask(movie).execute("");
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
        public TextView textView_overview;
        public ImageView imageView_poster;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;


        try {

            if (convertView == null) {
                vi = inflater.inflate(R.layout.popular_movie, null);
                holder = new ViewHolder();

                holder.textView_title = vi.findViewById(R.id.textView_popularMovieTitle);
                holder.imageView_poster = vi.findViewById(R.id.imageView_popularMoviePoster);
                holder.textView_overview = vi.findViewById(R.id.textView_popularMovieOverview);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            // Filling information about the movie
            final Movie movie = movies.get(position);
            holder.textView_title.setText(movie.getTitle());
            holder.imageView_poster.setImageBitmap(moviesPoster.get(movie));
            holder.textView_overview.setText(movie.getOverview());

            // Click listener on all view (item)
            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent movieDetailsIntent = new Intent(activity, MovieDetailsActivity.class);
                    movieDetailsIntent.putExtra("movieId", movie.getId());
                    activity.startActivity(movieDetailsIntent);

                }
            });

        } catch (Exception e) {


        }
        return vi;
    }
}
