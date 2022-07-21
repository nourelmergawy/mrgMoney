package com.mrg.mrgmoney.ViewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinViewModel (application: Application) : AndroidViewModel(application) {

    val  allCoins : LiveData<List<Coin>>
    val coinRepository : CoinRepository

    init{
        val dao = CoinBase.getCoinBase(application).coinDao()
        coinRepository = CoinRepository(dao)
        allCoins = coinRepository.allCoins

    }

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
//    fun getAllCoin() {
//        viewModelScope.launch(Dispatchers.IO) {
//            allCoins.postValue(coinRepository.allCoins())
//        }
//    }
//    fun getAllCoinObservers(): MutableLiveData<List<Coin>> {
//        return allCoins
//    }
//    fun getTotal() {
//        viewModelScope.launch(Dispatchers.IO) {
//             coinRepository.getTotal()
//        }
//    }
}