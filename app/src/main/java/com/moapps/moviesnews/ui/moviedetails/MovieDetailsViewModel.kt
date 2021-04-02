package com.moapps.moviesnews.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.moapps.moviesnews.data.repositories.MovieDetailsRepo
import com.moapps.moviesnews.pojo.Cast
import com.moapps.moviesnews.pojo.MovieDetails
import com.moapps.moviesnews.pojo.Result
import com.moapps.moviesnews.pojo.Results

class MovieDetailsViewModel(var movieDetailsRepo:MovieDetailsRepo):ViewModel() {

    val movieId = MutableLiveData<Int>()
    var movieDetailsInterface: MovieDetailsInterface? = null

    fun getMovieDetails(movieId: Int): MutableLiveData<MovieDetails> {
        return  movieDetailsRepo.getMovieDetails(movieId)
    }

    fun getCast(movieId: Int):LiveData<List<Cast>> {
        return  movieDetailsRepo.getCast(movieId)
    }

    fun getRecommendation(movieId: Int):LiveData<PagedList<Results>>?{
        return movieDetailsRepo.getRecommendation(movieId)
    }

    fun getYoutubeKey(movieId: Int):LiveData<List<Result>>{
        return movieDetailsRepo.getYoutubeKey(movieId)
    }

}