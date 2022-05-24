package org.d3if2123.kalkulatorkalori.ui.hitungKalori.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if2123.kalkulatorkalori.db.KaloriDao

class HistoryViewModelFactory(private val db: KaloriDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ")
    }
}