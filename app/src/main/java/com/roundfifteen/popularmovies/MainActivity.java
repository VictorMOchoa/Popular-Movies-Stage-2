package com.roundfifteen.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.roundfifteen.popularmovies.dto.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.roundfifteen.popularmovies.util.Constants.*;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MoviesAdapterOnClickListener {
    public MovieAdapter adapter;
    public String apiKey = BuildConfig.API_KEY;
    public String apiURL = "https://api.themoviedb.org/3/movie/popular?page=1&language=en-US&api_key=" + apiKey;
    public RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        int numOfColumns;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numOfColumns = 4;
        } else {
            numOfColumns = 2;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, numOfColumns);

        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(layoutManager);

        // Initial call to loadMovies will sort by popularity
        loadMovies();
    }

    private void loadMovies() {
        if (apiURL == null) {
           MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
           mainViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
               @Override
               public void onChanged(List<Movie> movies) {
                   for (int i = 0; i < movies.size(); i++) {
                       System.out.println(movies.get(i).getTitle());
                   }
                   adapter = new MovieAdapter(MainActivity.this, MainActivity.this, movies);
                   moviesRecyclerView.setAdapter(adapter);
               }
           });
        } else {
            new MovieDBQueryTask().execute(apiURL);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent movieSummaryIntent = new Intent(this, MovieDetails.class);
        movieSummaryIntent.putExtra(MOVIE, movie);
        startActivity(movieSummaryIntent);
    }

    public class MovieDBQueryTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return createMovieDataList(response.body().string());

            } catch (JSONException | IOException e) {
                System.out.println(e);
            }
            return null;
        }

        protected void onPostExecute(List<Movie> param) {
            try {
                adapter = new MovieAdapter(MainActivity.this, MainActivity.this, param);
                moviesRecyclerView.setAdapter(adapter);
            } catch (Error e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_popular:
                apiURL = "https://api.themoviedb.org/3/movie/popular?page=1&language=en-US&api_key=" + apiKey;
                break;
            case R.id.item_rating:
                apiURL = "https://api.themoviedb.org/3/movie/top_rated?page=1&language=en-US&api_key=" + apiKey;
                break;
            case R.id.item_favorites:
                apiURL = null;
                break;
        }
        loadMovies();
        return true;
    }

    public List<Movie> createMovieDataList(String jsonResponse) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        JSONObject jsonResToObj = new JSONObject(jsonResponse);
        JSONArray resultArray = new JSONArray(jsonResToObj.getString(RESULTS));
        String baseURL = "http://image.tmdb.org/t/p/w342";
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject ob = new JSONObject(resultArray.getString(i));
            Movie movie = new Movie(ob);
            movie.setTitle(ob.getString(TITLE));
            movie.setPlot(ob.getString(OVERVIEW));
            movie.setPosterURL(baseURL + ob.getString(POSTER_PATH));
            movie.setRating(ob.getString(VOTE_AVERAGE));
            movie.setReleaseData(ob.getString(RELEASE_DATE));
            movie.setId(ob.getInt(ID));
            movies.add(movie);
        }
        return movies;
    }

}
