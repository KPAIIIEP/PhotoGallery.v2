package ru.study.photogalleryv2;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import java.util.List;

public class SourceFactory extends DataSource.Factory<Integer, GalleryItem> {
    private final List<GalleryItem> galleryItems;

    SourceFactory(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    @NonNull
    @Override
    public DataSource<Integer, GalleryItem> create() {
        return new PhotoDataSource(galleryItems);
    }
}