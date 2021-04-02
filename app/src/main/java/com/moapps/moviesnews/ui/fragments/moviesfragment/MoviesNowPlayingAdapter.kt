package com.moapps.moviesnews.ui.fragments.moviesfragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.moviesnews.R
import com.moapps.moviesnews.pojo.Results
import com.moapps.moviesnews.ui.moviedetails.MovieDetailsActivity
import com.moapps.moviesnews.utilis.Credentials
import com.startapp.sdk.adsbase.StartAppAd
import io.armcha.elasticview.ElasticView
import kotlinx.android.synthetic.main.row_movie_card_full.view.*
import maes.tech.intentanim.CustomIntent

class MoviesNowPlayingAdapter(var context: Context):PagedListAdapter<Results, MoviesNowPlayingAdapter.HomeViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_movie_card_full, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val results = getItem(position)

        if (results?.title?.length!! > 40) {
            holder.title.text = results.title!!.substring(0, 37) + ".."
        } else {
            holder.title.text = results.title
        }
        holder.rate.text = results.vote_average.toString()

        val imgUrl:String

        if (results.backdrop_path.isNullOrEmpty()) {
            imgUrl = results.poster_path
        }else{
            imgUrl = results.backdrop_path
        }

        Glide.with(context).load(Credentials.BASE_IMAGE_URL + imgUrl).listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = View.GONE
                    return false
                }

            }).into(holder.img)

        holder.mLayout.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            Log.d(TAG,"the id is:${results.id}")
            intent.putExtra("movieid",results.id.toString())
            context.startActivity(intent)
            CustomIntent.customType(context,"left-to-right")
        }
    }

    class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var img: ImageView = itemView.row_image_movie_card_latest
        var progress: ProgressBar = itemView.row_progress_movie_card_latest
        var title: TextView = itemView.row_title_movie_card_latest
        var rate: TextView = itemView.row_rate_movie_card_latest
        var mLayout: ElasticView = itemView.row_layout_movie_card_full
    }

    companion object{

        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
                oldItem == newItem
        }

    }


}