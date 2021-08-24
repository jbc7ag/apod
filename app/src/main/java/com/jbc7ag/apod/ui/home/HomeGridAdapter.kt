/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jbc7ag.apod.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jbc7ag.apod.databinding.HomeDiscoveryItemBinding
import com.jbc7ag.apod.network.ApodProperty

class HomeGridAdapter(private val onClickListener: OnClickListener): ListAdapter<ApodProperty, HomeGridAdapter.PropertyViewHolder>(DiffCallback){

    class PropertyViewHolder(private var binding: HomeDiscoveryItemBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItems: ApodProperty) {
            binding.property = dataItems
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ApodProperty>() {
        override fun areItemsTheSame(oldItem: ApodProperty, newItem: ApodProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ApodProperty, newItem: ApodProperty): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        return PropertyViewHolder(HomeDiscoveryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val dataItems = getItem(position)
        holder.bind(dataItems)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(dataItems)
        }

    }

    class OnClickListener(val clickListener: (dataItems: ApodProperty) -> Unit) {
        fun onClick(dataItems: ApodProperty) = clickListener(dataItems)
    }

}