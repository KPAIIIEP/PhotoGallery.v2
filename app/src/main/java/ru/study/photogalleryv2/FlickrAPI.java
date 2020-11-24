package ru.study.photogalleryv2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FlickrAPI {
    @GET
    Call<JSONRootWrapper> getPhotos(@Url String queryParameters);
}