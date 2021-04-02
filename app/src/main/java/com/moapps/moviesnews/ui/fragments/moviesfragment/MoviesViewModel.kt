package com.moapps.moviesnews.ui.fragments.moviesfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.moapps.moviesnews.data.repositories.MoviesRepo
import com.moapps.moviesnews.pojo.Results

class MoviesViewModel(var moviesRepo:MoviesRepo) : ViewModel() {

    var moviesInterface: MoviesInterface? = null
    var upcomingLive: LiveData<PagedList<Results>>? = null
    var nowPlayingLive: LiveData<PagedList<Results>>? = null
    var topRatedLive: LiveData<PagedList<Results>>? = null

    init {
            upcomingLive = moviesRepo.getUpcoming()
            nowPlayingLive = moviesRepo.getNowPlaying()
            topRatedLive = moviesRepo.getTopRated()
    }

}