package com.example.amatiberkah.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amatiberkah.R
import com.example.amatiberkah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toMyActivity.setOnClickListener{
            val intent = Intent(this@MainActivity, ExploreVillageDescriptionActivity::class.java)
            startActivity(intent)

        }
    }
}