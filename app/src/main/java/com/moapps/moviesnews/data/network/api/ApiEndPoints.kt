package com.moapps.moviesnews.data.network.api

import com.moapps.moviesnews.pojo.Credits
import com.moapps.moviesnews.pojo.MovieDetails
import com.moapps.moviesnews.pojo.Movies
import com.moapps.moviesnews.pojo.YoutubeVideo
import com.moapps.moviesnews.utilis.Credentials.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoints {

    @GET("3/movie/{topic}?api_key=$API_KEY")
    fun getMovies(@Path("topic") topic:String,
                  @Query("page") page:Int):Call<Movies>

    @GET("3/movie/now_playing?api_key=$API_KEY")
    fun getNowPlayingRegion(@Query("page") page:Int,
                            @Query("region") region:String):Call<Movies>

    @GET("3/search/movie?api_key=$API_KEY")
    fun getSearchResult(@Query("page") page:Int,
                        @Query("query") query:String):Call<Movies>

    @GET("3/movie/{movie_id}?api_key=$API_KEY")
    fun getMovieDetails(@Path("movie_id") movie_id:Int):Call<MovieDetails>

    @GET("3/movie/{movie_id}/credits?api_key=$API_KEY")
    fun getCredits(@Path("movie_id") movie_id:Int):Call<Credits>

    @GET("3/movie/{movie_id}/recommendations?api_key=$API_KEY")
    fun getRecommendations(@Path("movie_id") movie_id:Int,
                           @Query("page") page:Int):Call<Movies>

    @GET("3/movie/{movie_id}/videos?api_key=$API_KEY")
    fun getYoutubeVideo(@Path("movie_id") movie_id:Int):Call<YoutubeVideo>

}