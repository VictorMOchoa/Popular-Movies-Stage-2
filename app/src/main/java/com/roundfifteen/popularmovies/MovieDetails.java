package com.roundfifteen.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;

import com.roundfifteen.popularmovies.dto.Movie;
import com.roundfifteen.popularmovies.util.ResponseUtil;
import com.squareup.picasso.Picasso;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.roundfifteen.popularmovies.util.Constants.MOVIE;

public class MovieDetails extends AppCompatActivity {
 public String apiKey = BuildConfig.API_KEY;
 public LinearLayout trailerLayout;
 public Button trailerBtn;
 public TextView reviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        Movie receivedMovie = (Movie) intent.getSerializableExtra(MOVIE);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle(receivedMovie.getTitle());
        }

        TextView moviePlot = (TextView) findViewById(R.id.tv_plot);
        TextView releaseDetails = (TextView) findViewById(R.id.tv_release);
        TextView rating = (TextView) findViewById(R.id.tv_rating);
        ImageView moviePoster = (ImageView) findViewById(R.id.iv_poster);
        trailerBtn = findViewById(R.id.btn_trailer);
        trailerLayout = findViewById(R.id.trailer_layout);
        reviewTv = findViewById(R.id.tv_review);

        moviePlot.setText(receivedMovie.getPlot());
        releaseDetails.setText(receivedMovie.getReleaseData());
        rating.setText(receivedMovie.getRating());
        Picasso.get().load(receivedMovie.getPosterURL()).into(moviePoster);


        new MovieTrailerQueryTask(trailerBtn).execute(receivedMovie.getId());
        new MovieReviewsQueryTask().execute(receivedMovie.getId());
    }

    private class MovieTrailerQueryTask extends AsyncTask<String, Void, String> {
        public Button playTrailerBtn;

        public MovieTrailerQueryTask(Button playTrailerBtn) {
            this.playTrailerBtn = playTrailerBtn;
        }

        @Override
        protected String doInBackground(String... movieIds) {
            String trailerKey = null;
            String apiURL = "https://api.themoviedb.org/3/movie/" + movieIds[0] + "/videos?page=1&language=en-US&api_key=" + apiKey;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                try {
                    trailerKey = ResponseUtil.extractTrailerKeyFromResponse(response.body().string());
                    System.out.println(trailerKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println(e);
            }
            return trailerKey;
        }

        protected void onPostExecute(final String trailerKey) {
            try {
                // If null, hide the trailer section altogether
                if (trailerKey == null) {
                    trailerLayout.setVisibility(View.GONE);
                } else {
                    trailerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerKey)));
                        }
                    });
                }
            } catch (Error e) {
                System.out.println(e);
            }
        }
    }

    private class MovieReviewsQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... movieIds) {

            String apiURL = "https://api.themoviedb.org/3/movie/" + movieIds[0] + "/reviews?page=1&language=en-US&api_key=" + apiKey;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                try {

                    String reviews = ResponseUtil.extractReviewsFromResponse(response.body().string());
                    return reviews;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println(e);
            }
            return "";
        }

        protected void onPostExecute(String review) {
            try {
                reviewTv.setText(review);
            } catch (Error e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
