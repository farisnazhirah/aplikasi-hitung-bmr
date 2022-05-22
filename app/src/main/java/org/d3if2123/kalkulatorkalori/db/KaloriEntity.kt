package org.d3if2123.kalkulatorkalori.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kalori")
data class KaloriEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal:Long = System.currentTimeMillis(),
    var berat: Float,
    var tinggi: Float,
    var usia: Float,
    var isMale: Boolean
)