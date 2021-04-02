package com.moapps.moviesnews.data.datasource.nowplaying

import androidx.paging.DataSource
import com.moapps.moviesnews.pojo.Results


class NowPlayingDataSourceFactory(var nowPlayingDataSource:NowPlayingDataSource): DataSource.Factory<Int,Results>() {

    override fun create(): DataSource<Int, Results> {
        return nowPlayingDataSource
    }
}