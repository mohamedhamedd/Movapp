package com.moapps.moviesnews.ui.fragments.moviesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.moviesnews.databinding.FragmentMoviesBinding
import com.moapps.moviesnews.utilis.makeToast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MoviesFragment : Fragment(), KodeinAware,MoviesInterface {

    lateinit var viewModel: MoviesViewModel
    lateinit var binding:FragmentMoviesBinding
    override val kodein: Kodein by kodein()
    private val factory: MovieViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this,factory).get(MoviesViewModel::class.java)
        viewModel.moviesInterface = this
        playingNow()
        topRated()
        popular()

        return binding.root
    }

    private fun playingNow(){
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvNowPlayingHome.layoutManager = mLayoutManager
        val adapterNowPlaying = MoviesNowPlayingAdapter(context!!)

        //Observe ViewModel to get data from repository
        viewModel.nowPlayingLive?.observe(viewLifecycleOwner, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterNowPlaying.submitList(it)
            adapterNowPlaying.notifyDataSetChanged()
        })

        binding.rvNowPlayingHome.adapter = adapterNowPlaying

    }

    private fun topRated(){
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvTopRatedHome.layoutManager = mLayoutManager
        val adapterTopRated = MoviesCardAdapter(context!!)

        //Observe ViewModel to get data from repository
        viewModel.topRatedLive?.observe(viewLifecycleOwner, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterTopRated.submitList(it)
            adapterTopRated.notifyDataSetChanged()
        })

        binding.rvTopRatedHome.adapter = adapterTopRated
    }

    private fun popular(){
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvPopularMoviesHome.layoutManager = mLayoutManager
        val adapterPopular = MoviesCardAdapter(context!!)

        //Observe ViewModel to get data from repository
        viewModel.upcomingLive?.observe(viewLifecycleOwner, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterPopular.submitList(it)
            adapterPopular.notifyDataSetChanged()
        })

        binding.rvPopularMoviesHome.adapter = adapterPopular

    }

    override fun onError(message: String) {
        context?.makeToast(message)
    }

}