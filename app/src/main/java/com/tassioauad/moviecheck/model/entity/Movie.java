package com.tassioauad.moviecheck.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Movie {

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
    private Long[] genreId;

    @SerializedName("original_language")
    private String language;

    public Movie() {
    }

    public Movie(Long id, String title, Date releaseDate, String backdropUrl, String posterUrl,
                 String overview, boolean adult, float voteAverage, long voteCount, Long[] genreId,
                 String language) {
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

    public Long[] getGenreId() {
        return genreId;
    }

    public void setGenreId(Long[] genreId) {
        this.genreId = genreId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
