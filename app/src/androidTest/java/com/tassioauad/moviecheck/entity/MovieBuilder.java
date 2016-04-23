package com.tassioauad.moviecheck.entity;

import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.util.DateParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MovieBuilder {

    private static final Long ID_VALID = 271110l;
    private static final String TITLE_VALID = "Captain America: Civil War";
    private static final String POSTERURL_VALID = "/5N20rQURev5CNDcMjHVUZhpoCNC.jpg";
    private static final String OVERVIEW_VALID = "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.";
    private static final String RELEASEDATE_VALID = "2016-04-27";
    private static final List<Long> GENREID_VALID = new ArrayList<>(Arrays.asList(28l, 878l, 53l));
    private static final String LANGUAGE_VALID = "en";
    private static final String BACKDROPURL_VALID = "/rqAHkvXldb9tHlnbQDwOzRi0yVD.jpg";
    private static final Double POPULARITY_VALID = 18.102569d;
    private static final Long VOTECOUNT_VALID = 75l;
    private static final Float VOTEAVERAGE_VALID = 5.77f;

    private Long id;
    private String title;
    private Date releaseDate;
    private String backdropUrl;
    private String posterUrl;
    private String overview;
    private String language;
    private boolean adult;
    private double popularity;
    private float voteAverage;
    private long voteCount;
    private List<Long> genreIdList = new ArrayList<>();

    public static MovieBuilder aMovie() throws ParseException {
        return new MovieBuilder().withIdValid().withTitleValid(). withReleaseDateValid().withBackdropUrlValid()
                .withPosterUrlValid().withOverviewValid().withLanguageValid().adult().withVoteAverageValid()
                .withVoteCountValid().withGenreIdValid();
    }

    public MovieBuilder withIdValid() {
        id = ID_VALID;
        return this;
    }

    public MovieBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MovieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder withTitleValid() {
        title = TITLE_VALID;
        return this;
    }

    public MovieBuilder withReleaseDate(String releaseDate) throws ParseException {
        this.releaseDate = DateParser.toDate(releaseDate);
        return this;
    }

    public MovieBuilder withReleaseDateValid() throws ParseException {
        releaseDate = DateParser.toDate(RELEASEDATE_VALID);
        return this;
    }

    public MovieBuilder withBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
        return this;
    }

    public MovieBuilder withBackdropUrlValid() {
        backdropUrl = BACKDROPURL_VALID;
        return this;
    }

    public MovieBuilder withPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
        return this;
    }

    public MovieBuilder withPosterUrlValid() {
        posterUrl = POSTERURL_VALID;
        return this;
    }

    public MovieBuilder withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public MovieBuilder withOverviewValid() {
        overview = OVERVIEW_VALID;
        return this;
    }

    public MovieBuilder withAdult(boolean adult) {
        this.adult = adult;
        return this;
    }

    public MovieBuilder adult() {
        adult = true;
        return this;
    }

    public MovieBuilder notAdult() {
        adult = false;
        return this;
    }

    public MovieBuilder withVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public MovieBuilder withVoteAverageValid() {
        voteAverage = VOTEAVERAGE_VALID;
        return this;
    }

    public MovieBuilder withVoteCount(long voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public MovieBuilder withVoteCountValid() {
        voteCount = VOTECOUNT_VALID;
        return this;
    }

    public MovieBuilder withGenreIdList(List<Long> genreIdList) {
        this.genreIdList = genreIdList;
        return this;
    }

    public MovieBuilder withGenreIdValid() {
        genreIdList = GENREID_VALID;
        return this;
    }

    public MovieBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public MovieBuilder withLanguageValid() {
        language = LANGUAGE_VALID;
        return this;
    }

    public MovieBuilder withPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public MovieBuilder withPopularityValid() {
        popularity = POPULARITY_VALID;
        return this;
    }

    public Movie build() {
        return new Movie(id, title, releaseDate, backdropUrl, posterUrl, overview, adult,
                voteAverage, voteCount, genreIdList, language, popularity);
    }

}
