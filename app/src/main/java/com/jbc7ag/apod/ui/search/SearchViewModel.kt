package com.jbc7ag.apod.ui.search

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbc7ag.apod.Utils
import com.jbc7ag.apod.network.ApodApi
import com.jbc7ag.apod.network.ApodProperty
import com.jbc7ag.apod.network.ImagesNasaApi
import com.jbc7ag.apod.network.Items
import kotlinx.coroutines.*
import java.lang.Exception

class SearchViewModel : ViewModel() {


    private val _searchList = MutableLiveData<List<Items>>()
    val searchList: LiveData<List<Items>>
    get() = _searchList

    private val _searchTerm = MutableLiveData<String>()
    val searchTerm: LiveData<String>
        get() = _searchTerm

    private val _apodProperty = MutableLiveData<ApodProperty>()
    val apodProperty : LiveData<ApodProperty>
        get() = _apodProperty

    private val _openDateSelector = MutableLiveData<Boolean>()
    val openDateSelector: LiveData<Boolean>
        get() = _openDateSelector

    private val _showStartImage = MutableLiveData<Int>()
    val showStartImage: LiveData<Int>
        get() = _showStartImage


    private val _showApod = MutableLiveData<Int>()
    val showApod: LiveData<Int>
        get() = _showApod

    private val _showImageRv = MutableLiveData<Int>()
    val showImageRv: LiveData<Int>
        get()= _showImageRv

    private val _showApodDetails = MutableLiveData<Boolean>()
    val showApodDetails: LiveData<Boolean>
        get()= _showApodDetails



    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.IO )

    init {
        _showStartImage.value = View.VISIBLE
        _showApod.value = View.GONE
    }


    fun afterUserNameChange(term: CharSequence){
        _searchTerm.value = term.toString();
        Log.i("[SearchVM]", "value "+ _searchTerm.value)
    }

    fun searchByTerm(term: String){

        _searchTerm.value = term
        _showApod.value = View.GONE

        if(_searchTerm.value.isNullOrEmpty()){
            _searchList.value = listOf()
            _showStartImage.value = View.VISIBLE
        }
        else{
            _showStartImage.value = View.INVISIBLE


            coroutineScope.launch {

                val geImagesProperty = ImagesNasaApi.retrofitServiceImages.getSearch(search = term)

                try {

                    withContext(Dispatchers.Main) {
                        val listImages = geImagesProperty.await()
                        val items = listImages.collection.items

                        if(!items.isNullOrEmpty()) {
                            _searchList.value = items
                            _showImageRv.value = View.VISIBLE

                        } else {
                            _searchList.value = listOf()
                            _showStartImage.value = View.VISIBLE

                        }

                    }

                } catch (e: Exception) {

                    Log.e("[SearchVM]", "Error: ${e.localizedMessage}")
                    withContext(Dispatchers.Main) {
                        _showImageRv.value = View.GONE
                        _showStartImage.value = View.VISIBLE
                    }
                }
            }
        }

    }

    fun searchByDate(day: String, month: String, year: String){
        coroutineScope.launch {

            val getProperty = ApodApi.retrofitService.getApod(date = "$year-$month-$day")

            try {

                withContext(Dispatchers.Main) {
                    val property = getProperty.await()
                    _apodProperty.value =  property
                    _showApod.value = View.VISIBLE
                    _showStartImage.value = View.INVISIBLE
                    _showImageRv.value = View.INVISIBLE


                }

            }catch (e: Exception){
                Log.e("[HomeVM]","Error: ${e.localizedMessage}")

                withContext(Dispatchers.Main) {
                    _showApod.value = View.GONE
                    _showImageRv.value = View.GONE
                    _showStartImage.value = View.VISIBLE
                }

            }
        }
    }

    fun openDateDialog(){
        _openDateSelector.value =  _openDateSelector.value?.not();
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onApodclick(){
        _showApodDetails.value = _showApodDetails.value?.not()
    }
}