package com.jbc7ag.apod.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApodProperty(
    val copyright: String?,
    val date: String,
    @Json(name= "explanation")
    val description: String,
    val hdurl: String?,
    @Json(name= "media_type")
    val type: String,
    @Json(name= "service_version")
    val version: String,
    val title: String,
    val url: String
): Parcelable

