package com.clewis.flickrfindr.feature.base;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.clewis.flickrfindr.R;
import com.clewis.flickrfindr.datamodel.Photo;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    private Photo currentPhoto = null;


    public ImageViewHolder(LayoutInflater inflater,
                            ViewGroup parent,
                            ImageCallback callback) {

        super(inflater.inflate(R.layout.saved_item_view, parent, false));
        imageView = itemView.findViewById(R.id.image_view);
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
