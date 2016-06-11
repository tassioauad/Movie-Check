package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.MovieWatched;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MovieWatchedListAdapter extends RecyclerView.Adapter<MovieWatchedListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<MovieWatched> movieWatchedList;
    private OnItemClickListener<MovieWatched> movieInterestOnItemClickListener;

    public MovieWatchedListAdapter(List<MovieWatched> movieWatchedList, OnItemClickListener<MovieWatched> movieInterestOnItemClickListener) {
        this.movieWatchedList = movieWatchedList;
        this.movieInterestOnItemClickListener = movieInterestOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_moviewatched, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieWatched movieWatched = movieWatchedList.get(position);
        holder.itemView.setTag(movieWatched);
        holder.textViewMovieName.setText(movieWatched.getMovie().getTitle());
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + movieWatched.getMovie().getPosterUrl();
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewMoviePoster);
        holder.ratingBarVote.setRating(movieWatched.getVote());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(holder.itemView.getContext().getString(R.string.general_date), Locale.getDefault());
        holder.textViewMovieReleaseDate.setText(simpleDateFormat.format(movieWatched.getMovie().getReleaseDate()));
}

    @Override
    public int getItemCount() {
        return movieWatchedList.size();
    }

    @Override
    public void onClick(View view) {
        MovieWatched movieWatched = (MovieWatched) view.getTag();
        movieInterestOnItemClickListener.onClick(movieWatched, view);
    }

    public void remove(int position) {
        movieWatchedList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public boolean onLongClick(View view) {
        MovieWatched movieWatched = (MovieWatched) view.getTag();
        movieInterestOnItemClickListener.onLongClick(movieWatched, view);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewMoviePoster;
        private TextView textViewMovieName;
        private TextView textViewMovieReleaseDate;
        private RatingBar ratingBarVote;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = (ImageView) itemView.findViewById(R.id.imageview_movieposter);
            textViewMovieName = (TextView) itemView.findViewById(R.id.textview_moviename);
            textViewMovieReleaseDate = (TextView) itemView.findViewById(R.id.textview_moviereleasedate);
            ratingBarVote = (RatingBar) itemView.findViewById(R.id.ratingbar_userclassification);
        }
    }

}