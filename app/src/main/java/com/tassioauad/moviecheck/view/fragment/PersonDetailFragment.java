package com.tassioauad.moviecheck.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.PersonDetailViewModule;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.presenter.PersonDetailPresenter;
import com.tassioauad.moviecheck.view.PersonDetailView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonDetailFragment extends Fragment implements PersonDetailView {

    @Inject
    PersonDetailPresenter presenter;
    private static final String KEY_PERSON = "PERSON";
    private Person person;

    @Bind(R.id.textview_birthday)
    TextView textViewBirthday;
    @Bind(R.id.textview_deathday)
    TextView textViewDeathday;
    @Bind(R.id.imageview_poster)
    ImageView imageViewPhoto;
    @Bind(R.id.textview_placebirthday)
    TextView textViewPlaceOfBirth;
    @Bind(R.id.textview_biography)
    TextView textViewBiography;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.linearlayout_deathday)
    LinearLayout linearLayoutDeathday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieCheckApplication) getActivity().getApplication())
                .getObjectGraph().plus(new PersonDetailViewModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persondetail, container, false);
        ButterKnife.bind(this, view);

        if(savedInstanceState == null) {
            presenter.loadPerson(((Person) getArguments().getParcelable(KEY_PERSON)).getId());
            showPerson((Person) getArguments().getParcelable(KEY_PERSON));
        } else {
            showPerson((Person) savedInstanceState.getParcelable(KEY_PERSON));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_PERSON, person);
    }

    public static PersonDetailFragment newInstance(Person person) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PERSON, person);
        PersonDetailFragment fragment = new PersonDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showBiography(String biography) {
        textViewBiography.setText(biography);
    }

    @Override
    public void showPlaceOfBirth(String placeOfBirth) {
        textViewPlaceOfBirth.setText(placeOfBirth);
    }

    @Override
    public void showBirthday(Date birthdayDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.general_date), Locale.getDefault());
        textViewBirthday.setText(simpleDateFormat.format(birthdayDate));
    }

    @Override
    public void showDeathday(Date deathday) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.general_date), Locale.getDefault());
        textViewDeathday.setText(simpleDateFormat.format(deathday));
    }

    @Override
    public void showPhoto(String photoUrl) {
        photoUrl = getString(R.string.imagetmdb_baseurl) + photoUrl;
        Picasso.with(getActivity()).load(photoUrl).into(imageViewPhoto);
        final String finalPhotoUrl = photoUrl;
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImageDialogFragment.newInstance(finalPhotoUrl).show(getActivity().getSupportFragmentManager(), "fullimage");
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void warnPersonNotFound() {
        Toast.makeText(getActivity(), R.string.persondetailfragment_notfound, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void warnFailedToLoadPerson() {
        Toast.makeText(getActivity(), R.string.persondetailfragment_failedtoload, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPerson(Person person) {
        this.person = person;
        showPhoto(person.getProfilePath());
        if (person.getDeathday() != null) {
            showDeathday(person.getDeathday());
        } else {
            linearLayoutDeathday.setVisibility(View.GONE);
        }
        if (person.getBirthday() != null) {
            showBirthday(person.getBirthday());
        }
        showBiography(person.getBiography());
        showPlaceOfBirth(person.getPlaceOfBirth());
    }

}
