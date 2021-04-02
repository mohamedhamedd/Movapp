package com.moapps.moviesnews.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.moapps.moviesnews.R
import com.moapps.moviesnews.databinding.ActivityDashboardBinding
import com.moapps.moviesnews.ui.fragments.moviesfragment.MoviesFragment
import com.moapps.moviesnews.ui.fragments.region.RegionFragment
import com.moapps.moviesnews.ui.fragments.popularfragment.PopularFragment
import com.moapps.moviesnews.ui.search.SearchActivity
import com.startapp.sdk.ads.splash.SplashConfig
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import kotlinx.android.synthetic.main.activity_dashboard.*
import maes.tech.intentanim.CustomIntent
import java.util.ArrayList

class DashboardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(MoviesFragment(), "Home")
        viewPagerAdapter.addFragment(RegionFragment(), "Region")
        viewPagerAdapter.addFragment(PopularFragment(), "Popular")
        binding.viewPager.adapter = viewPagerAdapter

        getSearchText()

    }

    private fun getSearchText(){
        binding.searchBtn.setOnClickListener {
            val txt = binding.searchText.text.trim().toString()
            if (!txt.isNullOrEmpty()){
                val intent = Intent(this,SearchActivity::class.java)
                intent.putExtra("txt",txt)
                startActivity(intent)
                CustomIntent.customType(this,"left-to-right")
            }
        }

    }

    class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
            FragmentPagerAdapter(fm, behavior) {
        private val fragments: MutableList<Fragment> = ArrayList()
        private val fragmentstitle: MutableList<String> = ArrayList()
        fun addFragment(fragment: Fragment, titleTab: String) {
            fragments.add(fragment)
            fragmentstitle.add(titleTab)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return fragmentstitle[position]
        }

    }

}