package com.clewis.flickrfindr.feature.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.clewis.flickrfindr.R;
import com.clewis.flickrfindr.datamodel.Photo;
import com.clewis.flickrfindr.feature.base.ImageCallback;
import com.clewis.flickrfindr.feature.base.ImageAdapterHelper;

public class SearchImageViewHolder extends RecyclerView.ViewHolder {


    private final ImageAdapterHelper imageAdapterHelper;


    public SearchImageViewHolder(LayoutInflater inflater, ViewGroup parent, ImageCallback callback) {
        super(inflater.inflate(R.layout.search_item_view, parent, false));
        imageAdapterHelper = new ImageAdapterHelper(itemView.findViewById(R.id.image_view), callback);
    }

    public void onBind(@NonNull Photo photo) {
        imageAdapterHelper.onBind(photo);
    }
}
