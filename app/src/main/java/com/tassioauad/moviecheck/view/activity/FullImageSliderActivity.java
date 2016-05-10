package com.tassioauad.moviecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.model.entity.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullImageSliderActivity extends AppCompatActivity {

    private static final String KEY_IMAGELIST = "IMAGELIST";
    private static final String KEY_POSITION = "POSITION";
    private List<Image> imageList;
    private int position;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimageslider);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageList = getIntent().getParcelableArrayListExtra(KEY_IMAGELIST);
        position = getIntent().getIntExtra(KEY_POSITION, 0);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                String imageUrl = getString(R.string.imagetmdb_baseurl) + imageList.get(position).getFilePath();
                Picasso.with(FullImageSliderActivity.this).load(imageUrl).placeholder(R.drawable.noimage).into(imageView);

                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, ArrayList<Image> imageList, int position) {
        Intent intent = new Intent(context, FullImageSliderActivity.class);
        intent.putParcelableArrayListExtra(KEY_IMAGELIST, imageList);
        intent.putExtra(KEY_POSITION, position);

        return intent;
    }
}
