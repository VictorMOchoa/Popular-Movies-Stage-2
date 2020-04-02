package com.roundfifteen.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder> {

    public List<String> reviews;
    private Context context;

    public MovieReviewAdapter(Context context, List<String> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public final TextView reviewTextView;

        public ReviewViewHolder(View view) {
            super(view);
            reviewTextView = view.findViewById(R.id.tv_user_review);
        }

    }

    @Override
    public MovieReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapter.ReviewViewHolder holder, int position) {
        String review = reviews.get(position);
        holder.reviewTextView.setText(review);
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
