package com.tassioauad.moviecheck.model.entity;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Image implements Media {

    @SerializedName("file_path")
    private String filePath;

    private Integer width;

    private Integer height;

    public Image() {
    }

    public Image(String filePath, Integer width, Integer height) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return filePath.equals(image.filePath);

    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filePath);
        dest.writeValue(this.width);
        dest.writeValue(this.height);
    }

    protected Image(Parcel in) {
        this.filePath = in.readString();
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
