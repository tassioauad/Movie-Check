package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Movie> movieList;
    private OnItemClickListener<Movie> movieOnItemClickListener;
    private OnShowMoreListener onShowMoreListener;

    public MovieListAdapter(List<Movie> movieList, OnItemClickListener<Movie> movieOnItemClickListener, OnShowMoreListener onShowMoreListener) {
        this.movieList = movieList;
        this.movieOnItemClickListener = movieOnItemClickListener;
        this.onShowMoreListener = onShowMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_movie, parent, false);
            view.setOnClickListener(this);
            return new ViewHolderMovie(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_showmore, parent, false);
            view.setOnClickListener(this);
            return new ViewHolderShowMore(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < movieList.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderMovie) {
            Movie movie = movieList.get(position);
            holder.itemView.setTag(movie);
            String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + movie.getPosterUrl();
            Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(((ViewHolderMovie)holder).imageViewPoster);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size() + 1;
    }

    @Override
    public void onClick(View view) {
        Movie movie = (Movie) view.getTag();
        if(movie == null) {
            onShowMoreListener.showMore();
        } else {
            movieOnItemClickListener.onClick(movie);
        }
    }

    public class ViewHolderMovie extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;

        public ViewHolderMovie(View itemView) {
            super(itemView);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageview_poster);
        }
    }

    public class ViewHolderShowMore extends RecyclerView.ViewHolder {

        public ViewHolderShowMore(View itemView) {
            super(itemView);
        }
    }

}
