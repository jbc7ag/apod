package com.jbc7ag.apod.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesNasaProperty(
    val collection: DataImages,
): Parcelable

@Parcelize
data class DataImages(
    val version: String?,
    val metadata: Metadata?,
    val href: String?,
    val items: List<Items>,
    @Json(name= "links")
    val pagination: List<Pagination>?,
): Parcelable

@Parcelize
data class Metadata(
    val total_hits: Int?

): Parcelable

@Parcelize
data class Items(
    val data: List<DataItems>,
    val href: String?,
    val links: List<Links>?,
): Parcelable

@Parcelize
data class DataItems(
    val nasa_id: String,
    val secondary_creator: String?,
    val description: String?,
    val title: String?,
    val keywords: Array<String>?,
    val center: String?,
    @Json(name = "date_created")
    val dataCreated: String?,
    @Json(name = "description_508")
    val description508: String?,
    @Json(name = "media_type")
    val mediaType: String?
): Parcelable

@Parcelize
data class Links(
    val render: String?,
    val href: String?,
    val rel: String?,
): Parcelable


@Parcelize
data class Pagination(
    val prompt: String?,
    val href: String?,
    val rel: String?,
): Parcelable
