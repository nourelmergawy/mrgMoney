package com.mrg.mrgmoney

import CoinListAdapter
import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , CoinListAdapter.DeleteInterface {
    lateinit var gainBtn : Button
    lateinit var spendBtn : Button
    lateinit var addMoney : EditText
    lateinit var tvTotal : TextView
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinListAdapter: CoinListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gainBtn = findViewById(R.id.gain_btn)
        spendBtn = findViewById(R.id.spend_btn)
        addMoney = findViewById(R.id.add_money)
        tvTotal = findViewById(R.id.total_money)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Coin>()
        //data.addAll(viewModal.allNotes)

        // This will pass the ArrayList to our Adapter
        val adapter = CoinListAdapter(data,this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // on below line we are
        // initializing our view modal.
        coinViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CoinViewModel::class.java)
        coinViewModel.allCoins.observe(this, Observer { list ->
            list?.let {
                Log.d(TAG, "getTotal: ${coinViewModel.allCoins.value?.lastIndex}")
                if(getTotal("gain",0) == -1){
                    tvTotal.setText("0")

                }else{
                    tvTotal.setText(getTotal("gain",0).toString())

                }
                gainBtn.setOnClickListener(View.OnClickListener {
                    var amount = addMoney.text.toString().toInt()
                        insertDataToDataBase(amount,"gain")
                })
                spendBtn.setOnClickListener(View.OnClickListener {
                    var amount = addMoney.text.toString().toInt()
                    insertDataToDataBase(amount,"spend")
                })
                adapter.updateList(it)
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertDataToDataBase(amount: Int, type: String) {

        Log.d(TAG, "insertDataToDataBase: ${amount} - ${type} - ${getTotal(type,amount)}")
        if(inputCheck(amount.toString())){
            if(getTotal(type,amount) == -1 ){
                if (type == "spend"){
                    coinViewModel.addCoin(Coin(0, getDate(),type,amount,-amount))

                }else{
                    coinViewModel.addCoin(Coin(0, getDate(),type,amount,amount))

                }
                Log.d(TAG, "insertDataToDataBase: date = ${ LocalDateTime.now()}")

            }else{
                coinViewModel.addCoin(Coin(0, getDate(),type,amount,getTotal(type,amount)))
            }
            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(this,"faild",Toast.LENGTH_SHORT)

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTotal(type: String, amount: Int): Int? {

        Log.d(TAG, "getTotal: ${coinViewModel.allCoins.value?.lastIndex}")
        if (coinViewModel.allCoins.value?.lastIndex == -1 ){
            return -1
        }else{
            var index = coinViewModel.allCoins.value?.lastIndex
            var total : Int? = coinViewModel.allCoins.value?.get(index!!)?.total
            if (total != null) {
                if (type == "gain"){
                    total += amount
                    return total
                }else if (type == "spend"){
                    total -= amount
                    return total
                }
            }
        }
        return 0
    }
    private fun inputCheck( money : String): Boolean {
        return !(TextUtils.isEmpty(money))
    }
    override fun onDelete(coin: Coin) {
        coinViewModel.deleteCoin(coin)
        Toast.makeText(this,"Coin deleted successfully", Toast.LENGTH_LONG).show()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun getDate():String{
        val simpleDate = SimpleDateFormat("dd/M/ hh:mm a")
        val currentDate = simpleDate.format(Date())
        return currentDate
    }
}