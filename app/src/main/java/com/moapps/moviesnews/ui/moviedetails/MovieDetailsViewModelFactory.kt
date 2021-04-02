package com.moapps.moviesnews.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moapps.moviesnews.data.repositories.MovieDetailsRepo

@Suppress("UNCHECKED_CAST")
class MovieDetailsViewModelFactory (private val repo: MovieDetailsRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(repo) as T
    }

}