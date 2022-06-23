package org.d3if2123.kalkulatorkalori.ui.hitungKalori.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if2123.kalkulatorkalori.db.KaloriDao
import org.d3if2123.kalkulatorkalori.ui.hitungKalori.HitungViewModel

class HitungViewModelFactory(private val db: KaloriDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HitungViewModel::class.java)) {
            return HitungViewModel(db) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}