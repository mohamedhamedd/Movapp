package com.moapps.moviesnews.ui.watchtrailer

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.moapps.moviesnews.R
import com.moapps.moviesnews.databinding.ActivityWatchTrailerBinding
import com.moapps.moviesnews.utilis.Credentials.YOUTUBE_API_KEY
import maes.tech.intentanim.CustomIntent


class WatchTrailerActivity : YouTubeBaseActivity() {
    lateinit var youtubeKey:String
    lateinit var binding:ActivityWatchTrailerBinding
    private var onInitializedListener: YouTubePlayer.OnInitializedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_trailer)
        binding = ActivityWatchTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        youtubeKey = intent.getStringExtra("youtubekey").toString()

        onInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider,youTubePlayer: YouTubePlayer, b: Boolean) {
                youTubePlayer.loadVideo(youtubeKey)
            }
            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
            }
        }

        binding.youtubePlayer.initialize(YOUTUBE_API_KEY,onInitializedListener)

        binding.btnBackTrailer.setOnClickListener {
            finish()
        }

    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this,"right-to-left")
    }
}