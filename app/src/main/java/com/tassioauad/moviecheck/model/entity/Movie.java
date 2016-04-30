package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie implements Parcelable {

    private Long id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("backdrop_path")
    private String backdropUrl;

    @SerializedName("poster_path")
    private String posterUrl;

    private String overview;

    private boolean adult;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("genre_ids")
    private List<Long> genreId;

    @SerializedName("original_language")
    private String language;

    private double popularity;

    public Movie() {
    }

    public Movie(Long id, String title, Date releaseDate, String backdropUrl, String posterUrl, String overview, boolean adult, float voteAverage, long voteCount, List<Long> genreId, String language, double popularity) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.backdropUrl = backdropUrl;
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.adult = adult;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.genreId = genreId;
        this.language = language;
        this.popularity = popularity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public List<Long> getGenreId() {
        return genreId;
    }

    public void setGenreId(List<Long> genreId) {
        this.genreId = genreId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1);
        dest.writeString(this.backdropUrl);
        dest.writeString(this.posterUrl);
        dest.writeString(this.overview);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeList(this.genreId);
        dest.writeString(this.language);
        dest.writeDouble(this.popularity);
    }

    protected Movie(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.backdropUrl = in.readString();
        this.posterUrl = in.readString();
        this.overview = in.readString();
        this.adult = in.readByte() != 0;
        this.voteAverage = in.readFloat();
        this.voteCount = in.readLong();
        this.genreId = new ArrayList<Long>();
        in.readList(this.genreId, List.class.getClassLoader());
        this.language = in.readString();
        this.popularity = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
