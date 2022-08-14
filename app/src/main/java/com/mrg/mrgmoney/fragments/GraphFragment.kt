package com.mrg.mrgmoney.fragments

import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.mrg.mrgmoney.DataBase.Coin
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.FragmentGraphBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {
  private lateinit var binding: FragmentGraphBinding
  private lateinit var coinViewModel: CoinViewModel
  private  val chartGain =  ArrayList<Array<Int>>()
  private  val chartSpend = ArrayList<Array<Int>>()
  private lateinit var aaChartModel : AAChartModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGraphBinding.inflate(layoutInflater,container,false)

        val aaChartView = binding.aaChartView

        coinViewModel = ViewModelProvider(activity?.viewModelStore!!,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(activity?.application!!))
            .get(CoinViewModel::class.java)

        coinViewModel.allCoins.observe(viewLifecycleOwner , Observer {
            setData(setTestData())
//            var arrayGian = intArrayOf(chartGain.to)
            aaChartModel = AAChartModel()
                .chartType(AAChartType.Line)
                .title("Graph")
                .subtitle("money")
                .dataLabelsEnabled(true)
                .series(
                        arrayOf(
                            AASeriesElement()
                                .name("gain")
                                .data(chartGain.toArray())
                            ,
                            AASeriesElement()
                                .name("spend")
                                .data(chartSpend.toArray())

                        )

                    )
            //The chart view object calls the instance object of AAChartModel and draws the final graphic
            aaChartView.aa_drawChartWithChartModel(aaChartModel)
        })

        //Only refresh the chart series data
//        aaChartView.aa_onlyRefreshTheChartDataWithChartModelSeries(chartModelSeriesArray)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setData(list: List<Coin>){
        for (item in list){
            if (item.type == "gain"){
                chartGain.add(arrayOf(item.date.toString().subSequence(0,2).toString().toInt(),item.amount?.toInt()!!))
//                chartGain.add()
                Log.d(TAG, "setData: ${item.date.toString().subSequence(0,2).toString().toInt()}")

            }else if(item.type == "spend"){
                chartSpend.add(arrayOf(item.date.toString().subSequence(0,2).toString().toInt(),item.amount?.toInt()!!))
                Log.d(TAG, "setData: ${item.date.toString().subSequence(0,2).toString().toInt()}")
            }
        }

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