package org.d3if2123.kalkulatorkalori.ui.hitungKalori.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import org.d3if2123.kalkulatorkalori.db.KaloriDao
import org.d3if2123.kalkulatorkalori.db.KaloriEntity

class HistoryViewModel(private val db: KaloriDao) : ViewModel() {
    val data = db.getLastKalori()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

    fun hapusHistori(kaloriEntity: KaloriEntity) {
        viewModelScope.launch {
            Dispatchers.IO {
                db.delete(kaloriEntity)
            }
        }
    }

}

