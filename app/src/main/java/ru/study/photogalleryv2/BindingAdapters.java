package ru.study.photogalleryv2;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter("android:src")
    public static void imageLoad(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageView);
    }
}
