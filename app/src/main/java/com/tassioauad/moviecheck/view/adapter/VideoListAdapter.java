package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Video;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Video> videoList;
    private OnItemClickListener<Video> videoOnItemClickListener;

    public VideoListAdapter(List<Video> videoList, OnItemClickListener<Video> videoOnItemClickListener) {
        this.videoList = videoList;
        this.videoOnItemClickListener = videoOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_video, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = videoList.get(position);

        holder.itemView.setTag(video);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void onClick(View view) {
        Video video = (Video) view.getTag();
        videoOnItemClickListener.onClick(video);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
