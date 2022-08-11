package com.mrg.mrgmoney.ViewModel

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import com.mrg.mrgmoney.DataBase.CoinDao

class CoinRepository(private val coinDao: CoinDao) {

    val allCoins: LiveData<List<Coin>> = coinDao.getAll()

    suspend fun addCoin(coin: Coin){
        coinDao?.insertAll(coin)
    }
    suspend fun deleteCoin(coin: Coin){
        coinDao?.delete(coin)
    }


}