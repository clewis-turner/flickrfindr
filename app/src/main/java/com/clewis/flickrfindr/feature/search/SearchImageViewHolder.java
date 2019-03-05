package com.clewis.flickrfindr.feature.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clewis.flickrfindr.R;
import com.clewis.flickrfindr.datamodel.Photo;
import com.clewis.flickrfindr.feature.base.ImageAdapterHelper;
import com.clewis.flickrfindr.feature.base.ImageCallback;

public class SearchImageViewHolder extends RecyclerView.ViewHolder {


    private final ImageAdapterHelper imageAdapterHelper;


    public SearchImageViewHolder(LayoutInflater inflater, ViewGroup parent, ImageCallback callback) {
        super(inflater.inflate(R.layout.search_item_view, parent, false));
        ImageView imageView = itemView.findViewById(R.id.search_item_image_view);
        TextView textView = itemView.findViewById(R.id.search_item_text_view);
        imageAdapterHelper = new ImageAdapterHelper(imageView, textView, callback);
    }

    public void onBind(@NonNull Photo photo) {
        imageAdapterHelper.onBind(photo);
    }
}
