package ru.study.photogalleryv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.study.photogalleryv2.databinding.ActivityFragmentBinding;
import ru.study.photogalleryv2.databinding.ItemViewBinding;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
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
        ActivityFragmentBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.activity_fragment, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photoAdapter = new PhotoAdapter();
        binding.recyclerView.setAdapter(photoAdapter);

        FlickrFetch instance = FlickrFetch.getInstance();
        instance.requestGalleryItems();
        instance.getLiveData().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItems) {
                photoAdapter.setPhotos(galleryItems);
            }
        });
        return binding.getRoot();
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ItemViewBinding binding;

        public PhotoHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setViewModel(new GalleryItemViewModel());

        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            binding.getViewModel().setGalleryItem(galleryItem);
            binding.executePendingBindings();
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<GalleryItem> galleryItems = new ArrayList<>();

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ItemViewBinding binding = DataBindingUtil.inflate(inflater,
                    R.layout.item_view, parent, false);
            return new PhotoHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            GalleryItem galleryItem = galleryItems.get(position);
            holder.bindGalleryItem(galleryItem);
            Picasso instance = Picasso.get();
            instance.load(galleryItem.getUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.binding.imageView);
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