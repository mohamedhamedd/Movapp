package com.moapps.moviesnews.ui.fragments.popularfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.moapps.moviesnews.data.repositories.PopularRepo
import com.moapps.moviesnews.pojo.Results

class PopularViewModel(var popularRepo:PopularRepo):ViewModel() {

    var popularInterface: PopularInterface? = null
    var response: LiveData<PagedList<Results>>? = null

    init {
            response = popularRepo.getPopular()
    }

}