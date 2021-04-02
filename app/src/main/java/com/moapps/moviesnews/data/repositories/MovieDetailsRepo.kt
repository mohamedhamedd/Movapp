package com.moapps.moviesnews.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moapps.moviesnews.data.network.api.ApiEndPoints
import com.moapps.moviesnews.data.datasource.moviedetails.cast.RecommendDataSource
import com.moapps.moviesnews.data.datasource.moviedetails.cast.RecommendDataSourceFactory
import com.moapps.moviesnews.data.network.RetrofitClient
import com.moapps.moviesnews.pojo.*
import com.moapps.moviesnews.utilis.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsRepo(var recommendDataSourceFactory: RecommendDataSourceFactory, var retrofitClient: RetrofitClient) {

    fun getMovieDetails(movieId: Int): MutableLiveData<MovieDetails> {
        val responseLive = MutableLiveData<MovieDetails>()
        GlobalScope.launch(Dispatchers.IO) {

            retrofitClient.getAPI(ApiEndPoints::class.java).getMovieDetails(movieId)
                    .enqueue(object : Callback<MovieDetails> {
                        override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                            if (response.isSuccessful) {
                                GlobalScope.launch(Dispatchers.Main) { responseLive.value = response.body()!! }
                            }
                        }

                        override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }
                    })
        }
        return responseLive
    }

    fun getCast(movieId: Int): LiveData<List<Cast>> {
        val responseList = MutableLiveData<List<Cast>>()
        GlobalScope.launch(Dispatchers.IO) {
            retrofitClient.getAPI(ApiEndPoints::class.java).getCredits(movieId)
                    .enqueue(object : Callback<Credits> {
                        override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                            if (response.isSuccessful) {
                                GlobalScope.launch(Dispatchers.Main) { responseList.value = response.body()!!.cast }
                            }
                        }

                        override fun onFailure(call: Call<Credits>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }

                    })
        }

        return responseList
    }

    fun getRecommendation(movieId: Int): LiveData<PagedList<Results>>? {
        recommendDataSourceFactory.movie_id = movieId
        val popularConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(RecommendDataSource.PAGE_SIZE)
                .build()
        return LivePagedListBuilder(recommendDataSourceFactory, popularConfig)
                .build()
    }

    fun getYoutubeKey(movieId: Int): LiveData<List<Result>> {
        val responseLive = MutableLiveData<List<Result>>()
        GlobalScope.launch(Dispatchers.IO) {
            retrofitClient.getAPI(ApiEndPoints::class.java).getYoutubeVideo(movieId)
                    .enqueue(object : Callback<YoutubeVideo> {
                        override fun onResponse(call: Call<YoutubeVideo>, response: Response<YoutubeVideo>) {
                            if (response.isSuccessful) {
                                GlobalScope.launch(Dispatchers.Main) {
                                    responseLive.value = response.body()!!.results
                                }
                            }
                        }

                        override fun onFailure(call: Call<YoutubeVideo>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }
                    })

        }
        return responseLive
    }

}