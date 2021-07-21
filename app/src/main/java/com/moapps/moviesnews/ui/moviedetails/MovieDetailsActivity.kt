package com.moapps.moviesnews.ui.moviedetails

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.moviesnews.R
import com.moapps.moviesnews.databinding.ActivityMovieDetailsBinding
import com.moapps.moviesnews.ui.watchtrailer.WatchTrailerActivity
import com.moapps.moviesnews.utilis.Credentials
import com.moapps.moviesnews.utilis.makeToast
import kotlinx.android.synthetic.main.activity_movie_details.*
import maes.tech.intentanim.CustomIntent
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MovieDetailsActivity : AppCompatActivity(), KodeinAware,MovieDetailsInterface {
    lateinit var viewModel: MovieDetailsViewModel
    lateinit var binding: ActivityMovieDetailsBinding
    lateinit var movieId: String
    lateinit var youtubeId: String
    override val kodein: Kodein by kodein()
    private val factory: MovieDetailsViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel::class.java)
        viewModel.movieDetailsInterface = this
        setSupportActionBar(binding.toolbarDetails)
        title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_icon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        movieId = intent.getStringExtra("movieid").toString()

        setDetails()
        getCast()
        getRecommendations()
        watchTrailer()


    }

    private fun watchTrailer() {
        binding.btnWatchTrailer.setOnClickListener {
            viewModel.getYoutubeKey(movieId.toInt()).observe(this, Observer {
                val intent = Intent(this, WatchTrailerActivity::class.java)
                    intent.putExtra("movieid", movieId)
                try {
                    intent.putExtra("youtubekey", it[0].key)//BdJKm16Co6M
                }catch (e:Exception){
                    Toast.makeText(this,"No Traiiler",Toast.LENGTH_LONG).show()
                }
                    startActivity(intent)
                    CustomIntent.customType(this, "left-to-right")
            })
        }

    }

    private fun setDetails() {
        viewModel.movieId.value = movieId.toInt()
        viewModel.movieId.observe(this, Observer {
            viewModel.getMovieDetails(movieId.toInt()).observe(this, Observer { details ->
                val imgLink: String
                if (details.backdrop_path == null){
                    if (details.poster_path == null){
                        imgLink = "https://wtwp.com/wp-content/uploads/2015/06/placeholder-image.png"
                    }else{
                        imgLink = Credentials.BASE_IMAGE_URL + details.poster_path
                    }
                }
                else{
                    imgLink = Credentials.BASE_IMAGE_URL + details.backdrop_path
                }

                Glide.with(this).load(imgLink).listener(
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
                            progress_details.visibility = View.GONE
                            binding.loadingFrame.visibility = View.GONE
                            return false
                        }

                    }).into(binding.backdropDetails)

                title_details.text = details.title
                rate_details.text = details.vote_average.toString()
                date_details.text = details.release_date
                vote_count_details.text = details.vote_count.toString()
                budget_details.text = details.budget.toString()
                language_details.text = details.original_language
                overview_details.text = details.overview
                revenue_details.text = details.revenue.toString()
                youtubeId = details.video.toString()

            })
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "right-to-left")
    }

    private fun getCast() {
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCast.layoutManager = mLayoutManager
        val adapterCast = CastCardAdapter(this)

        //Observe ViewModel to get data from repository
        viewModel.getCast(movieId.toInt())?.observe(this, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterCast.setData(it)
            adapterCast.notifyDataSetChanged()
        })

        binding.rvCast.adapter = adapterCast
    }

    private fun getRecommendations() {
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvRecommendations.layoutManager = mLayoutManager
        val adapterReco = com.moapps.moviesnews.ui.moviedetails.MoviesCardAdapter(this)

        //Observe ViewModel to get data from repository
        viewModel.getRecommendation(movieId.toInt())?.observe(this, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterReco.submitList(it)
            adapterReco.notifyDataSetChanged()
        })

        binding.rvRecommendations.adapter = adapterReco
    }

    override fun onMovieDetailsFailed(message: String) {
        makeToast(message)
    }

}