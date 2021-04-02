package com.moapps.moviesnews.ui.moviedetails

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.moviesnews.R
import com.moapps.moviesnews.pojo.Cast
import com.moapps.moviesnews.utilis.Credentials

class CastCardAdapter(var context: Context):RecyclerView.Adapter<CastCardAdapter.HomeViewHolder>() {

    private var datalist = listOf<Cast>()

    fun setData(list:List<Cast>){
        datalist = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_cast, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val results = datalist[position]

        if (results.name.length > 15) {
            holder.name.text = results.name!!.substring(0, 13) + ".."
        } else {
            holder.name.text = results.name
        }

        Glide.with(context).load(Credentials.BASE_IMAGE_URL + results.profile_path).listener(
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

    }

    class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.row_cast_image)
        var progress = itemView.findViewById<ProgressBar>(R.id.row_cast_progress)
        var name = itemView.findViewById<TextView>(R.id.row_cast_name)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}