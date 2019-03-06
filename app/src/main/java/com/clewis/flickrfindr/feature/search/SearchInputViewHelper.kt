package com.clewis.flickrfindr.feature.search

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.clewis.flickrfindr.R


class SearchInputViewHelper(searchInputView: View, searches: List<String>?, val onSearch:(search: String) -> Unit) {

    private val rootView = searchInputView
    private val recentSearchesLinearLayout = rootView.findViewById<LinearLayout>(R.id.recent_searches_view)
    private val searchInput = rootView.findViewById<EditText>(R.id.search_input_edit_text)

    init {
        showRecentSearches(searches ?: emptyList())

        searchInput.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                onSearch(v?.text.toString())
                searchInput.setText("")
            }
            true
        }
    }

    public fun hide() {
        rootView.visibility = View.GONE
    }

    public fun reset(recentSearches: List<String>?) {
        val searches = recentSearches ?: emptyList()
        rootView.visibility = View.VISIBLE
        showRecentSearches(searches)
    }

    private fun showRecentSearches(searches: List<String>) {
        recentSearchesLinearLayout.removeAllViews()
        val context = rootView.context ?: return
        for (search in searches) {
            val view = TextView(context)
            view.width = LinearLayout.LayoutParams.MATCH_PARENT
            view.text = search
            view.textSize = 20f

            view.setOnClickListener {
                onSearch(search)
            }

            recentSearchesLinearLayout.addView(view)
        }
    }
}