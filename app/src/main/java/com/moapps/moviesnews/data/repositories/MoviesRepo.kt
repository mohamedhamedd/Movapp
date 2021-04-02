package com.moapps.moviesnews.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moapps.moviesnews.data.datasource.nowplaying.NowPlayingDataSource
import com.moapps.moviesnews.data.datasource.nowplaying.NowPlayingDataSourceFactory
import com.moapps.moviesnews.data.datasource.popular.TopRatedDataSource
import com.moapps.moviesnews.data.datasource.popular.TopRatedDataSourceFactory
import com.moapps.moviesnews.data.datasource.upcoming.UpcomingDataSource
import com.moapps.moviesnews.data.datasource.upcoming.UpcomingDataSourceFactory
import com.moapps.moviesnews.pojo.Results

class MoviesRepo(var nowPlayingDataSourceFactory:NowPlayingDataSourceFactory,
                 var upcomingDataSourceFactory:UpcomingDataSourceFactory,
                 var topRatedDataSourceFactory:TopRatedDataSourceFactory) {

    fun getNowPlaying():LiveData<PagedList<Results>>{
        val nowPLayingConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(NowPlayingDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(nowPlayingDataSourceFactory,nowPLayingConfig)
                .build()
    }

    fun getUpcoming(): LiveData<PagedList<Results>>{
        val upcomingConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(UpcomingDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(upcomingDataSourceFactory,upcomingConfig)
                .build()
    }

    fun getTopRated(): LiveData<PagedList<Results>>{
        val topRatedConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(TopRatedDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(topRatedDataSourceFactory, topRatedConfig)
                .build()
    }

}