package com.mrg.mrgmoney.ViewModel

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

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
//           var total = allCoins.value?.get(0)?.total?.plus(coin.amount!!)
//            updateById(total!!,allCoins.value?.get(0)?.uid!!)
            coinRepository.deleteCoin(coin)
        }
    }
    fun getTotal(type: String, amount: Int): Int? {
        Log.d(ContentValues.TAG, "getTotal: ${allCoins.value?.lastIndex}")
        var total : Int? =0
        if (allCoins.value?.lastIndex == -1 ){
            return -1
        }else {
//            var index = allCoins.value?.lastIndex
            total = allCoins.value?.get(0)?.total?.toInt()
            if (total != 0) {
                if (type == "gain") {
                    total = total?.plus(amount)
                    Log.d(TAG, "getTotal: ${total}")
                    return total
                } else if (type == "spend") {
                    total = total?.minus(amount)
                    Log.d(TAG, "getTotal: ${total}")

                    return total
                }
            }else{
                if (type == "gain") {
                    total += amount
                    Log.d(TAG, "getTotal: ${total}")
                    return total
                } else if (type == "spend") {
                    total -= amount
                    Log.d(TAG, "getTotal: ${total}")

                    return total
                }
            }
        }
        return total
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertDataToDataBase(amount: Int, type: String, context: Context) {
        Log.d(TAG, "insertDataToDataBase: ${amount} - ${type} - ${getTotal(type,amount)}")

        if(amount > 0){
            if(getTotal(type,amount) == -1 ){
                if (type == "spend"){
                    Toast.makeText(context,"spend",Toast.LENGTH_SHORT)
                    addCoin(Coin(0, getDate(),type,amount,-amount))

                }else{
                   addCoin(Coin(0, getDate(),type,amount,amount))

                }
                Log.d(TAG, "insertDataToDataBase: date = ${ LocalDateTime.now()}")

            }else if (getTotal(type,amount)!! >= 0){
              addCoin(Coin(0, getDate(),type,amount,getTotal(type,amount)))
            }
            Toast.makeText(context,"successfully",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(context,"faild",Toast.LENGTH_SHORT)

        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun getDate():String{
        val simpleDate = SimpleDateFormat("dd/M hh:mm a")
        val currentDate = simpleDate.format(Date())
        return currentDate
    }
    fun updateById(amount: Int,id :Int){
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.updateById(amount,id)
        }
    }
}