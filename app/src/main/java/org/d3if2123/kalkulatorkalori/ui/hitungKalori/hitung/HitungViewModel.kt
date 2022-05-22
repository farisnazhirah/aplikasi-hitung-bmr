package org.d3if2123.kalkulatorkalori.ui.hitungKalori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2123.kalkulatorkalori.db.KaloriDao
import org.d3if2123.kalkulatorkalori.db.KaloriEntity
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class MainViewModel(private val db: KaloriDao): ViewModel() {

    private val hasilKalori = MutableLiveData<HasilKalori?>()
    private val navigasi = MutableLiveData<KategoriKalori?>()

    val data = db.getLastKalori()

    fun hitungBMR(berat: Float, tinggi: Float, usia: Float, isMale: Boolean) {
        val bmr = if (isMale) {
            66 + (13.7 * berat) + (5 * tinggi) - (6.8 * usia)
        } else {
            655 + (9.6 * berat) + (1.8 * tinggi) - (4.7 * usia)
        }
        val kategori = getKategori(bmr, isMale)
        hasilKalori.value = HasilKalori(bmr, kategori)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataKalori = KaloriEntity(
                    berat = berat,
                    tinggi = tinggi,
                    usia = usia,
                    isMale = isMale
                )
                db.insert(dataKalori)
            }
        }
    }

    fun getKategori(kalori: Double, isMale: Boolean): KategoriKalori {
        val kategori = if (isMale) {
            when {
                kalori < 2500 -> KategoriKalori.RENDAH
                else -> KategoriKalori.TINGGI
            }
        } else {
            when {
                kalori < 2000 -> KategoriKalori.RENDAH
                else -> KategoriKalori.TINGGI
            }
        }
        return kategori
    }

    fun getHasilKalori(): LiveData<HasilKalori?> = hasilKalori

    fun mulaiNavigasi() {
        navigasi.value = hasilKalori.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getNavigasi(): LiveData<KategoriKalori?> = navigasi
}