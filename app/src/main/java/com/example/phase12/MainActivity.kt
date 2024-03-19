package com.example.phase12


import android.os.Bundle
import com.example.phase12.databinding.ActivityMainBinding


import androidx.viewbinding.ViewBinding

class MainActivity : BaseLayout() {
    private lateinit var binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar((binding as ActivityMainBinding).toolbar)
    }
}
