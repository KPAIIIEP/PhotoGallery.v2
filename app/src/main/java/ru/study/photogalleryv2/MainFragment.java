package ru.study.photogalleryv2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.study.photogalleryv2.databinding.ActivityFragmentBinding;
import ru.study.photogalleryv2.databinding.ItemViewBinding;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private int firstVisibleItemPosition = 0;
    private RecyclerView recyclerView;
    private ActivityFragmentBinding binding;
    //private PhotoAdapter adapter;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            firstVisibleItemPosition = savedInstanceState.getInt("firstVisibleItemPosition");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                //Log.d(TAG, String.valueOf(firstVisibleItemPosition));
            }
        });
        GalleryItemViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(GalleryItemViewModel.class);
        final PhotoAdapter adapter = new PhotoAdapter(new DiffUtil.ItemCallback<GalleryItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull GalleryItem oldItem, @NonNull GalleryItem newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull GalleryItem oldItem, @NonNull GalleryItem newItem) {
                return oldItem == newItem;
            }
        });
        viewModel.pagedListLiveData.observe(requireActivity(), new Observer<PagedList<GalleryItem>>() {
            @Override
            public void onChanged(PagedList<GalleryItem> galleryItems) {
                adapter.submitList(galleryItems);
            }
        });
        recyclerView.setAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, String.valueOf(firstVisibleItemPosition));
        outState.putInt("firstVisibleItemPosition", firstVisibleItemPosition);
        super.onSaveInstanceState(outState);
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ItemViewBinding binding;

        public PhotoHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            binding.setGalleryItem(galleryItem);
            binding.executePendingBindings();
        }
    }

    private class PhotoAdapter extends PagedListAdapter<GalleryItem, PhotoHolder> {
        protected PhotoAdapter(@NonNull DiffUtil.ItemCallback<GalleryItem> diffCallback) {
            super(diffCallback);
        }

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
            holder.bindGalleryItem(getItem(position));
        }
    }
}