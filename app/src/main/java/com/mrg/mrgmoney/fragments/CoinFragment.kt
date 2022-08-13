package com.mrg.mrgmoney.fragments

import com.mrg.mrgmoney.adapters.CoinListAdapter
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.FragmentCoinBinding

class CoinFragment : Fragment() , CoinListAdapter.DeleteInterface {
    private lateinit var binding: FragmentCoinBinding
    private lateinit var gainBtn : Button
    private lateinit var spendBtn : Button
    private lateinit var addMoney : EditText
    private lateinit var tvTotal : TextView
    private lateinit var coinViewModel: CoinViewModel
    private val data = ArrayList<Coin>()
    private lateinit var coinFragment : CoinFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCoinBinding.inflate(inflater, container, false)
        coinFragment = CoinFragment()

        setViews()
        setRecycler()

        coinViewModel = ViewModelProvider(activity?.viewModelStore!!,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(activity?.application!!))
                .get(CoinViewModel::class.java)

        gainBtn.setOnClickListener(View.OnClickListener {
            var amount = addMoney.text.toString().toInt()
            coinViewModel.insertDataToDataBase(amount,"gain",activity?.applicationContext!!)
            addMoney.setText("")
        })

        spendBtn.setOnClickListener(View.OnClickListener {
            var amount = addMoney.text.toString().toInt()
            coinViewModel.insertDataToDataBase(amount,"spend",activity?.applicationContext!!)
            addMoney.setText("")
        })

        coinViewModel.allCoins.observe(viewLifecycleOwner, Observer { list ->
            list?.let {

                Log.d(TAG, "getTotal-CoinFragment : ${coinViewModel.allCoins.value?.lastIndex}")

                if(coinViewModel.getTotal("gain",0) == -1){
                    tvTotal.setText("0")

                }else{
                    tvTotal.setText(coinViewModel.getTotal("gain",0).toString())

                }
                // This will pass the ArrayList to our Adapter
                val adapter = CoinListAdapter(data,this)
                // Setting the Adapter with the recyclerview
                binding.recyclerview.adapter = adapter
                adapter.updateList(it)
            }
        })


        return binding.root
    }

    fun setViews(){
        view.apply{
            gainBtn = binding.gainBtn
            spendBtn =binding.spendBtn
            addMoney = binding.addMoney
            tvTotal = binding.totalMoney
        }
    }

    fun setRecycler(){
        // getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(activity?.applicationContext)

    }
    override fun onDelete(coin: Coin) {
        coinViewModel.deleteCoin(coin)
        Toast.makeText(activity?.applicationContext!!,"Coin deleted successfully", Toast.LENGTH_LONG).show()
    }
}