package org.d3if2123.kalkulatorkalori

import androidx.lifecycle.ViewModel
import org.d3if2123.kalkulatorkalori.model.HasilKalori
import org.d3if2123.kalkulatorkalori.model.KategoriKalori

class MainViewModel: ViewModel() {

    fun hitungBMR(berat: Float, tinggi: Float, usia: Float, isMale: Boolean): HasilKalori {
        val bmr = if (isMale) {
            66 + (13.7 * berat) + (5 * tinggi) - (6.8 * usia)
        } else {
            655 + (9.6 * berat) + (1.8 * tinggi) - (4.7 * usia)
        }
        val kategori = getKategori(bmr, isMale)
        return HasilKalori(bmr, kategori)
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
}