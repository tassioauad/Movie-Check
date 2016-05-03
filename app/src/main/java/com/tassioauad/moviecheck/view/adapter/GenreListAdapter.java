package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Genre;

import java.util.List;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Genre> genreList;
    private OnItemClickListener<Genre> genreOnItemClickListener;

    public GenreListAdapter(List<Genre> genreList, OnItemClickListener<Genre> genreOnItemClickListener) {
        this.genreList = genreList;
        this.genreOnItemClickListener = genreOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_genre, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Genre genre = genreList.get(position);

        holder.itemView.setTag(genre);
        holder.textViewName.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    @Override
    public void onClick(View view) {
        Genre genre = (Genre) view.getTag();
        genreOnItemClickListener.onClick(genre);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
        }
    }

}
