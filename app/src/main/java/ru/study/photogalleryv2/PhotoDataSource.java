package ru.study.photogalleryv2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PositionalDataSource<GalleryItem> {
    private static final String queryParameters = "?method=flickr.photos.getRecent"
        + "&api_key=" + Settings.API_KEY
        + "&format=json"
        + "&nojsoncallback=1"
        + "&extras=url_s";

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<GalleryItem> callback) {
        FlickrFetch.getInstance()
                .getAPI()
                .getPhotos(queryParameters)
                .enqueue(new Callback<JSONRootWrapper>() {
                    @Override
                    public void onResponse(Call<JSONRootWrapper> call,
                                           Response<JSONRootWrapper> response) {
                        if (response.body() != null) {
                            List<GalleryItem> galleryItems = response.body()
                                    .getGalleryItemWrapper()
                                    .getGalleryItems();
                            List<GalleryItem> result = safeSubList(galleryItems,
                                    params.requestedStartPosition, params.requestedLoadSize);
                            callback.onResult(result, 0);
                        }
                    }
                    @Override
                    public void onFailure(Call<JSONRootWrapper> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        Log.d("PhotoDataSource", "loadInitial, requestedStartPosition = "
                + params.requestedStartPosition
                + ", requestedLoadSize = " + params.requestedLoadSize
//                + ", startViewPosition = " + startViewPosition
        );
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<GalleryItem> callback) {
        FlickrFetch.getInstance()
                .getAPI()
                .getPhotos(queryParameters)
                .enqueue(new Callback<JSONRootWrapper>() {
                    @Override
                    public void onResponse(Call<JSONRootWrapper> call,
                                           Response<JSONRootWrapper> response) {
                        if (response.body() != null) {
                            List<GalleryItem> galleryItems = response.body()
                                    .getGalleryItemWrapper()
                                    .getGalleryItems();
                            List<GalleryItem> result = safeSubList(galleryItems,
                                    params.startPosition, params.startPosition + params.loadSize);
                            callback.onResult(result);
                        }
                    }
                    @Override
                    public void onFailure(Call<JSONRootWrapper> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        Log.d("PhotoDataSource", "loadRange, startPosition = "
                + params.startPosition + ", loadSize = " + params.loadSize);
    }

    public static  <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
        int size = list.size();
        if (fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex) {
            return Collections.emptyList();
        }

        fromIndex = Math.max(0, fromIndex);
        toIndex = Math.min(size, toIndex);

        return list.subList(fromIndex, toIndex);
    }
}