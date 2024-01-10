package com.brayandev.credibancoapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_grav) as NavHostFragment
        navController = navHost.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}
