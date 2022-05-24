package org.d3if2123.kalkulatorkalori.ui.hitungKalori.History

import androidx.lifecycle.ViewModel
import org.d3if2123.kalkulatorkalori.db.KaloriDao

class HistoryViewModel(db: KaloriDao) : ViewModel() {
    val data = db.getLastKalori()
}