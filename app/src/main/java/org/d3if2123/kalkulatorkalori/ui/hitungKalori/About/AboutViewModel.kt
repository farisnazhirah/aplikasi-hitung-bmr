package org.d3if2123.kalkulatorkalori.ui.hitungKalori.About

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2123.kalkulatorkalori.model.Tentang
import org.d3if2123.kalkulatorkalori.network.ApiStatus
import org.d3if2123.kalkulatorkalori.network.KaloriApi

class AboutViewModel(private val application: Application) : ViewModel() {

    private val data = MutableLiveData<Tentang>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(KaloriApi.service.getTentangAplikasi())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<Tentang> = data

    fun getStatus(): LiveData<ApiStatus> = status
}