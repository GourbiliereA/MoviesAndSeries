package gourbi.com.moviesandseries.adapter;

import android.app.Activity;
import android.content.Context;
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
import gourbi.com.moviesandseries.model.Actor;
import gourbi.com.moviesandseries.utils.ActorPhotoDownloaderTask;

/**
 * Created by Alex GOURBILIERE on 14/01/2018.
 */

public class ActorAdapter extends ArrayAdapter<Actor> {
    private Activity activity;
    private List<Actor> actors;
    public static HashMap<Actor, Bitmap> actorsPhoto;
    private static LayoutInflater inflater = null;

    public ActorAdapter (Activity activity, int textViewResourceId, List<Actor> movies) {
        super(activity, textViewResourceId, movies);
        try {
            this.activity = activity;
            this.actors = movies;

            actorsPhoto = new HashMap<>();
            for (Actor actor : actors) {
                if (actor.getPhoto() != null && !actor.getPhoto().isEmpty()) {
                    new ActorPhotoDownloaderTask(actor).execute("");
                }
            }

            Thread.sleep(1000);

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return actors.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView textView_name;
        public TextView textView_role;
        public ImageView imageView_photo;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        try {

            if (convertView == null) {
                vi = inflater.inflate(R.layout.acctor, null);
                holder = new ActorAdapter.ViewHolder();

                holder.textView_name = vi.findViewById(R.id.textView_actorName);
                holder.imageView_photo = vi.findViewById(R.id.imageView_actorPhoto);
                holder.textView_role = vi.findViewById(R.id.textView_actorRole);

                vi.setTag(holder);
            } else {
                holder = (ActorAdapter.ViewHolder) vi.getTag();
            }

            // Filling information about the movie
            final Actor actor = actors.get(position);
            holder.textView_name.setText(actor.getName());
            holder.imageView_photo.setImageBitmap(actorsPhoto.get(actor));
            holder.textView_role.setText("Role: " + actor.getRole());

        } catch (Exception e) {


        }
        return vi;
    }
}
