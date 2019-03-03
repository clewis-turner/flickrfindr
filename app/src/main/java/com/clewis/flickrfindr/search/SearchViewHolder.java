package com.clewis.flickrfindr.search;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.clewis.flickrfindr.R;
import com.clewis.flickrfindr.datamodel.Photo;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    private Photo currentPhoto = null;


    public SearchViewHolder(LayoutInflater inflater,
                            ViewGroup parent,
                            SearchCallback callback) {

        super(inflater.inflate(R.layout.search_item_view, parent, false));
        imageView = itemView.findViewById(R.id.search_item_image_view);
        imageView.setOnClickListener(v -> {
            Photo photo = currentPhoto;
            if (photo != null) {
                callback.onImageClicked(photo, imageView);
            }
        });

    }

    public void onBind(@NonNull Photo photo) {
        currentPhoto = photo;
        Glide.with(itemView.getContext()).load(photo.getUrl()).into(imageView);
        ViewCompat.setTransitionName(imageView, photo.getId());
    }
}
