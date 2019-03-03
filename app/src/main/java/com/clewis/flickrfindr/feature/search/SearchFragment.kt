package com.clewis.flickrfindr.feature.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
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


class SearchFragment: Fragment(), SearchContract.View, ImageCallback {

    var imageAdapter: SearchImageAdapter? = null

    var presenter: SearchContract.Presenter? = null

    private var imageRecyclerView: RecyclerView? = null


    companion object {
        const val NAME = "SearchFragment"

        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageAdapter = SearchImageAdapter(this)
        presenter = SearchPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        imageRecyclerView = view.findViewById(R.id.search_recycler_view)
        imageRecyclerView?.adapter = imageAdapter
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
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

    override fun onDestroyView() {
        super.onDestroyView()
        imageRecyclerView?.clearOnScrollListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

    override fun onSearchResults(photos: List<Photo>) {
        imageAdapter?.onNewSearch(photos)
    }


    override fun onAdditionalSearchResults(photos: List<Photo>) {
        imageAdapter?.addItems(photos)
    }

    override fun onImageClicked(photo: Photo, photoView: ImageView) {
        (activity as ImageCallback?)?.onImageClicked(photo, photoView)
    }

}