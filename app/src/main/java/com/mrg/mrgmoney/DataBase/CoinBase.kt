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
        fun getCoinBase(context: Context ,
                        scope: CoroutineScope
        ) : CoinBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinBase::class.java,
                    "coin_database"
                )
                .addCallback(CoinDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                // return instance
                instance
            }

    }
    }
    private class CoinDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.coinDao())
                }
            }
        }

        suspend fun populateDatabase(coinDao: CoinDao) {
            // Delete all content here.
            coinDao.deleteAll()

            // Add sample words.
            val coin = Coin(0,"fdfd","add",0,0)
            // TODO: Add your own words!
        }
    }
}
