package ru.study.photogalleryv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);
        FlickrFetch instance = FlickrFetch.getInstance();
        instance.requestGalleryItems();
        instance.getLiveData().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItems) {
                photoAdapter.setPhotos(galleryItems);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private GalleryItem galleryItem;
        private ImageView imageView;
        private TextView textView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            this.galleryItem = galleryItem;
            textView.setText("caption: " + galleryItem.getCaption()
                    +  "\nurl: " + galleryItem.getUrl()
            );
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<GalleryItem> galleryItems = new ArrayList<>();

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_view, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            GalleryItem galleryItem = galleryItems.get(position);
            holder.bindGalleryItem(galleryItem);
            Picasso instance = Picasso.get();
            instance.load(galleryItem.getUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return galleryItems.size();
        }

        public void setPhotos(List<GalleryItem> items) {
            galleryItems.clear();
            galleryItems.addAll(items);
            notifyDataSetChanged();
        }
    }
}