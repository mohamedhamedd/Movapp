package com.moapps.moviesnews.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moapps.moviesnews.data.datasource.popular.PopularDataSource
import com.moapps.moviesnews.data.datasource.popular.PopularDataSourceFactory
import com.moapps.moviesnews.pojo.Results

class PopularRepo(var popularDataSourceFactory:PopularDataSourceFactory) {

    fun getPopular():LiveData<PagedList<Results>> {
        val popularConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(PopularDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(popularDataSourceFactory,popularConfig)
                .build()
    }

}