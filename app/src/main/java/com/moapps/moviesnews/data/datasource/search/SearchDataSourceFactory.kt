package com.moapps.moviesnews.data.datasource.search

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class SearchDataSourceFactory(var searchDatasource:SearchDataSource): DataSource.Factory<Int,Results>() {

    var search: String? = null
    override fun create(): DataSource<Int, Results> {
        searchDatasource.search = search
        return searchDatasource
    }
}