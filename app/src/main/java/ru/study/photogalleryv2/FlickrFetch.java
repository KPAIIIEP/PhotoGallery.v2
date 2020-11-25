package ru.study.photogalleryv2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrFetch {
    private static FlickrFetch instance;
    private final Retrofit retrofit;
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String queryParameters = "?method=flickr.photos.getRecent"
            + "&api_key=" + Settings.API_KEY
            + "&format=json"
            + "&nojsoncallback=1"
            + "&extras=url_s";
    private final MutableLiveData<List<GalleryItem>> liveData = new MutableLiveData<>();

    private FlickrFetch() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static FlickrFetch getInstance() {
        if (instance == null) {
            instance = new FlickrFetch();
        }
        return instance;
    }

    public LiveData<List<GalleryItem>> getLiveData() {
        return liveData;
    }

    public FlickrAPI getAPI() {
        return retrofit.create(FlickrAPI.class);
    }

    public void requestGalleryItems() {
        this.getAPI().getPhotos(queryParameters)
                .enqueue(new Callback<JSONRootWrapper>() {
                    @Override
                    public void onResponse(Call<JSONRootWrapper> call,
                                           Response<JSONRootWrapper> response) {
                        List<GalleryItem> galleryItems  = response.body()
                                .getGalleryItemWrapper()
                                .getGalleryItems();
                        liveData.setValue(galleryItems);
                    }

                    @Override
                    public void onFailure(Call<JSONRootWrapper> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}