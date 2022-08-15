package com.mrg.mrgmoney

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.FragmentAddCoinBinding

class AddCoinFragment : Fragment() {

    private lateinit var binding: FragmentAddCoinBinding
    private lateinit var gainBtn: Button
    private lateinit var spendBtn: Button
    private lateinit var addMoney: EditText
    private lateinit var coinViewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCoinBinding.inflate(layoutInflater, container, false)
        setViews()
        coinViewModel = ViewModelProvider(activity?.viewModelStore!!,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(activity?.application!!))
            .get(CoinViewModel::class.java)
        gainBtn.setOnClickListener(View.OnClickListener {
            var amount = addMoney.text.toString().toInt()
            coinViewModel.insertDataToDataBase(amount,"gain",activity?.applicationContext!!)
            addMoney.setText("")
            val action =
                AddCoinFragmentDirections.
                    actionAddCoinFragmentToCoinFragment()
            view?.findNavController()?.navigate(action)
        })

        spendBtn.setOnClickListener(View.OnClickListener {
            var amount = addMoney.text.toString().toInt()
            coinViewModel.insertDataToDataBase(amount,"spend",activity?.applicationContext!!)
            addMoney.setText("")
            val action =
                AddCoinFragmentDirections
                    .actionAddCoinFragmentToCoinFragment()
            view?.findNavController()?.navigate(action)
        })

        return binding.root
    }

    fun setViews() {
        view.apply {
            gainBtn = binding.gainBtn
            spendBtn = binding.spendBtn
            addMoney = binding.addMoney
        }
    }
}