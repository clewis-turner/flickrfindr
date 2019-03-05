package com.clewis.flickrfindr.feature.base

import android.support.v4.view.ViewCompat
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.clewis.flickrfindr.datamodel.Photo


class ImageAdapterHelper(private val imageView: ImageView,
                         private val textView: TextView,
                         private val callback: ImageCallback) {

    private var currentPhoto: Photo? = null

    init {
        imageView.setOnClickListener { v ->
            val photo = currentPhoto
            if (photo != null) {
                callback.onImageClicked(photo, imageView)
            }
        }
    }

    fun onBind(photo: Photo) {
        currentPhoto = photo
        textView.text = photo.title
        Glide.with(imageView.getContext()).load(photo.getUrl()).into(imageView)
        ViewCompat.setTransitionName(imageView, photo.id)
    }
}
