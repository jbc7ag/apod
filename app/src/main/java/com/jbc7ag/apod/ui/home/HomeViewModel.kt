package com.jbc7ag.apod.ui.home

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbc7ag.apod.Utils
import com.jbc7ag.apod.network.*
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel : ViewModel() {

    //private val _randomDiscovery = listOf("venus","mars planet","mercury","jupiter","uranus","neptune","pluto","earth","moon","sun","milky way","andromeda","black hole","space","Hubble Space Telescope", "Telescope", "ovni", "supernova")

    private val _apodProperty = MutableLiveData<ApodProperty>()
    val apodProperty : LiveData<ApodProperty>
        get() = _apodProperty

    private val _readMore = MutableLiveData<Boolean>()
    val readMore : LiveData<Boolean>
    get() = _readMore

    private val _hasCopyRight = MutableLiveData<Boolean>()
    val hasCopyRight : LiveData<Boolean>
        get() = _hasCopyRight

   private val _discoveryList = MutableLiveData<List<ApodProperty>>()
   val discoveryList: LiveData<List<ApodProperty>>
    get() = _discoveryList

   private val _showApodDetails = MutableLiveData<Boolean>()
    val showApodDetails: LiveData<Boolean>
    get()= _showApodDetails



    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.IO )

    init {
        getApod()
        _readMore.value = false
        _showApodDetails.value = false
    }

    private fun getApod(){

        //Picture of the day
        coroutineScope.launch {

            val currentDate = Utils.getCurrentDate(-2)
            val getProperty = ApodApi.retrofitService.getApod(date = currentDate)

            try {

                withContext(Dispatchers.Main) {
                    val property = getProperty.await()
                    _apodProperty.value =  property
                    _hasCopyRight.value = property.copyright != null
                }

            }catch (e: Exception){
                Log.e("[HomeVM]","Error: ${e.localizedMessage}")

            }
        }

        // Discovery
       coroutineScope.launch {

            val getApodList = ApodApi.retrofitService.getApodList(count = 10)

            try {

                withContext(Dispatchers.Main) {
                    val listApod = getApodList.await()
                    _discoveryList.value = listApod
                }

            }catch (e: Exception){

                Log.e("[HomeVM]","Error: ${e.localizedMessage}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onReadMoreClick(){
        _readMore.value =  _readMore.value?.not() ?: false
    }

    fun onApodclick(){
        _showApodDetails.value = true
    }

}