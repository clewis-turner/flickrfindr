package com.clewis.flickrfindr.feature.search

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class RecentSearchManager(context: Context?) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        const val KEY_RECENT_SEARCHES = "KEY_RECENT_SEARCHES"
    }

    fun getRecentSearches(): List<String> {
        return sharedPreferences.getString(KEY_RECENT_SEARCHES,  null)?.split(",") ?: emptyList()
    }

    fun onSearch(search: String) {
        val recentSearchArray = getRecentSearches()

        val newRecentSearchesEncoded = StringBuilder()
        newRecentSearchesEncoded.append(search)

        var i = 0
        for (recentSearch in recentSearchArray) {
            newRecentSearchesEncoded.append(',')
            newRecentSearchesEncoded.append(recentSearch)

            //cap at 5 recent searches
            i++
            if (i == 4) {
                break
            }
        }

        sharedPreferences.edit().putString(KEY_RECENT_SEARCHES, newRecentSearchesEncoded.toString()).apply()
    }
}