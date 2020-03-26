package com.roundfifteen.popularmovies;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.roundfifteen.popularmovies.dto.Movie;
import com.squareup.picasso.Picasso;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.roundfifteen.popularmovies.util.Constants.MOVIE;

public class MovieDetails extends AppCompatActivity {

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

        moviePlot.setText(receivedMovie.getPlot());
        releaseDetails.setText(receivedMovie.getReleaseData());
        rating.setText(receivedMovie.getRating());
        Picasso.get().load(receivedMovie.getPosterURL()).into(moviePoster);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
