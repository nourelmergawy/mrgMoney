package com.mrg.mrgmoney

import CoinListAdapter
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.DataBase.CoinBase
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.ViewModel.CoinViewModelFactory
import kotlinx.coroutines.CoroutineScope
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var gainBtn : Button
    lateinit var spendBtn : Button
    lateinit var addMoney : EditText
    lateinit var total : TextView
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var itemsList: List<Coin>
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var observer : LiveData<CoinViewModel>
        gainBtn = findViewById(R.id.gain_btn)
        spendBtn = findViewById(R.id.spend_btn)
        addMoney = findViewById(R.id.add_money)
        total = findViewById(R.id.total_money)
        coinViewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        gainBtn.setOnClickListener(View.OnClickListener {
            insertDataToDataBase("gain")
        })
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CoinListAdapter(itemsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun insertDataToDataBase(type : String) {
        val totalMoney: String = addMoney.getText().toString()
        val totalMoneyInt : Int = Integer.parseInt(totalMoney)
        total.setText(totalMoney)
        if(inputCheck(totalMoney)){
            val coin = Coin(0,"fdfd",type,totalMoneyInt,0)
            coinViewModel.addCoin(coin)
            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT)
        }else{
           Toast.makeText(this,"faild",Toast.LENGTH_SHORT)

        }

    }
    private fun inputCheck( money : String): Boolean {
        return !(TextUtils.isEmpty(money))
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getdate(): String{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)
        return currentDate;
    }
    private val coinViewModels: CoinViewModel by viewModels {
        CoinViewModelFactory((application as CoinApplication).repository)
    }

}