package com.tassioauad.moviecheck.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Person;

import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Person> personList;
    private OnItemClickListener<Person> personOnItemClickListener;

    public PersonListAdapter(List<Person> personList, OnItemClickListener<Person> personOnItemClickListener) {
        this.personList = personList;
        this.personOnItemClickListener = personOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitem_person, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Person person = personList.get(position);

        holder.itemView.setTag(person);
        holder.textViewName.setText(person.getName());
        String posterUrl = holder.itemView.getContext().getString(R.string.imagetmdb_baseurl) + person.getProfilePath();
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.with(holder.itemView.getContext()).load(posterUrl).placeholder(R.drawable.noimage).into(holder.imageViewPoster, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progressBar.setVisibility(View.GONE);
                Picasso.with(holder.itemView.getContext()).load(R.drawable.noimage).into(holder.imageViewPoster);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    @Override
    public void onClick(View view) {
        Person person = (Person) view.getTag();
        personOnItemClickListener.onClick(person, view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;
        private TextView textViewName;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageview_photo);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }
    }

}
