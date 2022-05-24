package org.d3if2123.kalkulatorkalori.model

import org.d3if2123.kalkulatorkalori.db.KaloriEntity

fun KaloriEntity.hitungKalori(): HasilKalori {
    val bmr = if (isMale) {
        66 + (13.7 * berat) + (5 * tinggi) - (6.8 * usia)
    } else {
        655 + (9.6 * berat) + (1.8 * tinggi) - (4.7 * usia)
    }

    val kategori = if (isMale) {
        when {
            bmr < 2500 -> KategoriKalori.RENDAH
            else -> KategoriKalori.TINGGI
        }
    } else {
        when {
            bmr < 2000 -> KategoriKalori.RENDAH
            else -> KategoriKalori.TINGGI
        }
    }
    return HasilKalori(bmr, kategori)
}