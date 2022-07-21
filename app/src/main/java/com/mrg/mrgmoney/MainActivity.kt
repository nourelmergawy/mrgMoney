package com.mrg.mrgmoney

import CoinListAdapter
import android.content.ContentValues.TAG
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() , CoinListAdapter.DeleteInterface {
    lateinit var gainBtn : Button
    lateinit var spendBtn : Button
    lateinit var addMoney : EditText
    lateinit var total : TextView
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinListAdapter: CoinListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
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
        val adapter = CoinListAdapter(data,this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        // on below line we are
        // initializing our view modal.
        coinViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CoinViewModel::class.java)
        coinViewModel.allCoins.observe(this, Observer { list ->
            list?.let {
                Log.d(TAG, "getTotal: ${coinViewModel.allCoins.value?.lastIndex}")

                if( coinViewModel.allCoins.value?.lastIndex == -1){

                    coinViewModel.addCoin(Coin(0, "","",0,0))
                    Log.d(TAG, "getTotal2: ${coinViewModel.allCoins.value?.lastIndex}")

                }
                total.setText(getTotal("gain",0).toString()!!)
                gainBtn.setOnClickListener(View.OnClickListener {
                    var amount = addMoney.text.toString().toInt()
                    insertDataToDataBase(amount,"gain")
                })
                adapter.updateList(it)
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertDataToDataBase(amount: Int, type: String) {
        //val totalMoney: String = addMoney.getText().toString()
        //val totalMoneyInt : Int = Integer.parseInt(totalMoney)
        if(inputCheck(amount)){

                //var total = getTotal()?.plus(amount)
                coinViewModel.addCoin(Coin(0, LocalDateTime.now().toString(),type,amount,getTotal(type,amount)))

                Log.d(TAG, "insertDataToDataBase: ${amount} - ${type} - ${getTotal(type,amount)}")

            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT)
        }else{
            Toast.makeText(this,"faild",Toast.LENGTH_SHORT)

        }
    }
    private fun getTotal(type: String,amount: Int): Int? {

            Log.d(TAG, "getTotal: ${coinViewModel.allCoins.value?.lastIndex}")
            var index = coinViewModel.allCoins.value?.lastIndex
            if (total != null) {
                var total : Int? = coinViewModel.allCoins.value?.get(index!!)?.total
                if (total != null) {
                    if (type == "gain"){
                        total += amount
                        return total
                    }else{
                        total -= amount
                        return total
                    }
                }else{
                    total = 0
                    getTotal(type,amount)
                }
            }

        return 0
    }
    private fun inputCheck( money : Int): Boolean {
        return !(TextUtils.isEmpty(money.toString()))
    }
    override fun onDelete(coin: Coin) {
        coinViewModel.deleteCoin(coin)
        Toast.makeText(this,"Coin deleted successfully", Toast.LENGTH_LONG).show()
    }
}