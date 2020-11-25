package com.example.room_retrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.room_retrofit.R
import com.example.room_retrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        binding = ActivityMainBinding.inflate(layoutInflater)

        setListenerOnMenuNavigation()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainPageFragment())
            .addToBackStack(null)
            .commit()


    }

    private fun setListenerOnMenuNavigation() {
//        binding.tabLayout.setupWithViewPager() {
//
//            when (it.itemId) {
//                R.id.btn_database -> {
//                    Log.d("test123", "else")
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, SavedPostsFragment())
//                        .addToBackStack(null)
//                        .commit()
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.btn_internet -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, MainPageFragment())
//                        .addToBackStack(null)
//                        .commit()
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> {Log.d("test123", "else")
//                    return@setOnNavigationItemSelectedListener true}
//            }
//
//        }
    }
}