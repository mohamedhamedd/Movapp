package com.moapps.moviesnews.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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
import maes.tech.intentanim.CustomIntent

class SearchAdapter(var context: Context):PagedListAdapter<Results, SearchAdapter.HomeViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_movie_card_details, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val results = getItem(position)

        if (results?.title?.length!! > 17) {
            holder.title.text = results.title!!.substring(0, 15) + ".."
        } else {
            holder.title.text = results.title
        }

        if (results.overview.length > 100){
            holder.overView.text = results.overview!!.substring(0, 100) + ".."
        } else {
            holder.overView.text = results.title
        }

        holder.rate.text = results.vote_average.toString()
        Glide.with(context).load(Credentials.BASE_IMAGE_URL + results.poster_path).listener(
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
            intent.putExtra("movieid",results.id.toString())
            context.startActivity(intent)
            CustomIntent.customType(context,"left-to-right")
        }

        holder.mlayout2.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movieid",results.id.toString())
            context.startActivity(intent)
            CustomIntent.customType(context,"left-to-right")
        }
    }

    class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.row_image_movie_card_details)
        var progress = itemView.findViewById<ProgressBar>(R.id.row_progress_movie_card_details)
        var title = itemView.findViewById<TextView>(R.id.row_title_movie_card_details)
        var rate = itemView.findViewById<TextView>(R.id.row_rate_movie_card_details)
        var overView = itemView.findViewById<TextView>(R.id.row_overview_movie_card_details)
        var mLayout = itemView.findViewById<LinearLayout>(R.id.row_layout_movie_card_details)
        var mlayout2 = itemView.findViewById<ElasticView>(R.id.row_layout2_movie_card_details)
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