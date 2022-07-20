package com.mrg.mrgmoney

import CoinListAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
    //lateinit var list : LiveData<List<Coin>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gainBtn = findViewById(R.id.gain_btn)
        spendBtn = findViewById(R.id.spend_btn)
        addMoney = findViewById(R.id.add_money)
        total = findViewById(R.id.total_money)
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Coin>()
        //data.addAll(viewModal.allNotes)

        // This will pass the ArrayList to our Adapter
        val adapter = CoinListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        // on below line we are
        // initializing our view modal.
        coinViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CoinViewModel::class.java)
        coinViewModel.allCoins.observe(this, Observer { list ->
            list?.let {
                gainBtn.setOnClickListener(View.OnClickListener {
                    var amount : String = addMoney.text.toString()
                    insertDataToDataBase(amount.toInt(),"gain",)

                })
                adapter.updateList(it)
            }
        })
    }
    private fun insertDataToDataBase(amount: Int, type: String) {
        //val totalMoney: String = addMoney.getText().toString()
        //val totalMoneyInt : Int = Integer.parseInt(totalMoney)
        if(inputCheck(amount)){
            coinViewModel.addCoin(Coin(0,"fdfd",type,amount,0))
            Log.d(TAG, "insertDataToDataBase: ${amount}\n${type}")

            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(this,"faild",Toast.LENGTH_SHORT)

        }

    }
    private fun getTotal(){
        coinViewModel.allCoins
    }
    private fun inputCheck( money : Int): Boolean {
        return !(TextUtils.isEmpty(money.toString()))
    }


}


