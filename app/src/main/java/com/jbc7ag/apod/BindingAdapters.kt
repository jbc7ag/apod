package com.jbc7ag.apod

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jbc7ag.apod.network.ApodProperty
import com.jbc7ag.apod.network.Items
import com.jbc7ag.apod.ui.home.HomeGridAdapter
import com.jbc7ag.apod.ui.search.SearchAdapter
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)

    }
}

@BindingAdapter("listDiscoveryData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ApodProperty>?) {
    val adapter = recyclerView.adapter as HomeGridAdapter
    adapter.submitList(data)
}


@BindingAdapter("listDataSearch")
fun bindRecyclerViewSearch(recyclerView: RecyclerView, data: List<Items>?) {
    val adapter = recyclerView.adapter as SearchAdapter
    adapter.submitList(data)
}

@BindingAdapter("formatDate")
fun bindDate(textView: TextView, date: String?){
    date?.let {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("MMM d, ''yy", Locale.ENGLISH)
        val output = formatter.format(parser.parse(date))

        textView.text = output.toString()

    }
}
