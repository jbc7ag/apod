package com.jbc7ag.apod.ui.details

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import com.jbc7ag.apod.databinding.ActivityDetailBinding
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.Window
import com.jbc7ag.apod.R
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions





class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.title = intent.getStringExtra("title").toString()
        binding.url = intent.getStringExtra("date").toString()
        binding.content = intent.getStringExtra("content").toString()
        binding.date = intent.getStringExtra("date").toString()
        binding.url =  intent.getStringExtra("url").toString()

        val inflater = this.layoutInflater

        val dialogView: View = inflater.inflate(R.layout.dialog_photo_view, null )
        val customDialog = Dialog(this, android.R.style.Theme_Light)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val imageView = dialogView.findViewById<ImageView>(R.id.photo_view)
        val closeDialog = dialogView.findViewById<ImageView>(R.id.close_dialog)

        Glide.with(applicationContext)
            .load(binding.url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)

        customDialog.setContentView(dialogView)

        binding.imgPictureDay.setOnClickListener{
            customDialog.show()
        }
        closeDialog.setOnClickListener{
            customDialog.dismiss()
        }
        binding.back.setOnClickListener{
            super.onBackPressed();
        }
    }
}