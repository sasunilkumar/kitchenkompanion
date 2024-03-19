package com.example.phase12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.phase12.databinding.ActivityMainBinding
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.GroceryListBinding


class GroceryList : BaseLayout() {
    private lateinit var binding: ViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery_list)

        binding = GroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}