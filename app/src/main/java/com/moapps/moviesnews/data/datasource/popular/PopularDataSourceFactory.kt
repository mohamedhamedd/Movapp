package com.moapps.moviesnews.data.datasource.popular

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class PopularDataSourceFactory(var popularDatasource:PopularDataSource): DataSource.Factory<Int,Results>() {

    override fun create(): DataSource<Int, Results> {
        return popularDatasource
    }
}