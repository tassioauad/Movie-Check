package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public class TopRatedMovieListAdapter extends RecyclerView.Adapter<TopRatedMovieListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Movie> movieList;
    private OnItemClickListener<Movie> movieOnItemClickListener;

    public TopRatedMovieListAdapter(List<Movie> movieList, OnItemClickListener<Movie> movieOnItemClickListener) {
        this.movieList = movieList;
        this.movieOnItemClickListener = movieOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_topratedmovie, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + movie.getPosterUrl();

        holder.itemView.setTag(movie);
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewPoster);
        holder.textViewRate.setText(String.valueOf(movie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onClick(View view) {
        Movie movie = (Movie) view.getTag();
        movieOnItemClickListener.onClick(movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;
        private TextView textViewRate;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageview_poster);
            textViewRate = (TextView) itemView.findViewById(R.id.textview_rate);
        }
    }

}
