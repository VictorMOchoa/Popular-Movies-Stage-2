package com.roundfifteen.popularmovies;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.roundfifteen.popularmovies.database.MovieDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieDatabase database;
    private final int id;

    public MovieDetailsViewModelFactory(MovieDatabase database, int id) {
        this.database = database;
        this.id = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(database, id);
    }
}