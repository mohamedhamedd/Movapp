package com.moapps.moviesnews.ui.fragments.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.moapps.moviesnews.data.repositories.RegionRepo
import com.moapps.moviesnews.pojo.Results

class RegionViewModel(private var regionRepo:RegionRepo):ViewModel() {

    val region:MutableLiveData<String> = MutableLiveData<String>()
    var regionInterface: RegionInterface? = null

    fun getNowPlayingRegion(regionCouuntry:String): LiveData<PagedList<Results>> {
        return regionRepo.getRegion(regionCouuntry)
    }

}