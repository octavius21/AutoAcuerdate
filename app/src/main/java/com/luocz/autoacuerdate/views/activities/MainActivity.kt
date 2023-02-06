/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 17:30
 *   File: MainActivity.kt
 *   Last modified: 22/01/23 14:01
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 17:30
 *     Description ->
 *
 * **************************************************************************
 */



package com.luocz.autoacuerdate.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.ActivityMainBinding
import com.luocz.autoacuerdate.models.Car
import com.luocz.autoacuerdate.models.Location
import com.luocz.autoacuerdate.views.fragments.CarListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.navView.background = null


        navController = findNavController(R.id.nav_host_fragment_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.carListFragment,
                R.id.credentialsListFragment,
                R.id.mapFragment,
                R.id.locationFragment,
                R.id.userFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

    }

    fun selectedCar(carItem: Car) {
//         navController = findNavController(R.id.nav_host_fragment_main)
        navController.navigateUp()
//        Podria implementar safeargs para que me agrege las clases directiosn y poner
//        val action = CarListFragmentDrirections.actionCarListFragmentToCarDetailFragment(it)
//        findNavController().navigate(action)
        val parametros = Bundle().apply {
            putString("ID_CAR",carItem.id_car)
        }
        navController.navigate(R.id.carDetailFragment, parametros)
    }
    fun selectedLocation(locationItem: Location) {
        val parametros = Bundle().apply {
            putString("ID_LOCATION",locationItem.id_location)
        }
        navController.navigate(R.id.locationDetailFragment, parametros)
    }
}