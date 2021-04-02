package com.moapps.moviesnews.ui.fragments.moviesfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moapps.moviesnews.data.repositories.MoviesRepo

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory (private val repo: MoviesRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(repo) as T
    }

}