package com.tassioauad.moviecheck.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Person implements Parcelable {

    private Long id;

    private String name;

    private String biography;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    private Date birthday;

    private Date deathday;

    public Person() {
    }

    public Person(Long id, String name, String biography, String profilePath, String placeOfBirth, Date birthday, Date deathday) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.profilePath = profilePath;
        this.placeOfBirth = placeOfBirth;
        this.birthday = birthday;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.biography);
        dest.writeString(this.profilePath);
        dest.writeString(this.placeOfBirth);
        dest.writeLong(birthday != null ? birthday.getTime() : -1);
        dest.writeLong(deathday != null ? deathday.getTime() : -1);
    }

    protected Person(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.biography = in.readString();
        this.profilePath = in.readString();
        this.placeOfBirth = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        long tmpDeathday = in.readLong();
        this.deathday = tmpDeathday == -1 ? null : new Date(tmpDeathday);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
