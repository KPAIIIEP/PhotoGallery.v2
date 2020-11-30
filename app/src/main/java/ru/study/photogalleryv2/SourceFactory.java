package ru.study.photogalleryv2;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

public class SourceFactory extends DataSource.Factory<Integer, GalleryItem> {
    private MutableLiveData<PositionalDataSource<GalleryItem>> sourceMutableLiveData =
            new MutableLiveData<>();

    public MutableLiveData<PositionalDataSource<GalleryItem>> getSourceMutableLiveData() {
        return sourceMutableLiveData;
    }

    @NonNull
    @Override
    public DataSource<Integer, GalleryItem> create() {
        PhotoDataSource dataSource = new PhotoDataSource();
        sourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }
}