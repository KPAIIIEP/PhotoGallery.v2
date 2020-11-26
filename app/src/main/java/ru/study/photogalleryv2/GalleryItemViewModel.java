package ru.study.photogalleryv2;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class GalleryItemViewModel extends BaseObservable {
    GalleryItem galleryItem;

    public GalleryItem getGalleryItem() {
        return galleryItem;
    }

    public void setGalleryItem(GalleryItem galleryItem) {
        this.galleryItem = galleryItem;
        notifyChange();
    }

    @Bindable
    public String getCaption() {
        return galleryItem.getCaption();
    }
}
