package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;

import java.util.Date;

public class Crew extends Person {

    private String job;

    private String department;

    private String homepage;

    public Crew(Long id, String name, String biography, String profilePath, String placeOfBith, Date birthday, Date deathday, String job, String department, String homepage) {
        super(id, name, biography, profilePath, placeOfBith, birthday, deathday);
        this.job = job;
        this.department = department;
        this.homepage = homepage;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.job);
        dest.writeString(this.department);
        dest.writeString(this.homepage);
    }

    protected Crew(Parcel in) {
        super(in);
        this.job = in.readString();
        this.department = in.readString();
        this.homepage = in.readString();
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        public Crew createFromParcel(Parcel source) {
            return new Crew(source);
        }

        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };
}
