package com.moapps.moviesnews.data.datasource.moviedetails.cast

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class RecommendDataSourceFactory(var recommendDatasource:RecommendDataSource): DataSource.Factory<Int,Results>() {
    var movie_id: Int? = null
    override fun create(): DataSource<Int, Results> {
        recommendDatasource.movie_id = movie_id
        return recommendDatasource
    }
}