package com.jbc7ag.apod.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://images-api.nasa.gov"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ImagesNasaApiService{

    @GET("search")
    fun getSearch(@Query("q") search: String,
                  @Query("media_type") type: String = "image",
                  @Query("year_start") startYear: String = "1920",
                  @Query("year_end") endYear: String = "2020"): Deferred<ImagesNasaProperty>
}

object  ImagesNasaApi {
    //create only once
    val retrofitServiceImages : ImagesNasaApiService by lazy {
        retrofit.create(ImagesNasaApiService::class.java)
    }
}

