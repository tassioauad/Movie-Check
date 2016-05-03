package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Cast implements Parcelable {

    private Long id;

    private String name;

    @SerializedName("place_of_birth")
    private String placeOfBith;

    private String character;

    private String homepage;

    @SerializedName("profile_path")
    private String profilePath;

    private Date birthday;

    private String biography;

    private Date deathday;

    public Cast() {
    }

    public Cast(Long id, String name, String placeOfBith, String character, String homepage,
                String profilePath, Date birthday, String biography, Date deathday) {
        this.id = id;
        this.name = name;
        this.placeOfBith = placeOfBith;
        this.character = character;
        this.homepage = homepage;
        this.profilePath = profilePath;
        this.birthday = birthday;
        this.biography = biography;
        this.deathday = deathday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOfBith() {
        return placeOfBith;
    }

    public void setPlaceOfBith(String placeOfBith) {
        this.placeOfBith = placeOfBith;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cast cast = (Cast) o;

        return id.equals(cast.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.placeOfBith);
        dest.writeString(this.character);
        dest.writeString(this.homepage);
        dest.writeString(this.profilePath);
        dest.writeLong(birthday != null ? birthday.getTime() : -1);
        dest.writeString(this.biography);
        dest.writeLong(deathday != null ? deathday.getTime() : -1);
    }

    protected Cast(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.placeOfBith = in.readString();
        this.character = in.readString();
        this.homepage = in.readString();
        this.profilePath = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        this.biography = in.readString();
        long tmpDeathday = in.readLong();
        this.deathday = tmpDeathday == -1 ? null : new Date(tmpDeathday);
    }

    public static final Parcelable.Creator<Cast> CREATOR = new Parcelable.Creator<Cast>() {
        public Cast createFromParcel(Parcel source) {
            return new Cast(source);
        }

        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}
