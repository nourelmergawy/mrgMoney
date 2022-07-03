package com.mrg.mrgmoney.ViewModel

import androidx.lifecycle.*
import com.mrg.mrgmoney.DataBase.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinViewModel(private val repository: CoinRepository) : ViewModel() {
     val readAllDAta: LiveData<List<Coin>> = repository.readAllDAta

    fun addCoin(coin: Coin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoin(coin)
        }
    }
}

class CoinViewModelFactory(private val repository: CoinRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}