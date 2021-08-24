package com.jbc7ag.apod.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jbc7ag.apod.R
import com.jbc7ag.apod.databinding.FragmentHomeBinding
import com.jbc7ag.apod.network.ApodProperty
import com.jbc7ag.apod.ui.details.DetailActivity


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(inflater)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel

        binding.discoveryGrid.adapter = HomeGridAdapter(HomeGridAdapter.OnClickListener {
            openDetailActivity(it.title, it.description, it.url, it.date);

        })

       homeViewModel.showApodDetails.observe(viewLifecycleOwner, Observer {
           val apodProperty = homeViewModel.apodProperty.value
           if (apodProperty != null) {
               openDetailActivity(apodProperty.title, apodProperty.description, apodProperty.url, apodProperty.date);
           }
        })

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun openDetailActivity(title: String?, description: String?, url: String?, date: String?){
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("title", title ?: "")
        intent.putExtra("content", description ?: "")
        intent.putExtra("url", url ?: "")
        intent.putExtra("date", date ?: "")
        startActivity(intent)
    }
}