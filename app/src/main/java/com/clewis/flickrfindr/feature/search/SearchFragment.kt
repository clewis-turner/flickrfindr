package com.clewis.flickrfindr.feature.search

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.clewis.flickrfindr.R
import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.feature.base.ImageCallback
import com.clewis.flickrfindr.util.ViewUtils


class SearchFragment: Fragment(), SearchContract.View, ImageCallback {

    var imageAdapter: SearchImageAdapter? = null

    var presenter: SearchContract.Presenter? = null

    private var imageRecyclerView: RecyclerView? = null

    private var searchProgressDrawable: CircularProgressDrawable? = null
    private var searchLoadingView: View? = null

    companion object {
        const val NAME = "SearchFragment"

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageAdapter = SearchImageAdapter(this)

        val context = context ?: return
        presenter = SearchPresenter(context, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchLoadingView = view.findViewById(R.id.search_progress_view)
        searchProgressDrawable = ViewUtils.getProgressDrawable(context)
        searchLoadingView?.background = searchProgressDrawable

        imageRecyclerView = view.findViewById(R.id.search_recycler_view)
        imageRecyclerView?.adapter = imageAdapter
        val layoutManager = GridLayoutManager(context, 3)
        imageRecyclerView?.layoutManager = layoutManager
        imageRecyclerView?.setHasFixedSize(true)

        imageRecyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val perPage = presenter?.getCurrentPhotoData()?.perPage ?: return
                val adapter = imageAdapter ?: return

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING &&
                        adapter.furthestBoundItem > (adapter.itemCount - perPage) ) {
                    presenter?.onMaxScroll()
                }
            }
        })

        val searchEditText: EditText = view.findViewById(R.id.search_input)
        searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                showLoading(true)

                presenter?.onSearch(v?.text.toString())
                searchEditText.setText("")

                val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputManager?.hideSoftInputFromWindow(
                        activity?.currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
            }
            true
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        exitTransition = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageRecyclerView?.clearOnScrollListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
        showLoading(false)
        searchProgressDrawable?.stop()
    }

    override fun onSearchResults(photos: List<Photo>) {
        showLoading(false)
        imageAdapter?.onNewSearch(photos)
    }

    override fun onSearchError() {
        showLoading(false)
        val context = context
        if (context != null && isAdded) {
            AlertDialog.Builder(context)
                    .setMessage(R.string.network_error)
                    .setPositiveButton("Retry") { _, _ ->
                        showLoading(true)
                        presenter?.retrySearch()
                    }
                    .setNegativeButton("Cancel")  { dialog, _ ->
                        dialog.cancel()
                    }.show()
        }
    }

    override fun onAdditionalSearchResults(photos: List<Photo>) {
        imageAdapter?.addItems(photos)
    }

    override fun onImageClicked(photo: Photo, photoView: ImageView) {
        (activity as ImageCallback?)?.onImageClicked(photo, photoView)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            searchProgressDrawable?.start()
            searchLoadingView?.visibility = View.VISIBLE
        } else {
            searchProgressDrawable?.stop()
            searchLoadingView?.visibility = View.GONE
        }
    }

}