package com.moapps.moviesnews.ui.fragments.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.moviesnews.R
import com.moapps.moviesnews.databinding.FragmentRegionBinding
import com.moapps.moviesnews.utilis.makeToast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class RegionFragment : Fragment(), AdapterView.OnItemSelectedListener, KodeinAware,RegionInterface {

    lateinit var viewModel: RegionViewModel
    lateinit var binding:FragmentRegionBinding
    override val kodein: Kodein by kodein()
    private val factory: RegionViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegionBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this,factory).get(RegionViewModel::class.java)
        viewModel.regionInterface = this

        binding.spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(context!!, R.array.countries, R.layout.custom_spinner_text).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        viewModel.region.observe(viewLifecycleOwner, Observer {
            regionMoviesRecycler(it)
        })

        return binding.root
    }

    private fun regionMoviesRecycler(region: String) {
        binding.rvNowPlayingRegion.layoutManager = LinearLayoutManager(context)
        val adapterNowPlaying = RegionAdapter(context!!)

        //Observe ViewModel to get data from repository
        viewModel.getNowPlayingRegion(region).observe(viewLifecycleOwner, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterNowPlaying.submitList(it)
            adapterNowPlaying.notifyDataSetChanged()
        })

        binding.rvNowPlayingRegion.adapter = adapterNowPlaying
    }

    //Get item selected from spinner
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedItem: String = p0?.getItemAtPosition(p2).toString().substring(0, 2)
        viewModel.region.value = selectedItem
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onRegionFailed(message: String) {
        context?.makeToast(message)
    }

}