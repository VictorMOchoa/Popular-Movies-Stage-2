package com.roundfifteen.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roundfifteen.popularmovies.dto.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<Movie>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM favoriteMovies WHERE id = :id")
    void deleteMovie(int id);

    @Query("SELECT * FROM favoriteMovies WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);
}