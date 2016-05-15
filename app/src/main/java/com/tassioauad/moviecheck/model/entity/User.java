package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private Long id;

    private String googleId;

    private String name;

    private String email;

    private String photoUrl;

    public User() {
    }

    public User(String googleId, String name, String email, String photoUrl) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);

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
        dest.writeLong(this.id);
        dest.writeString(this.googleId);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.photoUrl);
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.googleId = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
