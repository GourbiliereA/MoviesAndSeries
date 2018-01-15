package gourbi.com.moviesandseries.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gourbi.com.moviesandseries.R;
import gourbi.com.moviesandseries.adapter.ActorAdapter;
import gourbi.com.moviesandseries.model.Actor;

/**
 * Created by Alex GOURBILIERE on 15/01/2018.
 */

public class MovieActorsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    ListView listView_actors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_actors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int movieId = this.getIntent().getIntExtra("movieId", 0);
        getMovieActors(movieId);

        listView_actors = findViewById(R.id.listView_movieActors);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMovieActors(int movieId) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final Context context = MovieActorsActivity.this;

        RequestParams params = new RequestParams();
        params.add("api_key", getString(R.string.api_key));

        asyncHttpClient.get("https://api.themoviedb.org/3/movie/" + movieId + "/credits", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    ArrayList<Actor> actors = new ArrayList<>();
                    JSONArray results = jsonResponse.getJSONArray("cast");

                    for(int i=0; i<results.length(); i++){
                        String name = results.getJSONObject(i).getString("name");
                        String role = results.getJSONObject(i).getString("character");

                        String photoPath = null;
                        if (!results.getJSONObject(i).getString("profile_path").equals("null")) {
                            photoPath = "https://image.tmdb.org/t/p/w185/" + results.getJSONObject(i).getString("profile_path");
                        }

                        Actor actor = new Actor(name, photoPath, role);
                        actors.add(actor);
                    }

                    ActorAdapter adapter = new ActorAdapter(MovieActorsActivity.this, 0, actors);
                    listView_actors.setAdapter(adapter);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure (int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(MovieActorsActivity.this, R.string.error_resourceNotFound, Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(MovieActorsActivity.this, R.string.error_serveurError, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieActorsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}