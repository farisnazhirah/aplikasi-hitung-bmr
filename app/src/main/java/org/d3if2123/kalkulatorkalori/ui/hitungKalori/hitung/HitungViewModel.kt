package org.d3if2123.kalkulatorkalori.ui.hitungKalori

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2123.kalkulatorkalori.MainActivity
import org.d3if2123.kalkulatorkalori.db.KaloriDao
import org.d3if2123.kalkulatorkalori.db.KaloriEntity
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori
import org.d3if2123.kalkulatorkalori.model.hitungKalori
import org.d3if2123.kalkulatorkalori.network.UpdateWorker
import java.util.concurrent.TimeUnit

class HitungViewModel(private val db: KaloriDao): ViewModel() {

    private val hasilKalori = MutableLiveData<HasilKalori?>()
    private val navigasi = MutableLiveData<KategoriKalori?>()

    fun hitungBMR(berat: Float, tinggi: Float, usia: Float, isMale: Boolean) {

        val dataKalori = KaloriEntity(
            berat = berat,
            tinggi = tinggi,
            usia = usia,
            isMale = isMale
        )
        hasilKalori.value = dataKalori.hitungKalori()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataKalori)
            }
        }
    }

    fun getHasilKalori(): LiveData<HasilKalori?> = hasilKalori

    fun mulaiNavigasi() {
        navigasi.value = hasilKalori.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getNavigasi(): LiveData<KategoriKalori?> = navigasi

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(7, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}