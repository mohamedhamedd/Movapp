package com.moapps.moviesnews.ui.fragments.popularfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.moviesnews.databinding.FragmentPopularBinding
import com.moapps.moviesnews.utilis.makeToast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PopularFragment : Fragment(), KodeinAware,PopularInterface {

    lateinit var viewModel: PopularViewModel
    lateinit var binding:FragmentPopularBinding
    override val kodein: Kodein by kodein()
    private val factory: PopularViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this,factory).get(PopularViewModel::class.java)
        viewModel.popularInterface = this

        popularRecycler()

        return binding.root
    }

    private fun popularRecycler() {
        binding.rvPopular.layoutManager = LinearLayoutManager(context)
        val adapterNowPlaying = PopularAdapter(context!!)

        //Observe ViewModel to get data from repository
        viewModel.response?.observe(viewLifecycleOwner, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterNowPlaying.submitList(it)
            adapterNowPlaying.notifyDataSetChanged()
        })

        binding.rvPopular.adapter = adapterNowPlaying
    }

    override fun onPopularFailed(message: String) {
        context?.makeToast(message)
    }

}