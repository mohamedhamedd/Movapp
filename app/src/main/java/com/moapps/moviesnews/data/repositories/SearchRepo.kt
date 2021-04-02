package com.moapps.moviesnews.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moapps.moviesnews.data.datasource.search.SearchDataSource
import com.moapps.moviesnews.data.datasource.search.SearchDataSourceFactory
import com.moapps.moviesnews.pojo.Results

class SearchRepo(var searchDataSourceFactory:SearchDataSourceFactory) {

    fun getSearch(search:String): LiveData<PagedList<Results>> {
        searchDataSourceFactory.search = search
        val searchConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(SearchDataSource.PAGE_SIZE)
            .build()
        return LivePagedListBuilder(searchDataSourceFactory, searchConfig)
            .build()
    }

}