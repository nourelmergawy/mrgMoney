package com.mrg.mrgmoney

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mrg.mrgmoney.ViewModel.CoinViewModel
import com.mrg.mrgmoney.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinViewModel: CoinViewModel
    lateinit var bottomNavigationView : BottomNavigationView
    var navController: NavController? = null
    var appBarConfiguration: AppBarConfiguration? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initializing our view modal.
        coinViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CoinViewModel::class.java)
        setupButtomBar()
//
//        bottomNavigationView = binding.bottomNavigationView
//        val navController: NavController =  Navigation.findNavController(this, R.id.fragmentContainerView);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
    fun setupButtomBar() {
        val navView = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.coinFragment, R.id.graphFragment, R.id.graphFragment
        )
            .build()
//        navView.selectedItemId = R.id.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment?
        navController = navHostFragment!!.navController
        setupWithNavController(navView, navController!!)
        setupActionBarWithNavController(this, navController!!, appBarConfiguration!!)
//        val user: User = DashBoardFragmentArgs.fromBundle(intent.extras).getGetUserData()
//        navController.navigate(
//            SignInFragmentDirections.actionGlobalDashBoardFragment(user)
//        )
    }

}