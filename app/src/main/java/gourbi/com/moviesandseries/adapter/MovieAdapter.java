package gourbi.com.moviesandseries.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static LayoutInflater inflater = null;

    public MovieAdapter (Activity activity, int textViewResourceId, List<Movie> movies) {
        super(activity, textViewResourceId, movies);
        try {
            this.activity = activity;
            this.movies = movies;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

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

                holder.textView_title = (TextView) vi.findViewById(R.id.textView_popularMovieTitle);
                holder.imageView_poster = (ImageView) vi.findViewById(R.id.imageView_popularMoviePoster);
                holder.textView_description = (TextView) vi.findViewById(R.id.textView_popularMovieDescription);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.textView_title.setText(movies.get(position).getTitle());
            new DownloadImageTask(holder.imageView_poster)
                    .execute(movies.get(position).getPosterUrl());
            holder.textView_description.setText(movies.get(position).getDescription());


        } catch (Exception e) {


        }
        return vi;
    }
}
