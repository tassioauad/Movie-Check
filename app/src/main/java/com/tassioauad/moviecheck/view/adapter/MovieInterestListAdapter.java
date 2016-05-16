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
import com.tassioauad.moviecheck.model.entity.MovieInterest;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MovieInterestListAdapter extends RecyclerView.Adapter<MovieInterestListAdapter.ViewHolder> implements View.OnClickListener {

    private List<MovieInterest> movieInterestList;
    private OnItemClickListener<MovieInterest> movieInterestOnItemClickListener;

    public MovieInterestListAdapter(List<MovieInterest> movieInterestList, OnItemClickListener<MovieInterest> movieInterestOnItemClickListener) {
        this.movieInterestList = movieInterestList;
        this.movieInterestOnItemClickListener = movieInterestOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_movieinterest, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieInterest movieInterest = movieInterestList.get(position);
        holder.itemView.setTag(movieInterest);
        holder.textViewMovieName.setText(movieInterest.getMovie().getTitle());
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + movieInterest.getMovie().getPosterUrl();
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewMoviePoster);
        holder.ratingBarVote.setRating(movieInterest.getMovie().getVoteAverage() / 2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(holder.itemView.getContext().getString(R.string.general_date), Locale.getDefault());
        holder.textViewMovieReleaseDate.setText(simpleDateFormat.format(movieInterest.getMovie().getReleaseDate()));
        holder.textViewMovieVoteCount.setText(String.format(holder.itemView.getContext().getString(R.string.moviedetailfragment_votecount),
                movieInterest.getMovie().getVoteCount()));
    }

    @Override
    public int getItemCount() {
        return movieInterestList.size();
    }

    @Override
    public void onClick(View view) {
        MovieInterest movieInterest = (MovieInterest) view.getTag();
        movieInterestOnItemClickListener.onClick(movieInterest, view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewMoviePoster;
        private TextView textViewMovieName;
        private TextView textViewMovieReleaseDate;
        private TextView textViewMovieVoteCount;
        private RatingBar ratingBarVote;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = (ImageView) itemView.findViewById(R.id.imageview_movieposter);
            textViewMovieName = (TextView) itemView.findViewById(R.id.textview_moviename);
            textViewMovieReleaseDate = (TextView) itemView.findViewById(R.id.textview_moviereleasedate);
            textViewMovieVoteCount = (TextView) itemView.findViewById(R.id.textview_movievotecount);
            ratingBarVote = (RatingBar) itemView.findViewById(R.id.ratingbar_vote);
        }
    }

}