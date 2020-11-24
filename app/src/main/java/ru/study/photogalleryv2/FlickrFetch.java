package ru.study.photogalleryv2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrFetch {
    private static FlickrFetch instance;
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private Retrofit retrofit;

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


}