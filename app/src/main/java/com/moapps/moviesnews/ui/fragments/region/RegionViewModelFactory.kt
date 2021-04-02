package com.moapps.moviesnews.ui.fragments.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moapps.moviesnews.data.repositories.RegionRepo

@Suppress("UNCHECKED_CAST")
class RegionViewModelFactory (private val repo: RegionRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegionViewModel(repo) as T
    }

}