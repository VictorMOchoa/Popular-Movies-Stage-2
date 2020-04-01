package com.roundfifteen.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.roundfifteen.popularmovies.dto.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoriteMovies";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}