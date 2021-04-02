package com.moapps.moviesnews.data.datasource.upcoming

import androidx.paging.PageKeyedDataSource
import com.moapps.moviesnews.data.network.RetrofitClient
import com.moapps.moviesnews.data.network.api.ApiEndPoints
import com.moapps.moviesnews.pojo.Movies
import com.moapps.moviesnews.pojo.Results
import com.moapps.moviesnews.utilis.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingDataSource(var retrofitClient: RetrofitClient) : PageKeyedDataSource<Int, Results>() {

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Results>
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            retrofitClient.getAPI(ApiEndPoints::class.java).getMovies(TOPIC, FIRST_PAGE)
                    .enqueue(object : Callback<Movies> {
                        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {

                            if (response.isSuccessful) {
                                val apiResponse = response.body()!!
                                val responseItems = apiResponse.results
                                responseItems.let {
                                    callback.onResult(responseItems, null, FIRST_PAGE + 1)
                                }
                            }
                        }

                        override fun onFailure(call: Call<Movies>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }
                    })


        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {

        GlobalScope.launch(Dispatchers.IO) {
            retrofitClient.getAPI(ApiEndPoints::class.java).getMovies(TOPIC, params.key)
                    .enqueue(object : Callback<Movies> {
                        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                            if (response.isSuccessful) {
                                val apiResponse = response.body()!!
                                val responseItems = apiResponse.results
                                val key: Int = if (params.key > 1) params.key - 1 else 0

                                responseItems.let {
                                    callback.onResult(responseItems, key)
                                }

                            }
                        }

                        override fun onFailure(call: Call<Movies>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }
                    })

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {


        GlobalScope.launch(Dispatchers.IO) {
            retrofitClient.getAPI(ApiEndPoints::class.java).getMovies(TOPIC, params.key)
                    .enqueue(object : Callback<Movies> {
                        override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                            if (response.isSuccessful) {
                                val apiResponse = response.body()!!
                                val responseItems = apiResponse.results
                                var key: Int = params.key


                                if (key != apiResponse.total_pages) {
                                    key = params.key + 1
                                }

                                responseItems.let {
                                    callback.onResult(responseItems, key)
                                }

                            }
                        }

                        override fun onFailure(call: Call<Movies>, t: Throwable) {
                            if (t is NoInternetException) {
                                // show No Connectivity message to user or do whatever you want.
                            }
                        }
                    })


        }

    }

    companion object {
        const val PAGE_SIZE = 4
        const val FIRST_PAGE = 1
        const val TOPIC = "upcoming"
    }

}