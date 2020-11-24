package ru.study.photogalleryv2;

import com.google.gson.annotations.SerializedName;

public class JSONRootWrapper {
    @SerializedName("photos")
    private GalleryItemWrapper galleryItemWrapper;

    public GalleryItemWrapper getGalleryItemWrapper() {
        return galleryItemWrapper;
    }

    public void setGalleryItemWrapper(GalleryItemWrapper galleryItemWrapper) {
        this.galleryItemWrapper = galleryItemWrapper;
    }
}