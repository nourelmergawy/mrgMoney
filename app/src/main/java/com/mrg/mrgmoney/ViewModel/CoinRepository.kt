package com.mrg.mrgmoney.ViewModel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinDao

class CoinRepository(private val coinDao: CoinDao) {

val readAllDAta : LiveData<List<Coin>> = coinDao. getAll()
   @Suppress("RedundantSuspendModifier")
   @WorkerThread
   suspend fun addCoin(coin: Coin){
        coinDao.insertAll(coin)
    }
}