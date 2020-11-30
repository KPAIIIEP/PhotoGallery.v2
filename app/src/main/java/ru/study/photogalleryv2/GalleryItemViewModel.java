package ru.study.photogalleryv2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

public class GalleryItemViewModel extends ViewModel {
    public LiveData<PagedList<GalleryItem>> pagedListLiveData;
    public LiveData<PositionalDataSource<GalleryItem>> dataSourceLiveData;

    public GalleryItemViewModel() {
        SourceFactory sourceFactory = new SourceFactory();
        dataSourceLiveData = sourceFactory.getSourceMutableLiveData();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();
        pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config)
//                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build();
    }
}
