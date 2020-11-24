package ru.study.photogalleryv2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GalleryItemWrapper {
    @SerializedName("photo")
    private List<GalleryItem> galleryItems;

    public List<GalleryItem> getGalleryItems() {
        return galleryItems;
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }
}