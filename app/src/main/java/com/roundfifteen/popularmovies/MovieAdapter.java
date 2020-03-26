package com.roundfifteen.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.roundfifteen.popularmovies.dto.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    public List<Movie> movies;
    private MoviesAdapterOnClickListener listener;
    private Context context;

    public MovieAdapter(Context context, MoviesAdapterOnClickListener listener, List<Movie> movies) {
        this.context = context;
        this.listener = listener;
        this.movies = movies;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public final ImageView moviePosterView;

        public MovieViewHolder(View view) {
            super(view);

            moviePosterView = (ImageView) view.findViewById(R.id.movie_poster_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie selectedMovie = movies.get(getAdapterPosition());
            listener.onClick(selectedMovie);
        }
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String posterURL = movies.get(position).getPosterURL();
        Picasso.get().load(posterURL).into(holder.moviePosterView);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface MoviesAdapterOnClickListener {
        void onClick(Movie movie);
    }

}
