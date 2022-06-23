package org.d3if2123.kalkulatorkalori.ui.hitungKalori.About

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if2123.kalkulatorkalori.db.KaloriDao
import org.d3if2123.kalkulatorkalori.ui.hitungKalori.History.HistoryViewModel

class AboutViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return AboutViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ")
    }
}