package com.clewis.flickrfindr.fullscreen

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v4.widget.CircularProgressDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.clewis.flickrfindr.R
import com.clewis.flickrfindr.datamodel.Photo


class ImageViewerFragment: Fragment() {

    private var loadingIndicator: CircularProgressDrawable? = null
    private var imageView: ImageView? = null

    companion object {
        const val NAME = "ImageViewFragment"

        const val ARG_PHOTO = "ARG_PHOTO"
        const val ARG_ASPECT_RATIO = "ARG_ASPECT_RATIO"

        fun newInstance(photo: Photo, aspectRatio: Float): ImageViewerFragment {
            val fragment = ImageViewerFragment()

            val args = Bundle()
            args.putSerializable(ARG_PHOTO, photo)
            args.putFloat(ARG_ASPECT_RATIO, aspectRatio)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_viewer, container, false)

        val photo = arguments?.get(ARG_PHOTO) as Photo?
        val aspectRatio = arguments?.get(ARG_ASPECT_RATIO) as Float? ?: 1f

        val photoView: ImageView? = view.findViewById(R.id.image_viewer_image_view)
        imageView = photoView
        if (container != null) {
            view?.layoutParams?.height = container.height
            view?.layoutParams?.width = container.width

            if (aspectRatio > 1f) {
                val newHeight = (container.width / aspectRatio).toInt()
                photoView?.layoutParams?.width = container.width
                photoView?.layoutParams?.height = newHeight
                (photoView?.layoutParams as? FrameLayout.LayoutParams?)?.topMargin = (container.height - newHeight) / 2
            } else {
                val newWidth = (container.height * aspectRatio).toInt()
                photoView?.layoutParams?.height = container.height
                photoView?.layoutParams?.width = newWidth
                (photoView?.layoutParams as? FrameLayout.LayoutParams?)?.marginStart = (container.width - newWidth) / 2
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            photoView?.transitionName = photo?.id
        }
        if (photo != null && photoView != null) {
            loadingIndicator = CircularProgressDrawable(view.context)
            loadingIndicator?.strokeWidth = 5f
            loadingIndicator?.centerRadius = 25f
            loadingIndicator?.start()
            Glide.with(view.context)
                    .load(photo.getUrl())
                    .listener(getRequestListener())
                    .placeholder(loadingIndicator)
                    .into(photoView)
        } else {
            //Uh oh! non-recoverable error...
        }
        return view;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingIndicator?.stop()
    }

    private fun getRequestListener(): RequestListener<Drawable> {
        return object: RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                loadingIndicator?.stop()
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                loadingIndicator?.stop()
                return false
            }
        }
    }
}
