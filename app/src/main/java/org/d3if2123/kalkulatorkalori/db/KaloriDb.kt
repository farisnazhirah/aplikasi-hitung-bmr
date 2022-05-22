package org.d3if2123.kalkulatorkalori.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KaloriEntity::class], version = 1, exportSchema = false)
abstract class KaloriDb: RoomDatabase() {

    abstract val dao: KaloriDao

    companion object {

        @Volatile
        private var INSTANCE: KaloriDb? = null

        fun getInstance(context: Context): KaloriDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, KaloriDb::class.java, "kalori.db").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}