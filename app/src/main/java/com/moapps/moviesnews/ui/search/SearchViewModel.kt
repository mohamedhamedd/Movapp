package com.moapps.moviesnews.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.moapps.moviesnews.data.repositories.SearchRepo
import com.moapps.moviesnews.pojo.Results

class SearchViewModel(var searchRepo:SearchRepo):ViewModel() {

    val txt = MutableLiveData<String>()
    var searchInterface: SearchInterface? = null

    fun getSearchResults(search:String): LiveData<PagedList<Results>> {
        return searchRepo.getSearch(search)
    }

}