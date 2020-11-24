package ru.study.photogalleryv2;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class GalleryItem {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String caption;
    @SerializedName("url_s")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return caption;
    }
}