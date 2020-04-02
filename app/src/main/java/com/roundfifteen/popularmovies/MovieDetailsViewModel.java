package com.roundfifteen.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.roundfifteen.popularmovies.database.MovieDatabase;
import com.roundfifteen.popularmovies.dto.Movie;

public class MovieDetailsViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public MovieDetailsViewModel(MovieDatabase database, int id) {
        movie = database.movieDao().loadMovieById(id);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
