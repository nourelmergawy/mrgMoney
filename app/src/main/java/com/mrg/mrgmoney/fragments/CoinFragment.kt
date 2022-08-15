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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mrg.mrgmoney.AddCoinFragmentDirections
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.FragmentCoinBinding

class CoinFragment : Fragment() , CoinListAdapter.DeleteInterface {
    private lateinit var binding: FragmentCoinBinding

    private lateinit var tvTotal : TextView
    private lateinit var coinViewModel: CoinViewModel
    private val data = ArrayList<Coin>()
    private lateinit var coinFragment : CoinFragment
    private lateinit var button : FloatingActionButton
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


        coinViewModel.allCoins.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if(coinViewModel.getTotal("gain",0) == -1){
                    tvTotal.setText("0")
                }
                else{
                    tvTotal.setText(coinViewModel.getTotal("gain",0).toString())
                }

                Log.d(TAG, "getTotal-CoinFragment : ${coinViewModel.allCoins.value?.lastIndex}")
                // This will pass the ArrayList to our Adapter
                val adapter = CoinListAdapter(data,this)
                // Setting the Adapter with the recyclerview
                binding.recyclerview.adapter = adapter
                adapter.updateList(it)
            }
        })

        button.setOnClickListener(View.OnClickListener {
            val action =
                CoinFragmentDirections.
                actionCoinFragmentToAddCoinFragment()
            view?.findNavController()?.navigate(action)
        })
        return binding.root
    }

    fun setViews(){
        view.apply{
            tvTotal = binding.totalMoney
            button =binding.floatingActionButton
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
    fun setTestData():List<Coin>{
        val  list = listOf<Coin>(
            Coin(0,"10","gain",50,0),
            Coin(0,"11","gain",150,0),
            Coin(0,"12","gain",20,0),
            Coin(0,"13","gain",30,0),
            Coin(0,"14","gain",10,0),
            Coin(0,"15","gain",200,0),
            Coin(0,"10","spend",50,0),
            Coin(0,"11","spend",70,0),
            Coin(0,"12","spend",20,0),
            Coin(0,"13","spend",10,0),
            Coin(0,"14","spend",25,0),
            Coin(0,"15","spend",30,0),
        )
        return list
    }
}