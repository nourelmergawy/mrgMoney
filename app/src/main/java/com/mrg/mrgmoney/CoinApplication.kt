package com.mrg.mrgmoney

import android.app.Application
import com.mrg.mrgmoney.DataBase.CoinBase
import com.mrg.mrgmoney.ViewModel.CoinRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CoinApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CoinBase.getCoinBase(this,applicationScope) }
    val repository by lazy { CoinRepository(database.coinDao()) }
}