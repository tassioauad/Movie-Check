package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Cast;

import java.util.List;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Cast> castList;
    private OnItemClickListener<Cast> castOnItemClickListener;

    public CastListAdapter(List<Cast> castList, OnItemClickListener<Cast> castOnItemClickListener) {
        this.castList = castList;
        this.castOnItemClickListener = castOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_cast, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast cast = castList.get(position);

        holder.itemView.setTag(cast);
        holder.textViewName.setText(cast.getName());
        holder.textViewCharacter.setText(cast.getCharacter());
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + cast.getProfilePath();
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    @Override
    public void onClick(View view) {
        Cast cast = (Cast) view.getTag();
        castOnItemClickListener.onClick(cast);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;
        private TextView textViewName;
        private TextView textViewCharacter;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageview_poster);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
            textViewCharacter = (TextView) itemView.findViewById(R.id.textview_character);
        }
    }

}
