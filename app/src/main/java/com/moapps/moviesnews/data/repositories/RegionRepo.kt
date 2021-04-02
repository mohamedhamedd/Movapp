package com.moapps.moviesnews.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moapps.moviesnews.data.datasource.region.RegionDataSource
import com.moapps.moviesnews.data.datasource.region.RegionDataSourceFactory
import com.moapps.moviesnews.pojo.Results

class RegionRepo(var regionDataSourceFactory:RegionDataSourceFactory) {

    fun getRegion(region:String): LiveData<PagedList<Results>> {
        regionDataSourceFactory.region = region
        val nowPlayingRegionConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(RegionDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(regionDataSourceFactory, nowPlayingRegionConfig)
                .build()
    }

}