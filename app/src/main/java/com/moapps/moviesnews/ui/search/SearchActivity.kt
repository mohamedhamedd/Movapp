package com.moapps.moviesnews.ui.search

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.moviesnews.R
import com.moapps.moviesnews.databinding.ActivitySearchBinding
import com.moapps.moviesnews.utilis.makeToast
import com.startapp.sdk.adsbase.StartAppSDK
import maes.tech.intentanim.CustomIntent
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SearchActivity : AppCompatActivity(), KodeinAware,SearchInterface {

    lateinit var viewModel: SearchViewModel
    lateinit var binding:ActivitySearchBinding
    override val kodein: Kodein by kodein()
    private val factory: SearchViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this,factory).get(SearchViewModel::class.java)
        viewModel.searchInterface = this
        setSupportActionBar(binding.toolbarSearch)
        title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_icon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getSearchText()
        anotherSearchText()

    }

    private fun getSearchText() {
        val txt = intent.getStringExtra("txt").toString()
        viewModel.txt.value = txt
        viewModel.txt.observe(this, Observer {
            binding.searchTextSearch.setText(it)
            searchRecyclerView(it)
        })

    }

    private fun anotherSearchText() {
        binding.searchBtnSearch.setOnClickListener {
            val txt = binding.searchTextSearch.text.trim().toString()
            if (!txt.isNullOrEmpty()){
                viewModel.txt.value = txt
                searchRecyclerView(txt)
            }
        }
    }

    private fun searchRecyclerView(search: String) {
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        val adapterSearch = SearchAdapter(this)

        //Observe ViewModel to get data from repository
        viewModel.getSearchResults(search)?.observe(this, Observer {
            //set List Data to adapter that come from repository to ViewModel
            adapterSearch.submitList(it)
            adapterSearch.notifyDataSetChanged()
        })

        binding.rvSearch.adapter = adapterSearch
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this,"right-to-left")
    }

    override fun onSearchFailed(message: String) {
        makeToast(message)
    }
}