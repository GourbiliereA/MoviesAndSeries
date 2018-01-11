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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import gourbi.com.moviesandseries.R;
import gourbi.com.moviesandseries.utils.DownloadImageTask;

/**
 * Created by Alex GOURBILIERE on 10/01/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
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
        getMovieDetails(movieId);
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

    private void getMovieDetails(int movieId) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final Context context = MovieDetailsActivity.this;

        RequestParams params = new RequestParams();
        params.add("api_key", getString(R.string.api_key));

        asyncHttpClient.get("https://api.themoviedb.org/3/movie/" + movieId, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    TextView textViewMovieTitle = findViewById(R.id.textView_movieDetailsTitle);
                    textViewMovieTitle.setText(jsonResponse.getString("title"));

                    ImageView imageViewMoviePoster = findViewById(R.id.imageView_movieDetailsPoster);
                    new DownloadImageTask(imageViewMoviePoster).execute("http://image.tmdb.org/t/p/w500" + jsonResponse.getString("poster_path"));

                    TextView textViewMovieOverview = findViewById(R.id.textView_movieDetailsOverview);
                    textViewMovieOverview.setText("      " + jsonResponse.getString("overview"));

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure (int statusCode, Throwable error, String content) {
                if (statusCode == 404) {
                    Toast.makeText(MovieDetailsActivity.this, R.string.error_resourceNotFound, Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(MovieDetailsActivity.this, R.string.error_serveurError, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MovieDetailsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
