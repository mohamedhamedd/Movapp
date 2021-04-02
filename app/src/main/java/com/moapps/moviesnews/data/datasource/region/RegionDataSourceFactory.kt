package com.moapps.moviesnews.data.datasource.region

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class RegionDataSourceFactory(var regionDatasouurce:RegionDataSource): DataSource.Factory<Int,Results>() {

    var region: String? = null
    override fun create(): DataSource<Int, Results> {
        regionDatasouurce.region = region
        return regionDatasouurce
    }
}