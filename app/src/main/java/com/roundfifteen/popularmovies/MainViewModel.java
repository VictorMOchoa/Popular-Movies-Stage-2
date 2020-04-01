package com.roundfifteen.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.roundfifteen.popularmovies.database.MovieDatabase;
import com.roundfifteen.popularmovies.dto.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super (application);
        MovieDatabase database = MovieDatabase.getInstance (this.getApplication ());
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movies;
    }
}
