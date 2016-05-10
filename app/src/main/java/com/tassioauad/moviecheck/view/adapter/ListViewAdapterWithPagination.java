package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tassioauad.moviecheck.R;

public class ListViewAdapterWithPagination extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private RecyclerView.Adapter adapter;
    private OnShowMoreListener onShowMoreListener;
    private int itensPerPage;
    private boolean withShowMoreButton = true;

    public ListViewAdapterWithPagination(RecyclerView.Adapter adapter, OnShowMoreListener onShowMoreListener, int itensPerPage) {
        this.adapter = adapter;
        this.onShowMoreListener = onShowMoreListener;
        this.itensPerPage = itensPerPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            return adapter.onCreateViewHolder(parent, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_showmore, parent, false);
            view.setOnClickListener(this);
            return new ViewHolderShowMore(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < adapter.getItemCount()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!(holder instanceof ViewHolderShowMore)) {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if(withShowMoreButton && adapter.getItemCount()> 0 && adapter.getItemCount() % itensPerPage == 0) {
            return adapter.getItemCount() + 1;
        } else {
            return adapter.getItemCount();
        }
    }

    public void withShowMoreButton(boolean withShowMoreButton) {
        this.withShowMoreButton = withShowMoreButton;
    }

    @Override
    public void onClick(View view) {
        onShowMoreListener.showMore();
    }


    public class ViewHolderShowMore extends RecyclerView.ViewHolder {

        public ViewHolderShowMore(View itemView) {
            super(itemView);
        }
    }

}
