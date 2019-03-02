package com.clewis.flickrfindr.fullscreen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.CircularProgressDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.clewis.flickrfindr.R
import com.clewis.flickrfindr.datamodel.Photo


class ImageViewerFragment: Fragment() {

    var loadingIndicator: CircularProgressDrawable? = null

    companion object {
        const val NAME = "ImageViewFragment"

        const val ARG_PHOTO = "ARG_PHOTO"

        fun newInstance(photo: Photo): ImageViewerFragment {
            val fragment = ImageViewerFragment()

            val args = Bundle()
            args.putSerializable(ARG_PHOTO, photo)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_viewer, container, false)

        val photo = arguments?.get(ARG_PHOTO) as Photo?

        val photoView: ImageView? = view.findViewById(R.id.image_viewer_image_view)


        if (photo != null && photoView != null) {
            loadingIndicator = CircularProgressDrawable(view.context)
            loadingIndicator?.strokeWidth = 5f
            loadingIndicator?.centerRadius = 25f
            loadingIndicator?.start()
            Glide.with(view.context).load(photo.getUrl()).placeholder(loadingIndicator).into(photoView)
        } else {
            //Uh oh! non-recoverable error...
        }
        return view;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingIndicator?.stop()
    }
}
