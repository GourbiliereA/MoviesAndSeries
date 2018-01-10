package gourbi.com.moviesandseries;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import gourbi.com.moviesandseries.adapter.MovieAdapter;
import gourbi.com.moviesandseries.model.Movie;

public class PopularMoviesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int NB_MAX_MOVIES = 20;

    private ListView listViewLastMovies;
    private List<Movie> popularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listViewLastMovies = findViewById(R.id.textView_lastMovies);
        popularMovies = new ArrayList<>();

        // Retrieving popualr movies to display them
        getPopularMovies();
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

    private void getPopularMovies() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final Context context = PopularMoviesActivity.this;

        RequestParams params = new RequestParams();
        params.add("api_key", getString(R.string.api_key));

        asyncHttpClient.get("https://api.themoviedb.org/3/movie/popular", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getInt("total_results") != 0) {
                        // Some movies found
                        popularMovies = new ArrayList<>();
                        String[] moviesTitle = new String[NB_MAX_MOVIES];
                        JSONArray results = jsonResponse.getJSONArray("results");

                        for(int i=0; i<results.length(); i++){
                            String title = results.getJSONObject(i).getString("title");
                            String description = results.getJSONObject(i).getString("overview");
                            Double rating = results.getJSONObject(i).getDouble("popularity");
                            String posterPath = results.getJSONObject(i).getString("poster_path");

                            Movie movie = new Movie(title, description, rating, posterPath);
                            popularMovies.add(movie);
                            moviesTitle[i] = title;

                            if (i == NB_MAX_MOVIES - 1) {
                                break;
                            }
                        }

                        MovieAdapter adapter = new MovieAdapter(PopularMoviesActivity.this, 0, popularMovies);
                        listViewLastMovies.setAdapter(adapter);

                    } else {
                        // No movies found
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure (int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(PopularMoviesActivity.this, R.string.error_resourceNotFound, Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(PopularMoviesActivity.this, R.string.error_serveurError, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PopularMoviesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
