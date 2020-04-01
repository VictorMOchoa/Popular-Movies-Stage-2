package com.roundfifteen.popularmovies.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = "favoriteMovies")
public class Movie implements Serializable {
    private String title;
    private String posterURL;
    private String plot;
    private String rating;
    private String releaseData;
    @PrimaryKey
    @NonNull
    private String id;
    private int trailerPath;

    public Movie() {
    }

    public Movie(JSONObject json) throws JSONException {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(int trailerPath) {
        this.trailerPath = trailerPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", plot='" + plot + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseData='" + releaseData + '\'' +
                ", id='" + id + '\'' +
                ", trailerPath=" + trailerPath +
                '}';
    }
}
