package com.moapps.moviesnews.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moapps.moviesnews.data.repositories.SearchRepo

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory (private val repo: SearchRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repo) as T
    }

}