package ru.study.photogalleryv2;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class GalleryItemViewModel extends BaseObservable {
    private GalleryItem galleryItem;

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

    @Bindable
    public String getUrl() {
        return galleryItem.getUrl();
    }

    @BindingAdapter("android:src")
    public static void imageLoad(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageView);
    }
}
