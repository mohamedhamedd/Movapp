package com.moapps.moviesnews.di

import android.app.Application
import com.moapps.moviesnews.data.datasource.moviedetails.cast.RecommendDataSource
import com.moapps.moviesnews.data.datasource.moviedetails.cast.RecommendDataSourceFactory
import com.moapps.moviesnews.data.datasource.nowplaying.NowPlayingDataSource
import com.moapps.moviesnews.data.datasource.nowplaying.NowPlayingDataSourceFactory
import com.moapps.moviesnews.data.datasource.popular.PopularDataSource
import com.moapps.moviesnews.data.datasource.popular.PopularDataSourceFactory
import com.moapps.moviesnews.data.datasource.popular.TopRatedDataSource
import com.moapps.moviesnews.data.datasource.popular.TopRatedDataSourceFactory
import com.moapps.moviesnews.data.datasource.region.RegionDataSource
import com.moapps.moviesnews.data.datasource.region.RegionDataSourceFactory
import com.moapps.moviesnews.data.datasource.search.SearchDataSource
import com.moapps.moviesnews.data.datasource.search.SearchDataSourceFactory
import com.moapps.moviesnews.data.datasource.upcoming.UpcomingDataSource
import com.moapps.moviesnews.data.datasource.upcoming.UpcomingDataSourceFactory
import com.moapps.moviesnews.data.network.NetworkConnectionInterceptor
import com.moapps.moviesnews.data.network.RetrofitClient
import com.moapps.moviesnews.data.repositories.*
import com.moapps.moviesnews.ui.fragments.moviesfragment.MovieViewModelFactory
import com.moapps.moviesnews.ui.fragments.popularfragment.PopularViewModelFactory
import com.moapps.moviesnews.ui.fragments.region.RegionViewModelFactory
import com.moapps.moviesnews.ui.moviedetails.MovieDetailsViewModelFactory
import com.moapps.moviesnews.ui.search.SearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DIKodein : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@DIKodein))

        //NetworkConnection
       bind() from singleton { NetworkConnectionInterceptor(instance()) }

        //API
        bind() from singleton { RetrofitClient(instance()) }

        //DataSources
        bind() from singleton { RecommendDataSource(instance()) }
        bind() from singleton { NowPlayingDataSource(instance()) }
        bind() from singleton { UpcomingDataSource(instance()) }
        bind() from singleton { TopRatedDataSource(instance()) }
        bind() from singleton { PopularDataSource(instance()) }
        bind() from singleton { RegionDataSource(instance()) }
        bind() from singleton { SearchDataSource(instance()) }

        //DataSources factory
        bind() from provider { RecommendDataSourceFactory(instance()) }
        bind() from provider { NowPlayingDataSourceFactory(instance()) }
        bind() from provider { UpcomingDataSourceFactory(instance()) }
        bind() from provider { TopRatedDataSourceFactory(instance()) }
        bind() from provider { PopularDataSourceFactory(instance()) }
        bind() from provider { RegionDataSourceFactory(instance()) }
        bind() from provider { SearchDataSourceFactory(instance()) }

        //Repositories
        bind() from singleton { MovieDetailsRepo(instance(), instance()) }
        bind() from singleton { MoviesRepo(instance(), instance(), instance()) }
        bind() from singleton { PopularRepo(instance()) }
        bind() from singleton { RegionRepo(instance()) }
        bind() from singleton { SearchRepo(instance()) }

        //ViewModel Factory
        bind() from provider { MovieDetailsViewModelFactory(instance()) }
        bind() from provider { MovieViewModelFactory(instance()) }
        bind() from provider { PopularViewModelFactory(instance()) }
        bind() from provider { RegionViewModelFactory(instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }


    }
}