package com.moapps.moviesnews.data.datasource.upcoming

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class UpcomingDataSourceFactory(var upcomingDatasource:UpcomingDataSource): DataSource.Factory<Int,Results>() {

    override fun create(): DataSource<Int, Results> {
        return upcomingDatasource
    }
}