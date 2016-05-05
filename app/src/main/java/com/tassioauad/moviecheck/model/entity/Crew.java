package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Crew implements Person {

    private Long id;

    private String name;

    private String job;

    private String biography;

    @SerializedName("profile_path")
    private String profilePath;

    private String department;

    private String homepage;

    @SerializedName("place_of_birth")
    private String placeOfBith;

    private Date birthday;

    private Date deathday;

    public Crew(Long id, String name, String job, String biography, String profilePath,
                String department, String homepage, String placeOfBith, Date birthday, Date deathday) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.biography = biography;
        this.profilePath = profilePath;
        this.department = department;
        this.homepage = homepage;
        this.placeOfBith = placeOfBith;
        this.birthday = birthday;
        this.deathday = deathday;
    }

    public Crew() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String getPlaceOfBith() {
        return placeOfBith;
    }

    public void setPlaceOfBith(String placeOfBith) {
        this.placeOfBith = placeOfBith;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
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

        Crew crew = (Crew) o;

        return id.equals(crew.id);

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
        dest.writeString(this.job);
        dest.writeString(this.biography);
        dest.writeString(this.profilePath);
        dest.writeString(this.department);
        dest.writeString(this.homepage);
        dest.writeString(this.placeOfBith);
        dest.writeLong(birthday != null ? birthday.getTime() : -1);
        dest.writeLong(deathday != null ? deathday.getTime() : -1);
    }

    protected Crew(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.job = in.readString();
        this.biography = in.readString();
        this.profilePath = in.readString();
        this.department = in.readString();
        this.homepage = in.readString();
        this.placeOfBith = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        long tmpDeathday = in.readLong();
        this.deathday = tmpDeathday == -1 ? null : new Date(tmpDeathday);
    }

    public static final Parcelable.Creator<Crew> CREATOR = new Parcelable.Creator<Crew>() {
        public Crew createFromParcel(Parcel source) {
            return new Crew(source);
        }

        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };
}
