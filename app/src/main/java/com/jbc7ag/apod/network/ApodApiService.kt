package com.jbc7ag.apod.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.nasa.gov/planetary/"
private const val API_KEY = "DEMO_KEY"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApodApiService{

    @GET("apod")
    fun getApod(@Query("api_key") apiKey: String = API_KEY, @Query("date") date: String): Deferred<ApodProperty>

    @GET("apod")
    fun getApodList(@Query("api_key") apiKey: String = API_KEY, @Query("count") count: Int): Deferred<List<ApodProperty>>
}

object  ApodApi {
    //create only once
    val retrofitService : ApodApiService by lazy {
        retrofit.create(ApodApiService::class.java)
    }
}

