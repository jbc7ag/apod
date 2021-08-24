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

package com.jbc7ag.apod.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jbc7ag.apod.databinding.HomeDiscoveryItemBinding
import com.jbc7ag.apod.databinding.HomeSearchItemBinding
import com.jbc7ag.apod.network.Items


class SearchAdapter(private val onClickListener: OnClickListener): ListAdapter<Items, SearchAdapter.PropertyViewHolder>(DiffCallback){

    class PropertyViewHolder(private var binding: HomeSearchItemBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItems: Items) {
            binding.property = dataItems
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.data[0].nasa_id == newItem.data[0].nasa_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        return PropertyViewHolder(HomeSearchItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val dataItems = getItem(position)
        holder.bind(dataItems)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(dataItems)
        }


    }

    class OnClickListener(val clickListener: (dataItems: Items) -> Unit) {
        fun onClick(dataItems: Items) = clickListener(dataItems)
    }

}