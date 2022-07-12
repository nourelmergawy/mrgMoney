package com.mrg.mrgmoney

import CoinListAdapter
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel

class MainActivity : AppCompatActivity() {
    lateinit var gainBtn : Button
    lateinit var spendBtn : Button
    lateinit var addMoney : EditText
    lateinit var total : TextView
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinListAdapter: CoinListAdapter
    lateinit var list : LiveData<List<Coin>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gainBtn = findViewById(R.id.gain_btn)
        spendBtn = findViewById(R.id.spend_btn)
        addMoney = findViewById(R.id.add_money)
        total = findViewById(R.id.total_money)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coinListAdapter = CoinListAdapter(this) { coin, i ->
            recyclerView.adapter = coinListAdapter
            coinViewModel = ViewModelProvider(this).get(coinViewModel::class.java)
           coinViewModel.getAllCoin()

            gainBtn.setOnClickListener(View.OnClickListener {
                var amount : String = addMoney.toString()
                insertDataToDataBase(amount.toInt(),"gain")
            })

        }

    }
    private fun insertDataToDataBase(amount : Int,type : String) {
        //val totalMoney: String = addMoney.getText().toString()
        //val totalMoneyInt : Int = Integer.parseInt(totalMoney)
        //total.setText(totalMoney)
        if(inputCheck(amount)){
            val coin = Coin(0,"fdfd",type,amount.toInt(),0)
            coinViewModel.addCoin(coin)

           // coinListAdapter.setCoins(list)
            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(this,"faild",Toast.LENGTH_SHORT)

        }

    }
    private fun inputCheck( money : Int): Boolean {
        return !(TextUtils.isEmpty(money.toString()))
    }
}


