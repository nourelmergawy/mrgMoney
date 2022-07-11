package com.mrg.mrgmoney.ViewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinViewModel (application: Application) : AndroidViewModel(application) {

   // val amount = List<String>()
    private var coinRepository = CoinRepository(application)
    private var coin: LiveData<List<Coin>>? = coinRepository.getAllCoin()

    fun addCoin(coin: Coin) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.addCoin(coin)
        }
    }
    fun deleteCoin(coin: Coin) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.deleteCoin(coin)
        }
    }
    fun getAllCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.getAllCoin()
        }
    }
}