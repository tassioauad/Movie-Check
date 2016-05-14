package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Review;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Review> reviewList;
    private OnItemClickListener<Review> reviewOnItemClickListener;

    public ReviewListAdapter(List<Review> reviewList, OnItemClickListener<Review> reviewOnItemClickListener) {
        this.reviewList = reviewList;
        this.reviewOnItemClickListener = reviewOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_review, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.itemView.setTag(review);
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    @Override
    public void onClick(View view) {
        Review review = (Review) view.getTag();
        reviewOnItemClickListener.onClick(review, view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAuthor = (TextView) itemView.findViewById(R.id.textview_author);
            textViewContent = (TextView) itemView.findViewById(R.id.textview_content);
        }
    }

}
