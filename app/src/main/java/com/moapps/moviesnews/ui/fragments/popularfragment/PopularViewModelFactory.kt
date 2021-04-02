package com.moapps.moviesnews.ui.fragments.popularfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moapps.moviesnews.data.repositories.PopularRepo

@Suppress("UNCHECKED_CAST")
class PopularViewModelFactory (private val repo: PopularRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularViewModel(repo) as T
    }

}