package org.d3if2123.kalkulatorkalori.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KaloriDao {

    @Insert
    fun insert (kalori: KaloriEntity)

    @Query ("SELECT * FROM kalori ORDER BY id DESC")
    fun getLastKalori(): LiveData<List<KaloriEntity?>>
}