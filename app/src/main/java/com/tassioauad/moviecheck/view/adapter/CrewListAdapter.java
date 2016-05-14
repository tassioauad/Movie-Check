package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Crew;

import java.util.List;

public class CrewListAdapter extends RecyclerView.Adapter<CrewListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Crew> crewList;
    private OnItemClickListener<Crew> crewOnItemClickListener;

    public CrewListAdapter(List<Crew> crewList, OnItemClickListener<Crew> crewOnItemClickListener) {
        this.crewList = crewList;
        this.crewOnItemClickListener = crewOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_crew, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Crew crew = crewList.get(position);

        holder.itemView.setTag(crew);
        holder.textViewName.setText(crew.getName());
        holder.textViewJob.setText(crew.getJob());
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + crew.getProfilePath();
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewProfile);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    @Override
    public void onClick(View view) {
        Crew crew = (Crew) view.getTag();
        crewOnItemClickListener.onClick(crew, view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewProfile;
        private TextView textViewName;
        private TextView textViewJob;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewProfile = (ImageView) itemView.findViewById(R.id.imageview_profile);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
            textViewJob = (TextView) itemView.findViewById(R.id.textview_job);
        }
    }

}
