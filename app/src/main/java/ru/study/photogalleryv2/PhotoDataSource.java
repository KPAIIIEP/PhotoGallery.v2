package ru.study.photogalleryv2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

public class PhotoDataSource extends PositionalDataSource<GalleryItem> {
    List<GalleryItem> galleryItems;

    public PhotoDataSource(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<GalleryItem> callback) {
        Log.d("PhotoDataSource", "loadInitial, requestedStartPosition = "
                + params.requestedStartPosition
                + ", requestedLoadSize = " + params.requestedLoadSize);
        List<GalleryItem> result = galleryItems.subList(
                params.requestedStartPosition, params.requestedLoadSize);
        callback.onResult(result, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<GalleryItem> callback) {
        Log.d("PhotoDataSource", "loadRange, startPosition = "
                + params.startPosition
                + ", loadSize = " + params.loadSize);
        List<GalleryItem> result = galleryItems.subList(
                params.startPosition, params.loadSize);
        callback.onResult(result);
    }
}