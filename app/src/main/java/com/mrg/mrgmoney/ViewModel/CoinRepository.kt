package com.mrg.mrgmoney.ViewModel

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import com.mrg.mrgmoney.DataBase.CoinDao

class CoinRepository(application: Application) {

    private val coinDao: CoinDao?
    private var coin: LiveData<List<Coin>>? = null

    init {
        val coinBase = CoinBase.getCoinBase(application)
        coinDao = coinBase?.coinDao()
        coin = coinDao?.getAll()
    }


    @Suppress("RedundantSuspendModifier")
   @WorkerThread
    fun addCoin(coin: Coin){
        coinDao?.insertAll(coin)
    }
     fun deleteCoin(coin: Coin){
        coinDao?.delete(coin)
    }
     fun getAllCoin(): LiveData<List<Coin>>? {
        return coin
    }

}