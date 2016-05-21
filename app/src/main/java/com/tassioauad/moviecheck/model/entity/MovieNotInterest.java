package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieNotInterest implements Parcelable {

    private Long id;
    private Movie movie;
    private User user;

    public MovieNotInterest() {
    }

    public MovieNotInterest(Movie movie, User user) {
        this.movie = movie;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieNotInterest movieInterest = (MovieNotInterest) o;

        return !(id != null ? !id.equals(movieInterest.id) : movieInterest.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.movie, 0);
        dest.writeParcelable(this.user, 0);
    }

    protected MovieNotInterest(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.movie = in.readParcelable(Movie.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<MovieNotInterest> CREATOR = new Creator<MovieNotInterest>() {
        public MovieNotInterest createFromParcel(Parcel source) {
            return new MovieNotInterest(source);
        }

        public MovieNotInterest[] newArray(int size) {
            return new MovieNotInterest[size];
        }
    };
}
