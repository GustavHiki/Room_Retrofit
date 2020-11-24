package com.example.room_retrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.room_retrofit.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainPageFragment())
            .commit()
    }
}