package com.mrg.mrgmoney.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Coin::class], version = 1)
abstract class CoinBase : RoomDatabase() {
    abstract fun coinDao(): CoinDao


    companion object{
        @Volatile
        private var INSTANCE : CoinBase? = null
        fun getCoinBase(context: Context ) : CoinBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinBase::class.java,
                    "coin_database"
                )
                .build()
                INSTANCE = instance
                return instance
                //instance
            }

    }

    }
}
