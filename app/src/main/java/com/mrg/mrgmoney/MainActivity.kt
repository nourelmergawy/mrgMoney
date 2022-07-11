package com.mrg.mrgmoney

import CoinListAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var gainBtn : Button
    lateinit var spendBtn : Button
    lateinit var addMoney : EditText
    lateinit var total : TextView
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinListAdapter: CoinListAdapter
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
           var it :LiveData<List<Coin>>
           println(coinViewModel.getAllCoin())
           //coinListAdapter.setCoins()



        }

    }
}