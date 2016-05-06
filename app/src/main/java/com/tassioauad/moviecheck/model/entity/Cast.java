package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;

public class Cast extends Person {

    private String character;

    private String homepage;

    public Cast() {
    }

    public Cast(String character, String homepage) {
        this.character = character;
        this.homepage = homepage;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.character);
        dest.writeString(this.homepage);
    }

    protected Cast(Parcel in) {
        super(in);
        this.character = in.readString();
        this.homepage = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        public Cast createFromParcel(Parcel source) {
            return new Cast(source);
        }

        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };
}
