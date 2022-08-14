package com.mrg.mrgmoney.fragments

import android.os.Build
import android.os.Bundle
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
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {
  private lateinit var binding: FragmentGraphBinding
  private lateinit var coinViewModel: CoinViewModel
//  private val data = ArrayList<ILineDataSet>()
  private  val chartGain =  ArrayList<Int>()
  private  val chartSpend = ArrayList<Int>()
//  private lateinit var arrayGain :Array<AASeriesElement>()
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
            setData(it)
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
                                .data(arrayOf(chartGain.toArray(), arrayOf(3, 4)))
//                            arrayOf(
//                                AASeriesElement()
//                                    .data(),
//                                AASeriesElement()
//                                    .name("spend")
//                                    .data(chartSpend.toArray())
//                            ),
//                            arrayOf(1,2,3,4,5,6,7,8,9,10)
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

                chartGain.add(
//                        LocalDate.parse(item.date).dayOfMonth.toFloat()?: 0F,
                    item.amount?.toInt()!!
                )
            }else if(item.type == "spend"){
                chartSpend.add(
                    item.amount?.toInt()!!
                )
            }
        }

    }
}